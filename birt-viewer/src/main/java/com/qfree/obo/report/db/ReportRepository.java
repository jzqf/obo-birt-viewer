package com.qfree.obo.report.db;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.qfree.obo.report.domain.Report;
import com.qfree.obo.report.domain.Role;
import com.qfree.obo.report.domain.RoleRole;

/**
 * Repository interface for {@link Report} persistence.
 *
 * Only query methods that are generated by Spring Data JPA using Spring Data's
 * domain-specific language or via JPQL should be declared here.
 * 
 * @author Jeffrey Zelt
 */
public interface ReportRepository extends JpaRepository<Report, UUID>, ReportRepositoryCustom {

	Report findByName(String name);

	List<Report> findByActiveTrue();

	List<Report> findByReportCategoryReportCategoryId(UUID reportCategoryId);

	@Query("SELECT r FROM RoleReport rr INNER JOIN rr.role r WHERE rr.report.reportId = :reportId")
	public List<Role> findRolesByReportId(@Param("reportId") UUID reportId);

	@Query("SELECT r FROM RoleReport rr INNER JOIN rr.role r WHERE rr.report.reportId = :reportId AND r.active=true")
	public List<Role> findActiveRolesByReportId(@Param("reportId") UUID reportId);

	/**
	 * Returns a {@link List}&lt;{@link String}&gt; that represent the ids of
	 * {@link Role} entities that are authorized for access to a specified 
	 * {@link Report}.
	 * 
	 * <p>
	 * A maximum of 10 levels of {@link RoleRole} relations will be followed.
	 * This is to avoid endless recursion for the case where a circular loop is
	 * created, e.g., a parent of a {@link Role} is set to be a child of that
	 * {@link Role}. The UI should protect the user from such situations, but in
	 * case this protection is not provided, this will provide a last line of
	 * defense.
	 * 
	 * @param reportId
	 *            String representation of the id of the {@link Report} for 
	 *            which authorized roles will be returned.
	 * @param activeOnly
	 *            If {@code true}, only {@link Role}s that have
	 *            {@code active=true} will be returned
	 * @return
	 */
	@Query(value = "WITH RECURSIVE descendent(level, role_id, username) AS (" +

	// CTE anchor member:

			"SELECT 0 AS level, role.role_id, role.username " +
			"FROM role " +
			"INNER JOIN role_report ON role_report.role_id=role.role_id " +
			//"WHERE role_report.report_id=:reportId " +
			"WHERE role_report.report_id=CAST(:reportId AS uuid) " +
			"AND (role.active=true OR :activeOnly=false) " +

			"UNION ALL " +

	// CTE recursive member:

			"SELECT level+1, role.role_id, role.username " +
			"FROM descendent " +
			"INNER JOIN role_role link ON link.parent_role_id=descendent.role_id " +
			"INNER JOIN role ON role.role_id=link.child_role_id " +
			"WHERE level<10 " +
			"AND (role.active=true OR :activeOnly=false) " +
			
			") " +

	// Statement using the CTE:

	/* 
	 * Here, we do a select on a derived table. The reason for this
	 * approach is that we want to order the results by role.username,
	 * but since I need to eliminate duplicate rows with DISTINCT
	 * (these duplicates occur because [role_role] junction records
	 * may link both a [role] as well as one or more of its descendent
	 * [role] records to the same parent [role]), 
	 * the SELECT list must include the column that
	 * we order on, in this case role.username. But I only want to 
	 * return a list of role_id's; hence, I perform a select on the
	 * derived table (that takes care of the DISTINCT business for us),
	 * and then I can order by DT.username 
	 * without including it in the SELECT list because this outer 
	 * SELECT does not use DISTINCT.
	 */
			"SELECT DT.role_id FROM " +
			"(" +
			"    SELECT DISTINCT CAST(descendent.role_id AS varchar), descendent.username FROM descendent " +
			//"    INNER JOIN role ON role.role_id=descendent.role_id " +
			//"    WHERE (role.active=true OR :activeOnly=false) " +
			") DT " +
			"ORDER BY DT.username",
			nativeQuery = true)
	public List<String> findRolesByReportIdRecursive(
			@Param("reportId") String reportId,
			@Param("activeOnly") Boolean activeOnly);
}

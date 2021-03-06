package com.qfree.bo.report.db;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.qfree.bo.report.domain.Report;
import com.qfree.bo.report.domain.ReportVersion;
import com.qfree.bo.report.domain.Role;
import com.qfree.bo.report.domain.RoleRole;

/**
 * Repository interface for {@link ReportVersion} persistence.
 *
 * Only query methods that are generated by Spring Data JPA using Spring Data's
 * domain-specific language or via JPQL should be declared here.
 * 
 * @author Jeffrey Zelt
 */
public interface ReportVersionRepository extends JpaRepository<ReportVersion, UUID>, ReportVersionRepositoryCustom {

	public List<ReportVersion> findByActiveTrue();

	public ReportVersion findByFileName(String fileName);

	//	@Query("SELECT rv FROM ReportVersion rv INNER JOIN rv.report r WHERE r.reportId = :reportId")
	@Query("SELECT rv FROM Report r INNER JOIN r.reportVersions rv WHERE r.reportId = :reportId")
	public List<ReportVersion> findByReportId(@Param("reportId") UUID reportId);

	//	@Query("SELECT rv FROM ReportVersion rv INNER JOIN rv.report r WHERE r.reportId = :reportId AND rv.active=true")
	@Query("SELECT rv FROM Report r INNER JOIN r.reportVersions rv WHERE r.reportId = :reportId AND rv.active=true")
	public List<ReportVersion> findActiveByReportId(@Param("reportId") UUID reportId);

	@Query("SELECT MAX(rv.versionCode) FROM Report r INNER JOIN r.reportVersions rv WHERE r = :report")
	public Integer maxVersionCodeForReport(@Param("report") Report report);

	/**
	 * Returns a {@link List}&lt;{@link String}&gt; containing the fileName of
	 * all {@link ReportVersion} entities that a specified {@link Role} has
	 * access to.
	 * 
	 * <p>
	 * A maximum of 10 levels of {@link RoleRole} relations will be followed.
	 * This is to avoid endless recursion for the case where a circular loop is
	 * created, e.g., a parent of a {@link Role} is set to be a child of that
	 * {@link Role}. The UI should protect the user from such situations, but in
	 * case this protection is not provided, this will provide a last line of
	 * defense.
	 * 
	 * @param roleId
	 *            String representation of the id of the {@link Role} for which
	 *            report version filenames will be returned.
	 * @param activeReportsAndVersionsOnly
	 *            If {@code true}, only {@link ReportVersion}s will be returned
	 *            for which {@code active=true} for the {@link ReportVersion}
	 *            as well as for its parent{@link Report}.
	 * @param activeInheritedRolesOnly
	 *            If {@code true}, only {@link Roles}s that have
	 *            {@code active=true} will be considered for Role inheritance.
	 * @return
	 */
	@Query(value = "WITH RECURSIVE ancestor(level, role_id, username) AS (" +

	// CTE anchor member:

			"SELECT 0 AS level, role.role_id, role.username " +
			"FROM role " +
			//"WHERE role.role_id=:roleId " +
			"WHERE role.role_id=CAST(:roleId AS uuid) " +

			"UNION ALL " +

	// CTE recursive member:

			"SELECT level+1, role.role_id, role.username " +
			"FROM ancestor " +
			"INNER JOIN role_role link ON link.child_role_id=ancestor.role_id " +
			"INNER JOIN role ON role.role_id=link.parent_role_id " +
			"WHERE level<10 " +
			/* 
			 * Insist that Roles used for Role inheritance are active if
			 * Parameter activeInheritedRolesOnly = true. This test is NOT performed
			 * for the CTE anchor member because the Role involved there is
			 * specified directly by the parameter "roleId", and if the user
			 * wants to find the names of ReportVersion entities that this
			 * Role has access to, we allow the query to return this 
			 * information.
			 */
			"AND (role.active=true OR :activeInheritedRolesOnly=false) " +

			") " +

	// Statement using the CTE:

	/* 
	 * Here, we do a select on a derived table. The reason for this approach
	 * is that we want to order the results by report_version.file_name,
	 * but since I need to eliminate duplicate rows with DISTINCT
	 * (these duplicates occur because [role_report] junction records may
	 * link both a [role] as well as one or more of its ancestor [role] records
	 * to the same [report]), the SELECT list must include the column that we
	 * order on, in this case report_version.file_name. Therefore, I perform the 
	 * DISTINCT operation in the definition of the derived table, and then I use
	 * the derived table in the FROM clause of the outer SELECT, where I am free
	 * to also order by DT.file_name since this outer SELECT does not use 
	 * DISTINCT.
	 */
			"SELECT DT.file_name FROM " +
			"(" +
			"    SELECT DISTINCT report_version.file_name AS file_name FROM role_report " +
			"    INNER JOIN ancestor ON ancestor.role_id=role_report.role_id " +
			"    INNER JOIN report ON report.report_id=role_report.report_id " +
			"    INNER JOIN report_version ON report_version.report_id=report.report_id " +
			"    WHERE ((report.active        =true OR :activeReportsAndVersionsOnly=false) AND " +
			"           (report_version.active=true OR :activeReportsAndVersionsOnly=false))" +
			") DT " +
			"ORDER BY DT.file_name",
			nativeQuery = true)
	public List<String> findReportVersionFilenamesByRoleIdRecursive(
			@Param("roleId") String roleId,
			@Param("activeReportsAndVersionsOnly") Boolean activeReportsAndVersionsOnly,
			@Param("activeInheritedRolesOnly") Boolean activeInheritedRolesOnly);
}

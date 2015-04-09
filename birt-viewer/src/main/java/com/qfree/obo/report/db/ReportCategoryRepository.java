package com.qfree.obo.report.db;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.qfree.obo.report.domain.ReportCategory;

/**
 * Repository interface for {@link ReportCategory} persistence.
 *
 * Only query methods that are generated by Spring Data JPA using Spring Data's
 * domain-specific language or via JPQL should be declared here.
 * 
 * @author Jeffrey Zelt
 */
public interface ReportCategoryRepository extends JpaRepository<ReportCategory, UUID>, ReportCategoryRepositoryCustom {
	  
	ReportCategory findByAbbreviation(String abbreviation);

	ReportCategory findByDescription(String description);
	
	List<ReportCategory> findByDescriptionLikeOrAbbreviationLike(String description, String abbreviation);

	List<ReportCategory> findByActiveIsTrue();

}

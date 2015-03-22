package com.qfree.obo.report.db;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.qfree.obo.report.domain.Report;

/**
 * Repository interface for {@link Report} persistence.
 *
 * Only query methods that are generated by Spring Data JPA using Spring Data's
 * domain-specific language should be declared here.
 * 
 * @author Jeffrey Zelt
 */
public interface ReportRepository extends JpaRepository<Report, UUID>, ReportRepositoryCustom {
  
	List<Report> findByReportCategoryReportCategoryId(UUID reportCategoryId);
  
}

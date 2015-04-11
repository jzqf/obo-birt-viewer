package com.qfree.obo.report.db;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.qfree.obo.report.domain.JobParameterValue;

/**
 * Repository interface for {@link JobParameterValue} persistence.
 *
 * Only query methods that are generated by Spring Data JPA using Spring Data's
 * domain-specific language or via JPQL should be declared here.
 * 
 * @author Jeffrey Zelt
 */
public interface JobParameterValueRepository extends JpaRepository<JobParameterValue, UUID> {
	  
}
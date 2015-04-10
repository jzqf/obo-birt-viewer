package com.qfree.obo.report.db;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.qfree.obo.report.configuration.Config.ParamName;
import com.qfree.obo.report.domain.Configuration;
import com.qfree.obo.report.domain.Role;

/**
 * Repository interface for {@link Configuration} persistence.
 *
 * Only query methods that are generated by Spring Data JPA using Spring Data's
 * domain-specific language or via JPQL should be declared here.
 * 
 * @author Jeffrey Zelt
 */
public interface ConfigurationRepository extends JpaRepository<Configuration, UUID> {

	//	@Query("SELECT c FROM Configuration c WHERE c.paramName=:paramName AND c.role IS NULL")
	//	Configuration findByParamName(@Param("paramName") String paramName);

	//	@Query("SELECT c FROM Configuration c WHERE c.paramName=:paramName AND c.role=:role")
	//	Configuration findByParamName(@Param("paramName") String paramName, @Param("role") Role role);

	@Query("SELECT c FROM Configuration c WHERE c.paramName=:paramName AND c.role IS NULL")
	Configuration findByParamName(@Param("paramName") ParamName paramName);

	/*
	 * This query does not work if the :role parameter is null, i.e., the WHERE
	 * clause "c.role=:role" is not correct if :role is null. If it is null,
	 * the proper WHERE clause would be "c.role IS NULL". 
	 * 
	 * Testing shows that a check for ":role IS NULL" in this query does not
	 * work and, in fact, triggers an error when :role is null. Hence, it is
	 * important to use this method signature only when :role is not null. It 
	 * does not appear to be possible to "guard" null values or to write a 
	 * clever query that tests for parameter :role being null since the very
	 * test that must be used (":role IS NULL") throws an error.
	 * 
	 * This problem may be related to the following bug reports:
	 * 
	 *     https://hibernate.atlassian.net/browse/JPA-28
	 *     https://bugs.eclipse.org/bugs/show_bug.cgi?id=362414
	 */
	@Query("SELECT c FROM Configuration c WHERE c.paramName=:paramName AND c.role=:role")
	Configuration findByParamName(@Param("paramName") ParamName paramName, @Param("role") Role role);

}

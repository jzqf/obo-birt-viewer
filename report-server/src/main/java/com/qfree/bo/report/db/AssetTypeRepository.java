package com.qfree.bo.report.db;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.qfree.bo.report.domain.AssetType;

/**
 * Repository interface for {@link AssetType} persistence.
 *
 * Only query methods that are generated by Spring Data JPA using Spring Data's
 * domain-specific language or via JPQL should be declared here.
 * 
 * @author Jeffrey Zelt
 */
public interface AssetTypeRepository extends JpaRepository<AssetType, UUID> {

	List<AssetType> findByActiveTrue();

}

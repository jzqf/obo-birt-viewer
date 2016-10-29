package com.qfree.obo.report.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.qfree.obo.report.db.AuthorityRepository;
import com.qfree.obo.report.domain.Authority;
import com.qfree.obo.report.domain.UuidCustomType;

@Component
@Transactional
public class AuthorityService {

	private static final Logger logger = LoggerFactory.getLogger(AuthorityService.class);

	private final AuthorityRepository authorityRepository;

	@Autowired
	public AuthorityService(AuthorityRepository authorityRepository) {
		this.authorityRepository = authorityRepository;
	}

	/**
	 * Returns an {@link Authority} {@link List} for all {@link Authority}
	 * entities that are linked <b>directly</b> to a {@link Role}, i.e.,
	 * {@link Role} inheritance is not taken into account.
	 * 
	 * @param roleId
	 * @return
	 */
	@Transactional
	public List<Authority> getActiveAuthoritiesByRoleIdDirect(UUID roleId) {

		List<String> uuidStrings = authorityRepository.findActiveAuthorityIdsByRoleId(roleId.toString());
		List<Authority> authorities = new ArrayList<>(uuidStrings.size());
		for (String uuidString : uuidStrings) {
			try {
				UUID authorityId = UUID.fromString(uuidString);
				Authority authority = authorityRepository.findOne(authorityId);
				if (authority != null) {
					authorities.add(authority);
				}
			} catch (IllegalArgumentException e) {
				logger.error("Illegal value for authorityId: {}", uuidString);
			}
		}
		return authorities;
	}

	/**
	 * Returns an {@link Authority} {@link List} for a {@link Role} specified
	 * by its id. If a PostgreSQL database is being used, {@link Role} 
	 * inheritance will be taken into account. If H2 is being used (for unit
	 * and integration tests), then {@link Role} inheritance will not be taken 
	 * into account.
	 * 
	 * @param roleId
	 * @return
	 */
	@Transactional
	public List<Authority> getActiveAuthoritiesByRoleId(UUID roleId) {

		/*
		 * This returns a list of Strings, each of which represents a UUID id
		 * of and Authority. 
		 */
		List<String> uuidStrings = findActiveAuthorityIdsByRoleId(roleId);
		/*
		 * The rest of this method generates a list of Authority entities from
		 * the list of Authority ids that are expressed as Strings, i.e., it
		 * performs the conversion:  List<String> -> List<Authority>
		 */
		List<Authority> authorities = new ArrayList<>(uuidStrings.size());
		for (String uuidString : uuidStrings) {
			try {
				UUID authorityId = UUID.fromString(uuidString);
				Authority authority = authorityRepository.findOne(authorityId);
				if (authority != null) {
					authorities.add(authority);
				}
			} catch (IllegalArgumentException e) {
				logger.error("Illegal value for authorityId: {}", uuidString);
			}
		}
		return authorities;
	}

	public List<String> findActiveAuthorityIdsByRoleId(UUID roleId) {
		/*
		 * The H2 database does not support recursive CTE expressions, so it is 
		 * necessary to run different code if the database is not PostgreSQL.
		 * This only affects integration tests, because only PostreSQL is used
		 * in production. 
		 */
		if (UuidCustomType.DB_VENDOR.equals(UuidCustomType.POSTGRESQL_VENDOR)) {
			return authorityRepository.findActiveAuthorityIdsByRoleIdRecursive(roleId.toString());
		} else {
			return authorityRepository.findActiveAuthorityIdsByRoleId(roleId.toString());
		}
	}

	public List<String> findActiveAuthorityNamesByRoleId(UUID roleId) {
		/*
		 * The H2 database does not support recursive CTE expressions, so it is 
		 * necessary to run different code if the database is not PostgreSQL.
		 * This only affects integration tests, because only PostreSQL is used
		 * in production. 
		 */
		if (UuidCustomType.DB_VENDOR.equals(UuidCustomType.POSTGRESQL_VENDOR)) {
			return authorityRepository.findActiveAuthorityNamesByRoleIdRecursive(roleId.toString());
		} else {
			return authorityRepository.findActiveAuthorityNamesByRoleId(roleId.toString());
		}
	}

	public List<String> findAuthorityNamesByRoleId(UUID roleId) {
		/*
		 * The H2 database does not support recursive CTE expressions, so it is 
		 * necessary to run different code if the database is not PostgreSQL.
		 * This only affects integration tests, because only PostreSQL is used
		 * in production. 
		 */
		if (UuidCustomType.DB_VENDOR.equals(UuidCustomType.POSTGRESQL_VENDOR)) {
			return authorityRepository.findAuthorityNamesByRoleIdRecursive(roleId.toString());
		} else {
			return authorityRepository.findAuthorityNamesByRoleId(roleId.toString());
		}
	}

}

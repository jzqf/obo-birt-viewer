package com.qfree.obo.report.db;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.qfree.obo.report.domain.Report;

/**
 * Implementation class for "custom" repository query methods for 
 * {@link Report} persistence.<br>
 * <br>
 * Only query methods that are declared in 
 * {@link ReportRepositoryCustom} should be declared here. These are
 * methods that <i>cannot</i> be created using Spring Data's domain specific 
 * language. Query methods created using Spring Data's domain specific language
 * are declared in {@link ReportRepository}.
 * 
 * @author Jeffrey Zelt
 */
public class ReportRepositoryImpl implements ReportRepositoryCustom {

	@PersistenceContext
	private EntityManager entityManager;

	public List<Report> findRecentlyCreated() {
		return findRecentlyCreated(10);
	}

	/*
	 * This version uses a static, named query that is defined in the 
	 * Configuration entity class.
	 */
	public List<Report> findRecentlyCreated(int count) {
		return (List<Report>) entityManager.createNamedQuery("Report.findByCreated", Report.class)
				.setMaxResults(count)
				.getResultList();
	}

	/*
	 * This version uses a dynamic query.
	 */
	//	public List<Report> findRecentlyCreated(int count) {
	//		return (List<Report>) entityManager.createQuery("select r from Report r order by r.createdOn desc")
	//				.setMaxResults(count)
	//				.getResultList();
	//	}

}

package com.qfree.obo.report.resource;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qfree.obo.report.domain.Report;
import com.qfree.obo.report.domain.ReportCategory;

@XmlRootElement
public class ReportCategoryResource {

	private static final Logger logger = LoggerFactory.getLogger(ReportCategoryResource.class);

	@XmlElement
	private String href;

	@XmlElement
	private UUID reportCategoryId;

	@XmlElement
	private String abbreviation;

	@XmlElement
	private String description;

	//	@XmlElement
	//	private List<Report> reports;

	@XmlElement
	private boolean active;

	@XmlElement
	private Date createdOn;

	public ReportCategoryResource() {
	}

	public ReportCategoryResource(ReportCategory reportCategory) {
		this(
				reportCategory.getReportCategoryId(),
				reportCategory.getAbbreviation(),
				reportCategory.getDescription(),
				reportCategory.getReports(),
				reportCategory.isActive(),
				reportCategory.getCreatedOn());
	}

	public ReportCategoryResource(
			UUID reportCategoryId,
			String abbreviation,
			String description,
			List<Report> reports,
			boolean active,
			Date createdOn) {

		logger.info("reportCategoryId = {}", reportCategoryId);

		this.reportCategoryId = reportCategoryId;
		this.abbreviation = abbreviation;
		this.description = description;
		//		this.reports=reports;
		this.active = active;
		this.createdOn = createdOn;
	}

}

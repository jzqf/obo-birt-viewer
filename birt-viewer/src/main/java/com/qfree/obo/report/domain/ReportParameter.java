package com.qfree.obo.report.domain;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

/**
 * The persistent class for the report_parameter database table.
 * 
 */
@Entity
@Table(name = "report_parameter", schema = "reporting")
public class ReportParameter implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@NotNull
	// type="pg-uuid" only works for postgresql
	@Type(type = "pg-uuid")
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(name = "report_parameter_id", columnDefinition = "uuid", unique = true, nullable = false)
	private UUID reportParameterId;

	@Column(nullable=false, length=20)
	private String abbreviation;

	@Column(nullable=false)
	private Boolean active;

	@Column(nullable=false, length=25)
	private String description;

	//	//bi-directional many-to-one association to Report
	//	@OneToMany(mappedBy="reportCategory")
	//	private List<ReportUuid> reports;

	public ReportParameter() {
	}

	public ReportParameter(UUID reportParameterId, String abbreviation, String description, Boolean active) {
		//super();
		//		if (reportParameterId == null) {
		//			reportParameterId = java.util.UUID.randomUUID();
		//		}
		this.reportParameterId = reportParameterId;
		this.abbreviation = abbreviation;
		this.description = description;
		this.active = active;
	}

	public UUID getreportParameterId() {
		return this.reportParameterId;
	}

	public void setreportParameterId(UUID reportParameterId) {
		this.reportParameterId = reportParameterId;
	}

	public String getAbbreviation() {
		return this.abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public Boolean getActive() {
		return this.active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ReportParameter [reportParameterId=");
		builder.append(reportParameterId);
		builder.append(", abbreviation=");
		builder.append(abbreviation);
		builder.append(", active=");
		builder.append(active);
		builder.append(", description=");
		builder.append(description);
		builder.append("]");
		return builder.toString();
	}

	//	public List<ReportUuid> getReports() {
	//		return this.reports;
	//	}
	//
	//	public void setReports(List<ReportUuid> reports) {
	//		this.reports = reports;
	//	}
	//
	//	public ReportUuid addReport(ReportUuid report) {
	//		getReports().add(report);
	//		report.setReportCategory(this);
	//
	//		return report;
	//	}
	//
	//	public ReportUuid removeReport(ReportUuid report) {
	//		getReports().remove(report);
	//		report.setReportCategory(null);
	//
	//		return report;
	//	}

}
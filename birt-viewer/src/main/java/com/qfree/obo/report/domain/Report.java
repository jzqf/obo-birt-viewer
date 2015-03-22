package com.qfree.obo.report.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

@Entity
@Table(name = "report", schema = "reporting")
@TypeDef(name = "uuid-custom", defaultForType = UUID.class, typeClass = UUIDCustomType.class)
public class Report implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	//	@Column(name = "report_id", unique = true, nullable = false)
	//	private Long reportId;
	@Type(type = "uuid-custom")
	//	@Type(type = "pg-uuid")
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(name = "report_id", columnDefinition = "uuid", unique = true, nullable = false)
	private UUID reportId;

	@ManyToOne
	@JoinColumn(name = "report_category_id")
	private ReportCategory reportCategory;

	@Column(name = "name")
	private String name;

	@Column(name = "created_on")
	private Date createdOn;

	private Report() {
	}

	public Report(ReportCategory reportCategory, String name, Date createdOn) {
		this.reportCategory = reportCategory;
		this.name = name;
		this.createdOn = createdOn;
	}

	//	public Report(UUID reportId, ReportCategory reportCategory, String name, Date createdOn) {
	//		this.reportId = reportId;
	//		this.reportCategory = reportCategory;
	//		this.name = name;
	//		this.createdOn = createdOn;
	//	}

	public UUID getReportId() {
		return this.reportId;
	}

	public String getName() {
		return this.name;
	}

	public Date getCreatedOn() {
		return this.createdOn;
	}

	public ReportCategory getReportCategory() {
		return this.reportCategory;
	}

}

package com.qfree.obo.report.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

/**
 * The persistent class for the "report_parameter" database table.
 * 
 * @author Jeffrey Zelt
 * 
 */
@Entity
@Table(name = "report_parameter", schema = "reporting")
@TypeDef(name = "uuid-custom", defaultForType = UUID.class, typeClass = UuidCustomType.class)
public class ReportParameter implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@NotNull
	@Type(type = "uuid-custom")
	//	@Type(type = "pg-uuid")
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(name = "report_parameter_id", unique = true, nullable = false,
			columnDefinition = "uuid DEFAULT uuid_generate_v4()")
	private UUID reportParameterId;

	@Column(name = "name", nullable = false, length = 32)
	private String name;

	@Column(name = "description", nullable = false, length = 80)
	private String description;

	@Column(name = "required", nullable = false)
	private Boolean required;

	@ManyToOne
	/*
	 * If columnDefinition="uuid" is omitted here and the database schema is 
	 * created by Hibernate (via hibernate.hbm2ddl.auto="create"), then the 
	 * PostgreSQL column definition includes "DEFAULT uuid_generate_v4()", which
	 * is not what is wanted.
	 */
	@JoinColumn(name = "report_id", nullable = false,
			foreignKey = @ForeignKey(name = "fk_reportparameter_report"),
			columnDefinition = "uuid")
	private Report report;

	@ManyToOne
	/*
	 * If columnDefinition="uuid" is omitted here and the database schema is 
	 * created by Hibernate (via hibernate.hbm2ddl.auto="create"), then the 
	 * PostgreSQL column definition includes "DEFAULT uuid_generate_v4()", which
	 * is not what is wanted.
	 */
	@JoinColumn(name = "widget_id", nullable = false,
			foreignKey = @ForeignKey(name = "fk_reportparameter_widget"),
			columnDefinition = "uuid")
	private Widget widget;

	@Column(name = "created_on", nullable = false)
	private Date createdOn;

	public ReportParameter() {
	}

	public ReportParameter(Report report, String name, String description, Widget widget, Boolean required) {
		this(report, name, description, widget, required, new Date());
	}

	public ReportParameter(Report report, String name, String description, Widget widget, Boolean required,
			Date createdOn) {
		this.report = report;
		this.name = name;
		this.description = description;
		this.widget = widget;
		this.required = required;
		this.createdOn = createdOn;
	}

	public UUID getReportParameterId() {
		return this.reportParameterId;
	}

	public void setReportParameterId(UUID reportParameterId) {
		this.reportParameterId = reportParameterId;
	}

	public Widget getWidget() {
		return widget;
	}

	public void setWidget(Widget widget) {
		this.widget = widget;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getRequired() {
		return this.required;
	}

	public void setRequired(Boolean required) {
		this.required = required;
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
		builder.append(", name=");
		builder.append(name);
		builder.append(", description=");
		builder.append(description);
		builder.append(", required=");
		builder.append(required);
		builder.append("]");
		return builder.toString();
	}

}
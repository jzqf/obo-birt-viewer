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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import com.qfree.obo.report.util.DateUtils;

/**
 * The persistent class for the "role_parameter_value" database table.
 * 
 * Instances/rows represent last used values by a specified Role for a specified
 * ReportParameter. Multi-valued ReportParameter's can have multiple
 * RoleParameterValue's for a given [Role, ReportParameter] combination.
 * Single-valued ReportParameter's may only have a single RoleParameterValue for
 * a given [Role, ReportParameter] combination.
 * 
 * @author Jeffrey Zelt
 * 
 */
@Entity
@Table(name = "role_parameter_value", schema = "reporting")
@TypeDef(name = "uuid-custom", defaultForType = UUID.class, typeClass = UuidCustomType.class)
public class RoleParameterValue implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@NotNull
	@Type(type = "uuid-custom")
	//	@Type(type = "pg-uuid")
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(name = "role_parameter_value_id", unique = true, nullable = false,
			columnDefinition = "uuid DEFAULT uuid_generate_v4()")
	private UUID roleParameterValueId;

	@ManyToOne
	/*
	 * If columnDefinition="uuid" is omitted here and the database schema is 
	 * created by Hibernate (via hibernate.hbm2ddl.auto="create"), then the 
	 * PostgreSQL column definition includes "DEFAULT uuid_generate_v4()", which
	 * is not what is wanted.
	 */
	@NotNull
	@JoinColumn(name = "role_parameter_id", nullable = false,
			foreignKey = @ForeignKey(name = "fk_roleparametervalue_roleparameter") ,
			columnDefinition = "uuid")
	private RoleParameter roleParameter;

	/**
	 * A "last used" value for report parameters of data type = Boolean.
	 */
	@Column(name = "boolean_value", nullable = true)
	private Boolean booleanValue;

	/**
	 * A "last used" value for report parameters of data type = Date.
	 */
	@Temporal(TemporalType.DATE)
	@Column(name = "date_value", nullable = true)
	private Date dateValue;

	/**
	 * A "last used" value for report parameters of data type = Datetime.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "datetime_value", nullable = true)
	private Date datetimeValue;

	/**
	 * A "last used" value for report parameters of data type = Float.
	 */
	/*
	 * TODO Can this also be used for report parameters of type "Decimal"?
	 */
	@Column(name = "float_value", nullable = true)
	private Double floatValue;

	/**
	 * A "last used" value for report parameters of data type = Integer.
	 */
	@Column(name = "integer_value", nullable = true)
	private Integer integerValue;

	/**
	 * A "last used" value for report parameters of data type = String.
	 */
	//@NotBlank
	@Column(name = "string_value", nullable = true, length = 80)
	private String stringValue;

	/**
	 * A "last used" value for report parameters of data type = Time.
	 */
	@Temporal(TemporalType.TIME)
	@Column(name = "time_value", nullable = true)
	private Date timeValue;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_on", nullable = false)
	private Date createdOn;

	public RoleParameterValue() {
	}

	public RoleParameterValue(
			RoleParameter roleParameter,
			Boolean booleanValue,
			Date dateValue,
			Date datetimeValue,
			Double floatValue,
			Integer integerValue,
			String stringValue,
			Date timeValue) {
		this(
				roleParameter,
				booleanValue,
				dateValue,
				datetimeValue,
				floatValue,
				integerValue,
				stringValue,
				timeValue,
				DateUtils.nowUtc());
	}

	public RoleParameterValue(
			RoleParameter roleParameter,
			Boolean booleanValue,
			Date dateValue,
			Date datetimeValue,
			Double floatValue,
			Integer integerValue,
			String stringValue,
			Date timeValue,
			Date createdOn) {
		this.roleParameter = roleParameter;
		this.booleanValue = booleanValue;
		this.dateValue = dateValue;
		this.datetimeValue = datetimeValue;
		this.floatValue = floatValue;
		this.integerValue = integerValue;
		this.stringValue = stringValue;
		this.timeValue = timeValue;
		this.createdOn = (createdOn != null) ? createdOn : DateUtils.nowUtc();
	}

}
package com.qfree.obo.report.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import com.qfree.obo.report.configuration.Config.ParamName;
import com.qfree.obo.report.configuration.Config.ParamType;

/**
 * The persistent class for the "configuration" database table.
 * 
 * @author Jeffrey Zelt
 * 
 */
@Entity
@Table(name = "configuration", schema = "reporting",
		uniqueConstraints = {
				@UniqueConstraint(columnNames = { "param_name", "role_id" },
						name = "uc_configuration_paramname_role") })
@TypeDef(name = "uuid-custom", defaultForType = UUID.class, typeClass = UuidCustomType.class)
@NamedQuery(name = "Report.findByCreated", query = "select r from Report r order by r.createdOn desc")
public class Configuration implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@NotNull
	@Type(type = "uuid-custom")
	//	@Type(type = "pg-uuid")
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(name = "configuration_id", unique = true, nullable = false,
			columnDefinition = "uuid DEFAULT uuid_generate_v4()")
	private UUID configurationId;

	@ManyToOne
	/*
	 * If columnDefinition="uuid" is omitted here and the database schema is 
	 * created by Hibernate (via hibernate.hbm2ddl.auto="create"), then the 
	 * PostgreSQL column definition includes "DEFAULT uuid_generate_v4()", which
	 * is not what is wanted.
	 */
	/**
	 * Default parameter values correspond to roll == null.
	 */
	@JoinColumn(name = "role_id", nullable = true,
			foreignKey = @ForeignKey(name = "fk_configuration_role"),
			columnDefinition = "uuid")
	private Role role;

	@Column(name = "param_name", nullable = false, length = 64)
	@Enumerated(EnumType.STRING)
	private ParamName paramName;

	@Column(name = "param_type", nullable = false, length = 16)
	@Enumerated(EnumType.STRING)
	private ParamType paramType;

	@Column(name = "boolean_value", nullable = true)
	private Boolean booleanValue;

	@Column(name = "bytea_value", nullable = true)
	private byte[] byteaValue;

	@Temporal(TemporalType.DATE)
	@Column(name = "date_value", nullable = true)
	private Date dateValue;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "datetime_value", nullable = true)
	private Date datetimeValue;

	@Column(name = "double_value", nullable = true)
	private Double doubleValue;

	@Column(name = "float_value", nullable = true)
	private Float floatValue;

	@Column(name = "integer_value", nullable = true)
	private Integer integerValue;

	@Column(name = "long_value", nullable = true)
	private Long longValue;

	@Column(name = "string_value", nullable = true, length = 1000)
	private String stringValue;

	@Column(name = "text_value", nullable = true, columnDefinition = "text")
	private String textValue;

	@Temporal(TemporalType.TIME)
	@Column(name = "time_value", nullable = true)
	private Date timeValue;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_on", nullable = false)
	private Date createdOn;

	public Configuration() {
		this(null, null, null, new Date());
	}

	public Configuration(ParamName paramName) {
		this(paramName, null, paramName.paramType(), new Date());
	}

	public Configuration(ParamName paramName, Role role) {
		this(paramName, role, paramName.paramType(), new Date());
	}

	public Configuration(ParamName paramName, Role role, ParamType paramType, Date createdOn) {
		this.paramName = paramName;
		this.role = role;
		this.paramType = paramType;
		this.createdOn = createdOn;
	}

	public UUID getConfigurationId() {
		return this.configurationId;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public ParamName getParamName() {
		return paramName;
	}

	public void setParamName(ParamName paramName) {
		this.paramName = paramName;
	}

	public ParamType getParamType() {
		return paramType;
	}

	public void setParamType(ParamType paramType) {
		this.paramType = paramType;
	}

	public Boolean getBooleanValue() {
		return booleanValue;
	}

	public void setBooleanValue(Boolean booleanValue) {
		this.booleanValue = booleanValue;
	}

	public byte[] getByteaValue() {
		return byteaValue;
	}

	public void setByteaValue(byte[] byteaValue) {
		this.byteaValue = byteaValue;
	}

	public Date getDateValue() {
		return dateValue;
	}

	public void setDateValue(Date dateValue) {
		this.dateValue = dateValue;
	}

	public Date getDatetimeValue() {
		return datetimeValue;
	}

	public void setDatetimeValue(Date datetimeValue) {
		this.datetimeValue = datetimeValue;
	}

	public Double getDoubleValue() {
		return doubleValue;
	}

	public void setDoubleValue(Double doubleValue) {
		this.doubleValue = doubleValue;
	}

	public Float getFloatValue() {
		return floatValue;
	}

	public void setFloatValue(Float floatValue) {
		this.floatValue = floatValue;
	}

	public Integer getIntegerValue() {
		return integerValue;
	}

	public void setIntegerValue(Integer integerValue) {
		this.integerValue = integerValue;
	}

	public Long getLongValue() {
		return longValue;
	}

	public void setLongValue(Long longValue) {
		this.longValue = longValue;
	}

	public String getStringValue() {
		return this.stringValue;
	}

	public void setStringValue(String stringValue) {
		this.stringValue = stringValue;
	}

	public String getTextValue() {
		return textValue;
	}

	public void setTextValue(String textValue) {
		this.textValue = textValue;
	}

	public Date getTimeValue() {
		return timeValue;
	}

	public void setTimeValue(Date timeValue) {
		this.timeValue = timeValue;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Configuration [role=");
		builder.append(role);
		builder.append(", paramName=");
		builder.append(paramName);
		builder.append(", stringValue=");
		builder.append(stringValue);
		builder.append("]");
		return builder.toString();
	}

}
package com.qfree.obo.report.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.ws.rs.core.UriInfo;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.qfree.obo.report.domain.Configuration;
import com.qfree.obo.report.domain.Configuration.ParamName;
import com.qfree.obo.report.domain.Configuration.ParamType;
import com.qfree.obo.report.rest.server.RestUtils.RestApiVersion;

@XmlRootElement
public class ConfigurationResource extends AbstractBaseResource {

	@XmlElement
	@XmlJavaTypeAdapter(UuidAdapter.class)
	private UUID configurationId;

	@XmlElement(name = "role")
	private RoleResource roleResource;

	@XmlElement
	private ParamName paramName;

	// @XmlElement
	// private String value;

	@XmlElement
	private ParamType paramType;

	@XmlElement
	private Boolean booleanValue;

	@XmlElement
	private byte[] byteaValue;

	@XmlElement
	@XmlJavaTypeAdapter(DateAdapter.class)
	private Date dateValue;

	@XmlElement
	@XmlJavaTypeAdapter(DatetimeAdapter.class)
	private Date datetimeValue;

	@XmlElement
	private Double doubleValue;

	@XmlElement
	private Float floatValue;

	@XmlElement
	private Integer integerValue;

	@XmlElement
	private Long longValue;

	@XmlElement
	private String stringValue;

	@XmlElement
	private String textValue;

	@XmlElement
	@XmlJavaTypeAdapter(TimeAdapter.class)
	private Date timeValue;

	@XmlElement
	@XmlJavaTypeAdapter(DatetimeAdapter.class)
	private Date createdOn;

	/*
	 * Required by JAXB
	 */
	public ConfigurationResource() {
	}

	public ConfigurationResource(Configuration configuration, UriInfo uriInfo,
			Map<String, List<String>> queryParams, RestApiVersion apiVersion) {

		super(Configuration.class, configuration.getConfigurationId(), uriInfo, queryParams, apiVersion);

		List<String> expand = queryParams.get(ResourcePath.EXPAND_QP_KEY);

		String expandParam = ResourcePath.forEntity(Configuration.class).getExpandParam();
		if (expand.contains(expandParam)) {
			/*
			 * Make a copy of the "expand" list from which expandParam is
			 * removed. This list should be used when creating new resources
			 * here, instead of the original "expand" list. This is done to 
			 * avoid the unlikely event of a long list of chained expansions
			 * across relations.
			 */
			List<String> expandElementRemoved = new ArrayList<>(expand);
			expandElementRemoved.remove(expandParam);
			/*
			 * Make a copy of the original queryParams Map and then replace the 
			 * "expand" array with expandElementRemoved.
			 */
			Map<String, List<String>> newQueryParams = new HashMap<>(queryParams);
			newQueryParams.put(ResourcePath.EXPAND_QP_KEY, expandElementRemoved);

			/*
			 * Clear apiVersion since its current value is not necessarily
			 * applicable to any resources associated with fields of this class. 
			 * See ReportResource for a more detailed explanation.
			 */
			apiVersion = null;

			this.configurationId = configuration.getConfigurationId();
			this.paramName = configuration.getParamName();
			this.paramType = configuration.getParamType();
			this.booleanValue = configuration.getBooleanValue();
			this.byteaValue = configuration.getByteaValue();
			this.dateValue = configuration.getDateValue();
			this.datetimeValue = configuration.getDatetimeValue();
			this.doubleValue = configuration.getDoubleValue();
			this.floatValue = configuration.getFloatValue();
			this.integerValue = configuration.getIntegerValue();
			this.longValue = configuration.getLongValue();
			this.stringValue = configuration.getStringValue();
			this.textValue = configuration.getTextValue();
			this.timeValue = configuration.getTimeValue();
			this.createdOn = configuration.getCreatedOn();

			/*
			 * A Configuration may not be associated with a Role. This is the
			 * case for global defaults.
			 */
			if (configuration.getRole() != null) {
				this.roleResource = new RoleResource(configuration.getRole(),
						uriInfo, newQueryParams, apiVersion);
			}

			/*
			 * Set "value" from appropriate field based on this.paramName.paramType()????????????????????????????????????????????????????????
			 * No, set "value from configuration.getStringValue(), but this will not work for data type "bytea", 
			 * so I will need to check this.paramName.paramType() for this case??????????????????????????????????????????????????????????????
			 * 
			 * At the moment, I am not sure how to proceed. Just come back to this when I have fleshed out other
			 * resources and controller. It might be more obvious then. :-)
			 */
			//		this.value = configuration.getStringValue();
		}
	}

	public UUID getConfigurationId() {
		return configurationId;
	}

	public void setConfigurationId(UUID configurationId) {
		this.configurationId = configurationId;
	}

	public RoleResource getRoleResource() {
		return roleResource;
	}

	public void setRoleResource(RoleResource roleResource) {
		this.roleResource = roleResource;
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
		return stringValue;
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

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ConfigurationResource [configurationId=");
		builder.append(configurationId);
		builder.append(", paramName=");
		builder.append(paramName);
		builder.append(", paramType=");
		builder.append(paramType);
		builder.append(", booleanValue=");
		builder.append(booleanValue);
		builder.append(", byteaValue=");
		builder.append(Arrays.toString(byteaValue));
		builder.append(", dateValue=");
		builder.append(dateValue);
		builder.append(", datetimeValue=");
		builder.append(datetimeValue);
		builder.append(", doubleValue=");
		builder.append(doubleValue);
		builder.append(", floatValue=");
		builder.append(floatValue);
		builder.append(", integerValue=");
		builder.append(integerValue);
		builder.append(", longValue=");
		builder.append(longValue);
		builder.append(", stringValue=");
		builder.append(stringValue);
		builder.append(", textValue=");
		builder.append(textValue);
		builder.append(", timeValue=");
		builder.append(timeValue);
		builder.append(", createdOn=");
		builder.append(createdOn);
		builder.append("]");
		return builder.toString();
	}

}
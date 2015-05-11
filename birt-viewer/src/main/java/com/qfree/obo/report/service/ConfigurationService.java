package com.qfree.obo.report.service;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.qfree.obo.report.db.ConfigurationRepository;
import com.qfree.obo.report.domain.Configuration;
import com.qfree.obo.report.domain.Configuration.ParamName;
import com.qfree.obo.report.domain.Configuration.ParamType;
import com.qfree.obo.report.domain.Role;

@Component
@Transactional
public class ConfigurationService {

	private static final Logger logger = LoggerFactory.getLogger(ConfigurationService.class);

	private final ConfigurationRepository configurationRepository;

	@Autowired
	public ConfigurationService(ConfigurationRepository configurationRepository) {
		this.configurationRepository = configurationRepository;
	}

	@Transactional(readOnly = true)
	public Object get(ParamName paramName) {
		return get(paramName, null);
	}

	/**
	 * Generic version of {@code get} method that returns an object of the 
	 * appropriate type that does need casting.
	 * 
	 * @param paramName
	 * @param role
	 * @param c
	 * @return T paramValue
	 */
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	public <T> T get(ParamName paramName, Role role, Class<T> c) {
		Object object = get(paramName, role);
		T t = null;
		if (object != null) {
			//		try {
			t = (T) object;  // let calling code deal with possible exception
			//		} catch (ClassCastException e) {
			//			t = null;  // Just return null
			//		}
		}
		return t;
	}

	@Transactional(readOnly = true)
	public Object get(ParamName paramName, Role role) {
		/*
		 * First look for role-specific value if a Role is specified.
		 */
		Configuration configuration = null;
		Object object = null;
		if (role != null) {
			configuration = configurationRepository.findByParamName(paramName, role);
		}

		/*
		 * If no role-specific Configuration has been found for the specified 
		 * ParamName...
		 */
		if (configuration == null) {
			/* 
			 * ...Look for a global (role-independent) default Configuration.
			 * 
			 * Do *not* call findByParamName(paramName, role) here with 
			 * role=null. This will not work set comments where this query is
			 * defined in ConfigurationRepository.
			 */
			configuration = configurationRepository.findByParamName(paramName);
		}
		/*
		 * No, do not execute RestUtils.ifNullThen404. If configuration==null 
		 * here, this method should return null since that is its expected 
		 * behavior. This case is logged below so that we can monitor if/when
		 * it occurs.
		 */
		//RestUtils.ifNullThen404(configuration, Configuration.class, "paramName", paramName.toString());

		if (configuration != null) {
			/*
			 * Retrieve the parameter value using the appropriate getter based
			 * on the data type of the value as recorded in the Configuration
			 * entity. 
			 */
			switch (configuration.getParamType()) {
			case BOOLEAN:
				object = configuration.getBooleanValue();
				break;
			case BYTEARRAY:
				object = configuration.getByteaValue();
				break;
			case DATE:
				object = configuration.getDateValue();
				break;
			case DATETIME:
				object = configuration.getDatetimeValue();
				break;
			case DOUBLE:
				object = configuration.getDoubleValue();
				break;
			case FLOAT:
				object = configuration.getFloatValue();
				break;
			case INTEGER:
				object = configuration.getIntegerValue();
				break;
			case LONG:
				object = configuration.getLongValue();
				break;
			case STRING:
				object = configuration.getStringValue();
				break;
			case TEXT:
				object = configuration.getTextValue();
				break;
			case TIME:
				object = configuration.getTimeValue();
				break;
			default:
				logger.error("Untreated case. paramType = {}", configuration.getParamType());
				break;
			}

		} else {
			logger.error("A Configuration entity does not exist for paramName = {}", paramName);
		}

		return object;
	}

	@Transactional
	public void set(ParamName paramName, Object value) {
		set(paramName, value, null);
	}

	@Transactional
	public void set(ParamName paramName, Object value, Role role) {
		logger.info("paramName={}, value={}, role={}", paramName, value, role);
		/*
		 * Check if an existing Configuration does not exist, create one. Then
		 * update the Configuration with the specified value.
		 */
		Configuration configuration = null;
		if (role == null) {
			configuration = configurationRepository.findByParamName(paramName);
		} else {
			/*
			 * Do *not* call findByParamName(paramName, role) here with 
			 * role=null. This will not work set comments where this query is
			 * defined in ConfigurationRepository.
			 */
			configuration = configurationRepository.findByParamName(paramName, role);
		}
		if (configuration == null) {
			configuration = new Configuration(paramName, role);
		}

		switch (paramName.paramType()) {
		case BOOLEAN:
			configuration.setBooleanValue((Boolean) value);
			break;
		case BYTEARRAY:
			configuration.setByteaValue((byte[]) value);
			break;
		case DATE:
			configuration.setDateValue((Date) value);
			break;
		case DATETIME:
			configuration.setDatetimeValue((Date) value);
			break;
		case DOUBLE:
			configuration.setDoubleValue((Double) value);
			break;
		case FLOAT:
			configuration.setFloatValue((Float) value);
			break;
		case INTEGER:
			configuration.setIntegerValue((Integer) value);
			break;
		case LONG:
			configuration.setLongValue((Long) value);
			break;
		case STRING:
			configuration.setStringValue((String) value);
			break;
		case TEXT:
			configuration.setTextValue((String) value);
			break;
		case TIME:
			configuration.setTimeValue((Date) value);
			break;
		default:
			logger.error("Untreated case. paramType = {}", paramName.paramType());
			//TODO Throw a custom (or even a standard) exception here?
			break;
		}

		/*
		 * Set stringValue field for non-String parameters. This allows
		 * information for all parameters to be displayed using just the 
		 * stringValue field.
		 */
		if (paramName.paramType() != ParamType.STRING) {
			switch (paramName.paramType()) {
			case BYTEARRAY:
				configuration.setStringValue(String.valueOf(configuration.getByteaValue().length) + " bytes");
				break;
			case TEXT:
				String textValue = configuration.getTextValue();
				if (textValue.length() > 40) {
					textValue = textValue.substring(0, 40) + "...";
				}
				configuration.setStringValue(textValue);
				break;
			default:
				configuration.setStringValue(value.toString());
				break;
			}
		}

		configurationRepository.save(configuration);
	}
}

package com.qfree.obo.report.db;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;

import org.joda.time.LocalTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.qfree.obo.report.configuration.Config;
import com.qfree.obo.report.configuration.Config.ParamName;
import com.qfree.obo.report.domain.Configuration;
import com.qfree.obo.report.domain.Role;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PersistenceConfigTestEnv.class)
public class ConfigurationRepositoryTest {

	private static final Logger logger = LoggerFactory.getLogger(ConfigurationRepositoryTest.class);

	private static final long NUM_TEST_CONFIGURATIONS = 18L;  // number of test [configuration] records

	/*
	 * Default values (role_id = null) in the test [configuration] records.
	 */
	private static final Boolean TEST_BOOLEAN_DEFAULT_VALUE=true;
	private static final byte[] TEST_BYTEARRAY_DEFAULT_VALUE=null;
	private static final Date TEST_DATE_DEFAULT_VALUE = new GregorianCalendar(1958, 4, 6).getTime();
	private static final Date TEST_DATETIME_DEFAULT_VALUE = new GregorianCalendar(2008, 10, 29, 01, 0, 0).getTime();
	private static final Float TEST_FLOAT_DEFAULT_VALUE=3.14159F;
	private static final Integer TEST_INTEGER_DEFAULT_VALUE=42;
	private static final String TEST_STRING_DEFAULT_VALUE="Meaning of life";
	private static final String TEST_TEXT_DEFAULT_VALUE="Meaning of life - really";
	// The year, month and day here are arbitrary and used only to construct a Date.
	private static final Date TEST_TIME_DEFAULT_VALUE = new GregorianCalendar(2000, 0, 1, 16, 17, 18).getTime();
	private static final LocalTime TEST_TIME_DEFAULT_VALUE_JODA = new LocalTime(TEST_TIME_DEFAULT_VALUE);
	/*
	 * Role-specific values for Role "aabb" in the test [configuration] records.
	 */

	@Autowired
	ConfigurationRepository configurationRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	Config config;

	@Test
	@Transactional
	public void countTestRecords() {
		assertThat(configurationRepository.count(), is(NUM_TEST_CONFIGURATIONS));
		assertThat(configurationRepository.count(), is(equalTo(NUM_TEST_CONFIGURATIONS)));
	}

	// ======================== Date parameter tests ===========================

	@Test
	@Transactional
	public void dateValueExistsDefault() {
		UUID uuidOfDefaultDateValueConfiguration = UUID.fromString("7d325b3c-d307-4fd0-bdae-c349ec2d4835");
		Configuration defaultDateValueConfiguration = configurationRepository
				.findOne(uuidOfDefaultDateValueConfiguration);
		assertThat(defaultDateValueConfiguration, is(not(nullValue())));
		//		System.out.println("defaultDateValueConfiguration.getDateValue() = "
		//				+ defaultDateValueConfiguration.getDateValue());
		//		System.out.println("TEST_DATE_DEFAULT_VALUE = " + TEST_DATE_DEFAULT_VALUE);
		/*
		 * TEST_DATE_DEFAULT_VALUE has time zone information, but the 
		 * datetime from the Configuration does not. In order to compare the 
		 * datetime from the Configuration (for this unit test), we create a 
		 * new date from the date retrieved from the Configuration and then
		 * compared *that* Date WITH TEST_DATE_DEFAULT_VALUE.
		 */
		Date dateFromConfig = defaultDateValueConfiguration.getDateValue();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(dateFromConfig);
		Date dateWithTimeZone = calendar.getTime();
		assertThat(dateWithTimeZone, is(equalTo(TEST_DATE_DEFAULT_VALUE)));
		//		assertThat(defaultDateValueConfiguration.getDateValue(), is(equalTo(TEST_DATE_DEFAULT_VALUE)));
	}

	@Test
	@Transactional
	public void dateValueFetchDefault() {
		//		Configuration configuration = configurationRepository.findByParamName(ParamName.TEST_DATE.toString());
		Configuration configuration = configurationRepository.findByParamName(ParamName.TEST_DATE);
		assertThat(configuration, is(not(nullValue())));
		Date dateFromConfig = configuration.getDateValue();
		assertThat(dateFromConfig, is(not(nullValue())));
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(dateFromConfig);
		Date dateWithTimeZone = calendar.getTime();
		assertThat(dateWithTimeZone, is(equalTo(TEST_DATE_DEFAULT_VALUE)));
	}

	@Test
	@Transactional
	public void dateValueFetchDefaultFromConfig() {
		Object dateValueObject = config.get(ParamName.TEST_DATE);
		assertThat(dateValueObject, is(instanceOf(Date.class)));
		Date dateFromConfig = (Date) dateValueObject;
		assertThat(dateFromConfig, is(not(nullValue())));
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(dateFromConfig);
		Date dateWithTimeZone = calendar.getTime();
		assertThat(dateWithTimeZone, is(equalTo(TEST_DATE_DEFAULT_VALUE)));
		//		assertThat((Date) dateValueObject, is(TEST_INTEGER_DEFAULT_VALUE));
	}

	@Test
	@Transactional
	public void dateValueFetchNonexistentRoleSpecificFromConfig() {
		UUID uuidOfRole_bcbc = UUID.fromString("e918c8aa-c6d1-462c-9e91-f1db0fb9f346");
		Role role_bcbc = roleRepository.findOne(uuidOfRole_bcbc);
		assertThat(role_bcbc, is(notNullValue()));
		/*
		 * There is a default value for ParamName.TEST_DATE, but no
		 * role-specific value for Role "bcbc".
		 */
		Object dateValueObject = config.get(ParamName.TEST_DATE, role_bcbc);
		assertThat(dateValueObject, is(instanceOf(Date.class)));
		Date dateFromConfig = (Date) dateValueObject;
		assertThat(dateFromConfig, is(not(nullValue())));
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(dateFromConfig);
		Date dateWithTimeZone = calendar.getTime();
		assertThat(dateWithTimeZone, is(equalTo(TEST_DATE_DEFAULT_VALUE)));
		//		assertThat((Date) dateValueObject, is(TEST_INTEGER_DEFAULT_VALUE));
	}

	/*
	 * Set default date value for a parameter that already has a default 
	 * (Role==null) Configuration. This should update the existing Configuration
	 * entity to store this new value.
	 */
	@Test
	@Transactional
	public void dateValueSetDefaultWithConfig() {
		assertThat(configurationRepository.count(), is(NUM_TEST_CONFIGURATIONS));
		/*
		 * Retrieve the existing default value.
		 */
		Object dateValueObject = config.get(ParamName.TEST_DATE);
		assertThat(dateValueObject, is(instanceOf(Date.class)));
		Date dateFromConfig = (Date) dateValueObject;
		assertThat(dateFromConfig, is(not(nullValue())));
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(dateFromConfig);
		Date dateWithTimeZone = calendar.getTime();
		assertThat(dateWithTimeZone, is(equalTo(TEST_DATE_DEFAULT_VALUE)));
		//		assertThat((Date) dateValueObject, is(TEST_DATE_DEFAULT_VALUE));
		/*
		 * Update default value.
		 */
		Date newDateValue = new GregorianCalendar(1927, 1, 15).getTime();
		config.set(ParamName.TEST_DATE, newDateValue);
		/*
		 * The existing Configuration should have been updated, so there should
		 * be the same number of entities in the database
		 */
		assertThat(configurationRepository.count(), is(NUM_TEST_CONFIGURATIONS));
		/* 
		 * Retrieve value just set.
		 */
		Object dateValueObjectUpdated = config.get(ParamName.TEST_DATE);
		assertThat(dateValueObjectUpdated, is(instanceOf(Date.class)));
		Date dateFromConfigUpdated = (Date) dateValueObjectUpdated;
		assertThat(dateFromConfigUpdated, is(not(nullValue())));
		Calendar calendarUpdated = new GregorianCalendar();
		calendarUpdated.setTime(dateFromConfigUpdated);
		Date dateWithTimeZoneUpdated = calendarUpdated.getTime();
		assertThat(dateWithTimeZoneUpdated, is(equalTo(newDateValue)));
	}

	/*
	 * Set role-specific date value for a parameter that has a default 
	 * (Role==null) Configuration, but no role-specific value. This should
	 * create a new Configuration entity to store this value.
	 */
	@Test
	@Transactional
	public void dateValueSetRoleSpecificWithConfig() {
		assertThat(configurationRepository.count(), is(NUM_TEST_CONFIGURATIONS));

		UUID uuidOfRole_bcbc = UUID.fromString("e918c8aa-c6d1-462c-9e91-f1db0fb9f346");
		Role role_bcbc = roleRepository.findOne(uuidOfRole_bcbc);
		assertThat(role_bcbc, is(notNullValue()));
		/*
		 * There is a default value for ParamName.TEST_DATE, but no
		 * role-specific value for Role "bcbc".
		 */
		Object dateValueObject = config.get(ParamName.TEST_DATE, role_bcbc);
		assertThat(dateValueObject, is(instanceOf(Date.class)));
		Date dateFromConfig = (Date) dateValueObject;
		assertThat(dateFromConfig, is(not(nullValue())));
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(dateFromConfig);
		Date dateWithTimeZone = calendar.getTime();
		assertThat(dateWithTimeZone, is(equalTo(TEST_DATE_DEFAULT_VALUE)));
		//		assertThat((Date) dateValueObject, is(TEST_DATE_DEFAULT_VALUE));

		/*
		 * Set role-specific value, which should override the default value for
		 * the specified role.
		 */
		Date newDateValue = new GregorianCalendar(1932, 3, 17).getTime();
		config.set(ParamName.TEST_DATE, newDateValue, role_bcbc);
		/*
		 * This should have created a new Configuration.
		 */
		assertThat(configurationRepository.count(), is(NUM_TEST_CONFIGURATIONS + 1));
		/*
		 * Retrieve value just set. The new value should override the default.
		 */
		Object dateValueRoleSpecificObject = config.get(ParamName.TEST_DATE, role_bcbc);
		assertThat(dateValueRoleSpecificObject, is(instanceOf(Date.class)));
		Date dateFromRoleSpecific = (Date) dateValueRoleSpecificObject;
		assertThat(dateFromRoleSpecific, is(not(nullValue())));
		Calendar calendarUpdated = new GregorianCalendar();
		calendarUpdated.setTime(dateFromRoleSpecific);
		Date dateWithTimeZoneUpdated = calendarUpdated.getTime();
		assertThat(dateWithTimeZoneUpdated, is(equalTo(newDateValue)));
		//		assertThat((Date) dateValueRoleSpecificObject, is(newDateValue));

		/*
		 * Update the role-specific value. This should override the default 
		 * value for the specified role and RE-USE the new role-specific
		 * Configuration just created, i.e., not create a new Configuration
		 */
		newDateValue = new GregorianCalendar(1955, 2, 24).getTime();
		config.set(ParamName.TEST_DATE, newDateValue, role_bcbc);
		/*
		 * This should have created a new Configuration.
		 */
		assertThat(configurationRepository.count(), is(NUM_TEST_CONFIGURATIONS + 1));
		/*
		 * Retrieve value just set. The new value should override the default.
		 */
		dateValueRoleSpecificObject = config.get(ParamName.TEST_DATE, role_bcbc);
		assertThat(dateValueRoleSpecificObject, is(instanceOf(Date.class)));
		dateFromRoleSpecific = (Date) dateValueRoleSpecificObject;
		assertThat(dateFromRoleSpecific, is(not(nullValue())));
		calendarUpdated = new GregorianCalendar();
		calendarUpdated.setTime(dateFromRoleSpecific);
		dateWithTimeZoneUpdated = calendarUpdated.getTime();
		assertThat(dateWithTimeZoneUpdated, is(equalTo(newDateValue)));
		//		assertThat((Date) dateValueRoleSpecificObject, is(newDateValue));

		/*
		 * The default value for ParamName.TEST_DATE should still be the
		 * same.
		 */
		Object dateValueDefaultObject = config.get(ParamName.TEST_DATE);
		assertThat(dateValueDefaultObject, is(instanceOf(Date.class)));
		dateFromConfig = (Date) dateValueDefaultObject;
		assertThat(dateFromConfig, is(not(nullValue())));
		calendar = new GregorianCalendar();
		calendar.setTime(dateFromConfig);
		dateWithTimeZone = calendar.getTime();
		assertThat(dateWithTimeZone, is(equalTo(TEST_DATE_DEFAULT_VALUE)));
	}

	// ====================== Datetime parameter tests =========================

	@Test
	@Transactional
	public void datetimeValueExistsDefault() {
		UUID uuidOfDefaultDatetimeValueConfiguration = UUID.fromString("5e7d5a1e-5d42-4a9c-b790-e45e545463f7");
		Configuration defaultDatetimeValueConfiguration = configurationRepository
				.findOne(uuidOfDefaultDatetimeValueConfiguration);
		assertThat(defaultDatetimeValueConfiguration, is(not(nullValue())));
		//		System.out.println("defaultDatetimeValueConfiguration.getDatetimeValue() = "
		//				+ defaultDatetimeValueConfiguration.getDatetimeValue());
		//		System.out.println("TEST_DATETIME_DEFAULT_VALUE = " + TEST_DATETIME_DEFAULT_VALUE);
		/*
		 * TEST_DATETIME_DEFAULT_VALUE has time zone information, but the 
		 * datetime from the Configuration does not. In order to compare the 
		 * datetime from the Configuration (for this unit test), we create a 
		 * new date from the date retrieved from the Configuration and then
		 * compared *that* Date WITH TEST_DATETIME_DEFAULT_VALUE.
		 */
		Date datetimeFromConfig = defaultDatetimeValueConfiguration.getDatetimeValue();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(datetimeFromConfig);
		Date dateWithTimeZone = calendar.getTime();
		assertThat(dateWithTimeZone, is(equalTo(TEST_DATETIME_DEFAULT_VALUE)));
		//		assertThat(defaultDatetimeValueConfiguration.getDatetimeValue(), is(equalTo(TEST_DATETIME_DEFAULT_VALUE)));
	}

	/*
	 * Set default datetime value for a parameter that already has a default 
	 * (Role==null) Configuration. This should update the existing Configuration
	 * entity to store this new value.
	 */

	@Test
	@Transactional
	public void datetimeValueSetDefaultWithConfig() {
		assertThat(configurationRepository.count(), is(NUM_TEST_CONFIGURATIONS));
		/*
		 * Retrieve the existing default value.
		 */
		Object datetimeValueObject = config.get(ParamName.TEST_DATETIME);
		assertThat(datetimeValueObject, is(instanceOf(Date.class)));
		Date datetimeFromConfig = (Date) datetimeValueObject;
		assertThat(datetimeFromConfig, is(not(nullValue())));
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(datetimeFromConfig);
		Date datetimeWithTimeZone = calendar.getTime();
		assertThat(datetimeWithTimeZone, is(equalTo(TEST_DATETIME_DEFAULT_VALUE)));
		/*
		 * Update default value.
		 */
		Date newDatetimeValue = new GregorianCalendar(1960, 4, 14, 1, 2, 3).getTime();
		config.set(ParamName.TEST_DATETIME, newDatetimeValue);
		/*
		 * The existing Configuration should have been updated, so there should
		 * be the same number of entities in the database
		 */
		assertThat(configurationRepository.count(), is(NUM_TEST_CONFIGURATIONS));
		/* 
		 * Retrieve value just set.
		 */
		Object datetimeValueObjectUpdated = config.get(ParamName.TEST_DATETIME);
		assertThat(datetimeValueObjectUpdated, is(instanceOf(Date.class)));
		Date datetimeFromConfigUpdated = (Date) datetimeValueObjectUpdated;
		assertThat(datetimeFromConfigUpdated, is(not(nullValue())));
		Calendar calendarUpdated = new GregorianCalendar();
		calendarUpdated.setTime(datetimeFromConfigUpdated);
		Date datetimeWithTimeZoneUpdated = calendarUpdated.getTime();
		assertThat(datetimeWithTimeZoneUpdated, is(equalTo(newDatetimeValue)));
	}

	/*
	 * Set role-specific datetime value for a parameter that has a default 
	 * (Role==null) Configuration, but no role-specific value. This should
	 * create a new Configuration entity to store this value.
	 */
	@Test
	@Transactional
	public void datetimeValueSetRoleSpecificWithConfig() {
		assertThat(configurationRepository.count(), is(NUM_TEST_CONFIGURATIONS));

		UUID uuidOfRole_bcbc = UUID.fromString("e918c8aa-c6d1-462c-9e91-f1db0fb9f346");
		Role role_bcbc = roleRepository.findOne(uuidOfRole_bcbc);
		assertThat(role_bcbc, is(notNullValue()));
		/*
		 * There is a default value for ParamName.TEST_DATETIME, but no
		 * role-specific value for Role "bcbc".
		 */
		Object datetimeValueObject = config.get(ParamName.TEST_DATETIME, role_bcbc);
		assertThat(datetimeValueObject, is(instanceOf(Date.class)));
		Date datetimeFromConfig = (Date) datetimeValueObject;
		assertThat(datetimeFromConfig, is(not(nullValue())));
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(datetimeFromConfig);
		Date datetimeWithTimeZone = calendar.getTime();
		assertThat(datetimeWithTimeZone, is(equalTo(TEST_DATETIME_DEFAULT_VALUE)));

		/*
		 * Set role-specific value, which should override the default value for
		 * the specified role.
		 */
		Date newDatetimeValue = new GregorianCalendar(1960, 4, 14, 1, 2, 3).getTime();
		config.set(ParamName.TEST_DATETIME, newDatetimeValue, role_bcbc);
		/*
		 * This should have created a new Configuration.
		 */
		assertThat(configurationRepository.count(), is(NUM_TEST_CONFIGURATIONS + 1));
		/*
		 * Retrieve value just set. The new value should override the default.
		 */
		Object datetimeValueRoleSpecificObject = config.get(ParamName.TEST_DATETIME, role_bcbc);
		assertThat(datetimeValueRoleSpecificObject, is(instanceOf(Date.class)));
		Date datetimeFromRoleSpecific = (Date) datetimeValueRoleSpecificObject;
		assertThat(datetimeFromRoleSpecific, is(not(nullValue())));
		Calendar calendarUpdated = new GregorianCalendar();
		calendarUpdated.setTime(datetimeFromRoleSpecific);
		Date datetimeWithTimeZoneUpdated = calendarUpdated.getTime();
		assertThat(datetimeWithTimeZoneUpdated, is(equalTo(newDatetimeValue)));

		/*
		 * Update the role-specific value. This should override the default 
		 * value for the specified role and RE-USE the new role-specific
		 * Configuration just created, i.e., not create a new Configuration
		 */
		newDatetimeValue = new GregorianCalendar(1961, 10, 4, 18, 30, 15).getTime();
		config.set(ParamName.TEST_DATETIME, newDatetimeValue, role_bcbc);
		/*
		 * This should have created a new Configuration.
		 */
		assertThat(configurationRepository.count(), is(NUM_TEST_CONFIGURATIONS + 1));
		/*
		 * Retrieve value just set. The new value should override the default.
		 */
		datetimeValueRoleSpecificObject = config.get(ParamName.TEST_DATETIME, role_bcbc);
		assertThat(datetimeValueRoleSpecificObject, is(instanceOf(Date.class)));
		datetimeFromRoleSpecific = (Date) datetimeValueRoleSpecificObject;
		assertThat(datetimeFromRoleSpecific, is(not(nullValue())));
		calendarUpdated = new GregorianCalendar();
		calendarUpdated.setTime(datetimeFromRoleSpecific);
		datetimeWithTimeZoneUpdated = calendarUpdated.getTime();
		assertThat(datetimeWithTimeZoneUpdated, is(equalTo(newDatetimeValue)));

		/*
		 * The default value for ParamName.TEST_DATETIME should still be the
		 * same.
		 */
		Object datetimeValueDefaultObject = config.get(ParamName.TEST_DATETIME);
		assertThat(datetimeValueDefaultObject, is(instanceOf(Date.class)));
		datetimeFromConfig = (Date) datetimeValueDefaultObject;
		assertThat(datetimeFromConfig, is(not(nullValue())));
		calendar = new GregorianCalendar();
		calendar.setTime(datetimeFromConfig);
		datetimeWithTimeZone = calendar.getTime();
		assertThat(datetimeWithTimeZone, is(equalTo(TEST_DATETIME_DEFAULT_VALUE)));
	}

	// ======================== Integer parameter tests ========================

	@Test
	@Transactional
	public void integerValueExistsDefault() {
		UUID uuidOfDefaultIntegerValueConfiguration = UUID.fromString("b4fd2271-26fb-4db7-bfe7-07211d849364");
		Configuration defaultIntegerValueConfiguration = configurationRepository
				.findOne(uuidOfDefaultIntegerValueConfiguration);
		assertThat(defaultIntegerValueConfiguration, is(not(nullValue())));
		assertThat(defaultIntegerValueConfiguration.getIntegerValue(), is(TEST_INTEGER_DEFAULT_VALUE));
	}

	@Test
	@Transactional
	public void integerValueFetchDefault() {
		//		Configuration configuration = configurationRepository.findByParamName(ParamName.TEST_INTEGER.toString());
		Configuration configuration = configurationRepository.findByParamName(ParamName.TEST_INTEGER);
		assertThat(configuration, is(not(nullValue())));
		Integer integerValue = configuration.getIntegerValue();
		assertThat(integerValue, is(not(nullValue())));
		assertThat(integerValue, is(TEST_INTEGER_DEFAULT_VALUE));
	}

	@Test
	@Transactional
	public void integerValueFetchDefaultFromConfig() {
		Object integerValueObject = config.get(ParamName.TEST_INTEGER);
		assertThat(integerValueObject, is(instanceOf(Integer.class)));
		assertThat((Integer) integerValueObject, is(TEST_INTEGER_DEFAULT_VALUE));
	}

	@Test
	@Transactional
	public void integerValueFetchNonexistentRoleSpecificFromConfig() {
		UUID uuidOfRole_bcbc = UUID.fromString("e918c8aa-c6d1-462c-9e91-f1db0fb9f346");
		Role role_bcbc = roleRepository.findOne(uuidOfRole_bcbc);
		assertThat(role_bcbc, is(notNullValue()));
		/*
		 * There is a default value for ParamName.TEST_INTEGER, but no
		 * role-specific value for Role "bcbc".
		 */
		Object integerValueObject = config.get(ParamName.TEST_INTEGER, role_bcbc);
		assertThat(integerValueObject, is(instanceOf(Integer.class)));
		assertThat((Integer) integerValueObject, is(TEST_INTEGER_DEFAULT_VALUE));
	}

	/*
	 * Set default integer value for a parameter that already has a default 
	 * (Role==null) Configuration. This should update the existing Configuration
	 * entity to store this new value.
	 */
	@Test
	@Transactional
	public void integerValueSetDefaultWithConfig() {
		assertThat(configurationRepository.count(), is(NUM_TEST_CONFIGURATIONS));
		/*
		 * Retrieve the existing default value.
		 */
		Object integerValueDefaultObject = config.get(ParamName.TEST_INTEGER);
		assertThat(integerValueDefaultObject, is(instanceOf(Integer.class)));
		assertThat((Integer) integerValueDefaultObject, is(TEST_INTEGER_DEFAULT_VALUE));
		/*
		 * Update default value.
		 */
		Integer newIntegerValue = 123456;
		config.set(ParamName.TEST_INTEGER, newIntegerValue);
		/*
		 * The existing Configuration should have been updated, so there should
		 * be the same number of entities in the database
		 */
		assertThat(configurationRepository.count(), is(NUM_TEST_CONFIGURATIONS));
		/* 
		 * Retrieve value just set.
		 */
		Object integerValueNewDefaultObject = config.get(ParamName.TEST_INTEGER);
		assertThat(integerValueNewDefaultObject, is(instanceOf(Integer.class)));
		assertThat((Integer) integerValueNewDefaultObject, is(newIntegerValue));
	}

	/*
	 * Set role-specific integer value for a parameter that has a default 
	 * (Role==null) Configuration, but no role-specific value. This should
	 * create a new Configuration entity to store this value.
	 */
	@Test
	@Transactional
	public void integerValueSetRoleSpecificWithConfig() {
		assertThat(configurationRepository.count(), is(NUM_TEST_CONFIGURATIONS));

		UUID uuidOfRole_bcbc = UUID.fromString("e918c8aa-c6d1-462c-9e91-f1db0fb9f346");
		Role role_bcbc = roleRepository.findOne(uuidOfRole_bcbc);
		assertThat(role_bcbc, is(notNullValue()));
		/*
		 * There is a default value for ParamName.TEST_INTEGER, but no
		 * role-specific value for Role "bcbc".
		 */
		Object integerValueObject = config.get(ParamName.TEST_INTEGER, role_bcbc);
		assertThat(integerValueObject, is(instanceOf(Integer.class)));
		assertThat((Integer) integerValueObject, is(TEST_INTEGER_DEFAULT_VALUE));

		/*
		 * Set role-specific value, which should override the default value for
		 * the specified role.
		 */
		Integer newIntegerValue = 1000001;
		config.set(ParamName.TEST_INTEGER, newIntegerValue, role_bcbc);
		/*
		 * This should have created a new Configuration.
		 */
		assertThat(configurationRepository.count(), is(NUM_TEST_CONFIGURATIONS + 1));
		/*
		 * Retrieve value just set. The new value should override the default.
		 */
		Object integerValueRoleSpecificObject = config.get(ParamName.TEST_INTEGER, role_bcbc);
		assertThat(integerValueRoleSpecificObject, is(instanceOf(Integer.class)));
		assertThat((Integer) integerValueRoleSpecificObject, is(newIntegerValue));

		/*
		 * Update the role-specific value. This should override the default 
		 * value for the specified role and RE-USE the new role-specific
		 * Configuration just created, i.e., not create a new Configuration
		 */
		newIntegerValue = 666666;
		config.set(ParamName.TEST_INTEGER, newIntegerValue, role_bcbc);
		/*
		 * This should have re-used the Configuration just created.
		 */
		assertThat(configurationRepository.count(), is(NUM_TEST_CONFIGURATIONS + 1));
		/*
		 * Retrieve value just set. The new value should override the default.
		 */
		integerValueRoleSpecificObject = config.get(ParamName.TEST_INTEGER, role_bcbc);
		assertThat(integerValueRoleSpecificObject, is(instanceOf(Integer.class)));
		assertThat((Integer) integerValueRoleSpecificObject, is(newIntegerValue));

		/*
		 * The default value for ParamName.TEST_INTEGER should still be the
		 * same.
		 */
		Object integerValueDefaultObject = config.get(ParamName.TEST_INTEGER);
		assertThat(integerValueDefaultObject, is(instanceOf(Integer.class)));
		assertThat((Integer) integerValueDefaultObject, is(TEST_INTEGER_DEFAULT_VALUE));
	}

	// ======================== String parameter tests =========================

	@Test
	@Transactional
	public void stringValueExistsDefault() {
		UUID uuidOfDefaultStringValueConfiguration = UUID.fromString("62f9ca07-79ce-48dd-8357-873191ea8b9d");
		Configuration defaultStringValueConfiguration = configurationRepository
				.findOne(uuidOfDefaultStringValueConfiguration);
		assertThat(defaultStringValueConfiguration, is(not(nullValue())));
		assertThat(defaultStringValueConfiguration.getStringValue(), is("Meaning of life"));
	}

	@Test
	@Transactional
	public void stringValueFetchDefault() {
		//		Configuration configuration = configurationRepository.findByParamName(ParamName.TEST_STRING.toString());
		Configuration configuration = configurationRepository.findByParamName(ParamName.TEST_STRING);
		assertThat(configuration, is(not(nullValue())));
		String stringValue = configuration.getStringValue();
		assertThat(stringValue, is(not(nullValue())));
		assertThat(stringValue, is("Meaning of life"));
	}

	//	@Test
	//	@Transactional
	//	public void stringValueFetchDefaultFromConfig() {
	//		Object stringValueObject = Config.get(ParamName.TEST_STRING);
	//		assertThat(stringValueObject, is(instanceOf(String.class)));
	//		assertThat((String) stringValueObject, is("Meaning of life"));
	//	}

	@Test
	@Transactional
	public void stringValueFetchDefaultFromConfig() {
		Object stringValueObject = config.get(ParamName.TEST_STRING);
		assertThat(stringValueObject, is(instanceOf(String.class)));
		assertThat((String) stringValueObject, is("Meaning of life"));
	}

	/*
	 * Attempt to fetch a role-specific value value that does not exist in the
	 * [configuration] table. However, there *is* a default value for the 
	 * parameter, which should be returned instead.
	 */
	@Test
	@Transactional
	public void stringValueFetchNonexistentRoleSpecificFromConfig() {
		UUID uuidOfRole_bcbc = UUID.fromString("e918c8aa-c6d1-462c-9e91-f1db0fb9f346");
		Role role_bcbc = roleRepository.findOne(uuidOfRole_bcbc);
		assertThat(role_bcbc, is(notNullValue()));
		/*
		 * There is a default value for ParamName.TEST_STRING, but no
		 * role-specific value for Role "bcbc".
		 */
		String defaultStringValue = "Meaning of life";
		Object stringValueObject = config.get(ParamName.TEST_STRING, role_bcbc);
		assertThat(stringValueObject, is(instanceOf(String.class)));
		assertThat((String) stringValueObject, is(defaultStringValue));
	}

	/*
	 * Attempt to fetch a default (Role==null) value that does not exist in the
	 * [configuration] table. This should return null for the parameter value.
	 * An error should also be logged, but we do not check that here.
	 */
	@Test
	@Transactional
	public void stringValueFetchNonexistentDefaultFromConfig() {
		/*
		 * Parameter ParamName.TEST_NOTSET does not exist in the test data for
		 * the [configuration] table.
		 */
		Object stringValueObject = config.get(ParamName.TEST_NOTSET);
		assertThat(stringValueObject, is(nullValue()));
	}

	/*
	 * Set default string value for a parameter that already has a default 
	 * (Role==null) Configuration. This should update the existing Configuration
	 * entity to store this new value.
	 */
	@Test
	@Transactional
	public void stringValueSetDefaultWithConfig() {
		assertThat(configurationRepository.count(), is(NUM_TEST_CONFIGURATIONS));
		/*
		 * Retrieve the existing default value.
		 */
		String defaultStringValue = "Meaning of life";
		Object stringValueDefaultObject = config.get(ParamName.TEST_STRING);
		assertThat(stringValueDefaultObject, is(instanceOf(String.class)));
		assertThat((String) stringValueDefaultObject, is(defaultStringValue));
		/*
		 * Update default value.
		 */
		String newStringValue = "New default value";
		config.set(ParamName.TEST_STRING, newStringValue);
		/*
		 * The existing Configuration should have been updated, so there should
		 * be the same number of entities in the database
		 */
		assertThat(configurationRepository.count(), is(NUM_TEST_CONFIGURATIONS));
		/* 
		 * Retrieve value just set.
		 */
		Object stringValueNewDefaultObject = config.get(ParamName.TEST_STRING);
		assertThat(stringValueNewDefaultObject, is(instanceOf(String.class)));
		assertThat((String) stringValueNewDefaultObject, is(newStringValue));
	}

	/*
	 * Set role-specific string value for a parameter that has a default 
	 * (Role==null) Configuration, but no role-specific value. This should
	 * create a new Configuration entity to store this value.
	 */
	@Test
	@Transactional
	public void stringValueSetRoleSpecificWithConfig() {
		assertThat(configurationRepository.count(), is(NUM_TEST_CONFIGURATIONS));

		UUID uuidOfRole_bcbc = UUID.fromString("e918c8aa-c6d1-462c-9e91-f1db0fb9f346");
		Role role_bcbc = roleRepository.findOne(uuidOfRole_bcbc);
		assertThat(role_bcbc, is(notNullValue()));
		/*
		 * There is a default value for ParamName.TEST_STRING, but no
		 * role-specific value for Role "bcbc".
		 */
		String defaultStringValue = "Meaning of life";
		Object stringValueObject = config.get(ParamName.TEST_STRING, role_bcbc);
		assertThat(stringValueObject, is(instanceOf(String.class)));
		assertThat((String) stringValueObject, is(defaultStringValue));

		/*
		 * Set role-specific value, which should override the default value for
		 * the specified role.
		 */
		String newStringValue = "String value for role 'bcbc'";
		config.set(ParamName.TEST_STRING, newStringValue, role_bcbc);
		/*
		 * This should have created a new Configuration.
		 */
		assertThat(configurationRepository.count(), is(NUM_TEST_CONFIGURATIONS + 1));
		/*
		 * Retrieve value just set. The new value should override the default.
		 */
		Object stringValueRoleSpecificObject = config.get(ParamName.TEST_STRING, role_bcbc);
		assertThat(stringValueRoleSpecificObject, is(instanceOf(String.class)));
		assertThat((String) stringValueRoleSpecificObject, is(newStringValue));

		/*
		 * Update the role-specific value. This should override the default 
		 * value for the specified role and RE-USE the new role-specific
		 * Configuration just created, i.e., not create a new Configuration
		 */
		/*
		 * Set role-specific value, which should override the default value for
		 * the specified role.
		 */
		newStringValue = "Updated role-specific value";
		config.set(ParamName.TEST_STRING, newStringValue, role_bcbc);
		/*
		 * This should have re-used the Configuration just created.
		 */
		assertThat(configurationRepository.count(), is(NUM_TEST_CONFIGURATIONS + 1));
		/*
		 * Retrieve value just set. The new value should override the default.
		 */
		stringValueRoleSpecificObject = config.get(ParamName.TEST_STRING, role_bcbc);
		assertThat(stringValueRoleSpecificObject, is(instanceOf(String.class)));
		assertThat((String) stringValueRoleSpecificObject, is(newStringValue));

		/*
		 * The default value for ParamName.TEST_STRING should still be the
		 * same.
		 */
		Object stringValueDefaultObject = config.get(ParamName.TEST_STRING);
		assertThat(stringValueDefaultObject, is(instanceOf(String.class)));
		assertThat((String) stringValueDefaultObject, is(defaultStringValue));
	}

	// ======================== Time parameter tests ===========================

	@Test
	@Transactional
	public void timeValueExistsDefault() {
		UUID uuidOfDefaultTimeValueConfiguration = UUID.fromString("e0537e8a-62f3-4240-ba19-da0d5005092e");
		Configuration defaultTimeValueConfiguration = configurationRepository
				.findOne(uuidOfDefaultTimeValueConfiguration);
		assertThat(defaultTimeValueConfiguration, is(not(nullValue())));
		/*
		 * Convert the time returned from the Configuration to Joda time so
		 * that it can be compared to TEST_TIME_DEFAULT_VALUE_JODA. Joda time
		 * is used here because Java 7 does not have convenient methods for 
		 * dealing with times that are not associated with an instant in time) 
		 * or with dates that do not have a time portion. I could have managed 
		 * this with standard Java 7 methods, but it is easier to just use Joda 
		 * time.
		 */
		LocalTime defaultTime_Joda = new LocalTime(defaultTimeValueConfiguration.getTimeValue());
		assertThat(defaultTime_Joda, is(equalTo(TEST_TIME_DEFAULT_VALUE_JODA)));
	}

	/*
	 * Set default time value for a parameter that already has a default 
	 * (Role==null) Configuration. This should update the existing Configuration
	 * entity to store this new value.
	 */

	@Test
	@Transactional
	public void timeValueSetDefaultWithConfig() {
		assertThat(configurationRepository.count(), is(NUM_TEST_CONFIGURATIONS));
		/*
		 * Retrieve the existing default value.
		 */
		Object timeValueObject = config.get(ParamName.TEST_TIME);
		assertThat(timeValueObject, is(instanceOf(Date.class)));
		Date timeFromConfig = (Date) timeValueObject;
		assertThat(timeFromConfig, is(not(nullValue())));

		/*
		 * Convert Date object to Joda time LocalTime object to make it easy
		 * to compare with TEST_TIME_DEFAULT_VALUE_JODA.
		 */
		LocalTime timeFromConfig_Joda = new LocalTime(timeFromConfig);
		assertThat(timeFromConfig_Joda, is(equalTo(TEST_TIME_DEFAULT_VALUE_JODA)));

		/*
		 * Update default value.
		 * 
		 * The year, month & day here (1800, 6, 15) are arbitrary and are not
		 * used because it is only the time portion that we work with.
		 */
		Date newTimeValue = new GregorianCalendar(1800, 6, 15, 13, 45, 59).getTime();
		config.set(ParamName.TEST_TIME, newTimeValue);
		/*
		* The existing Configuration should have been updated, so there should
		* be the same number of entities in the database
		*/
		assertThat(configurationRepository.count(), is(NUM_TEST_CONFIGURATIONS));
		/* 
		 * Retrieve value just set.
		 */
		Object timeValueObjectUpdated = config.get(ParamName.TEST_TIME);
		assertThat(timeValueObjectUpdated, is(instanceOf(Date.class)));
		Date timeFromConfigUpdated = (Date) timeValueObjectUpdated;
		assertThat(timeFromConfigUpdated, is(not(nullValue())));

		LocalTime timeFromConfigUpdated_Joda = new LocalTime(timeFromConfigUpdated);
		LocalTime newTimeValue_Joda = new LocalTime(newTimeValue);
		assertThat(timeFromConfigUpdated_Joda, is(equalTo(newTimeValue_Joda)));
	}

	/*
	 * Set role-specific time value for a parameter that has a default 
	 * (Role==null) Configuration, but no role-specific value. This should
	 * create a new Configuration entity to store this value.
	 */
	@Test
	@Transactional
	public void timeValueSetRoleSpecificWithConfig() {
		assertThat(configurationRepository.count(), is(NUM_TEST_CONFIGURATIONS));

		UUID uuidOfRole_bcbc = UUID.fromString("e918c8aa-c6d1-462c-9e91-f1db0fb9f346");
		Role role_bcbc = roleRepository.findOne(uuidOfRole_bcbc);
		assertThat(role_bcbc, is(notNullValue()));
		/*
		 * There is a default value for ParamName.TEST_TIME, but no
		 * role-specific value for Role "bcbc".
		 */
		Object timeValueObject = config.get(ParamName.TEST_TIME, role_bcbc);
		assertThat(timeValueObject, is(instanceOf(Date.class)));
		Date timeFromConfig = (Date) timeValueObject;
		assertThat(timeFromConfig, is(not(nullValue())));

		/*
		 * Convert Date object to Joda time LocalTime object to make it easy
		 * to compare with TEST_TIME_DEFAULT_VALUE_JODA.
		 */
		LocalTime timeFromConfig_Joda = new LocalTime(timeFromConfig);
		assertThat(timeFromConfig_Joda, is(equalTo(TEST_TIME_DEFAULT_VALUE_JODA)));

		/*
		 * Set role-specific value, which should override the default value for
		 * the specified role.
		 * 
		 * The year, month & day here (1960, 4, 14) are arbitrary and are not
		 * used because it is only the time portion that we work with.
		 */
		Date newTimeValue = new GregorianCalendar(1960, 4, 14, 23, 30, 59).getTime();
		config.set(ParamName.TEST_TIME, newTimeValue, role_bcbc);
		/*
		 * This should have created a new Configuration.
		 */
		assertThat(configurationRepository.count(), is(NUM_TEST_CONFIGURATIONS + 1));
		/*
		 * Retrieve value just set. The new value should override the default.
		 */
		Object timeValueRoleSpecificObject = config.get(ParamName.TEST_TIME, role_bcbc);
		assertThat(timeValueRoleSpecificObject, is(instanceOf(Date.class)));
		Date timeFromRoleSpecific = (Date) timeValueRoleSpecificObject;
		assertThat(timeFromRoleSpecific, is(not(nullValue())));

		LocalTime timeFromRoleSpecific_Joda = new LocalTime(timeFromRoleSpecific);
		LocalTime newTimeValue_Joda = new LocalTime(newTimeValue);
		assertThat(timeFromRoleSpecific_Joda, is(equalTo(newTimeValue_Joda)));

		/*
		 * Update the role-specific value. This should override the default 
		 * value for the specified role and RE-USE the new role-specific
		 * Configuration just created, i.e., not create a new Configuration
		 * 
		 * The year, month & day here (1960, 4, 14) are arbitrary and are not
		 * used because it is only the time portion that we work with.
		 */
		newTimeValue = new GregorianCalendar(1961, 10, 4, 00, 00, 00).getTime();
		config.set(ParamName.TEST_TIME, newTimeValue, role_bcbc);
		/*
		 * This should have created a new Configuration.
		 */
		assertThat(configurationRepository.count(), is(NUM_TEST_CONFIGURATIONS + 1));
		/*
		 * Retrieve value just set. The new value should override the default.
		 */
		timeValueRoleSpecificObject = config.get(ParamName.TEST_TIME, role_bcbc);
		assertThat(timeValueRoleSpecificObject, is(instanceOf(Date.class)));
		timeFromRoleSpecific = (Date) timeValueRoleSpecificObject;
		assertThat(timeFromRoleSpecific, is(not(nullValue())));

		timeFromRoleSpecific_Joda = new LocalTime(timeFromRoleSpecific);
		newTimeValue_Joda = new LocalTime(newTimeValue);
		assertThat(timeFromRoleSpecific_Joda, is(equalTo(newTimeValue_Joda)));

		/*
		 * The default value for ParamName.TEST_TIME should still be the
		 * same.
		 */
		Object timeValueDefaultObject = config.get(ParamName.TEST_TIME);
		assertThat(timeValueDefaultObject, is(instanceOf(Date.class)));
		timeFromConfig = (Date) timeValueDefaultObject;
		assertThat(timeFromConfig, is(not(nullValue())));

		/*
		 * Convert Date object to Joda time LocalTime object to make it easy
		 * to compare with TEST_TIME_DEFAULT_VALUE_JODA.
		 */
		timeFromConfig_Joda = new LocalTime(timeFromConfig);
		assertThat(timeFromConfig_Joda, is(equalTo(TEST_TIME_DEFAULT_VALUE_JODA)));
	}

}

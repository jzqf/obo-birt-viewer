package com.qfree.obo.report.service;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.eclipse.birt.core.exception.BirtException;
import org.eclipse.birt.report.engine.api.IParameterDefn;
import org.eclipse.birt.report.engine.api.IScalarParameterDefn;
import org.eclipse.birt.report.model.api.elements.DesignChoiceConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.qfree.obo.report.db.ParameterTypeRepository;
import com.qfree.obo.report.db.ReportParameterRepository;
import com.qfree.obo.report.db.WidgetRepository;
import com.qfree.obo.report.domain.ParameterType;
import com.qfree.obo.report.domain.ReportParameter;
import com.qfree.obo.report.domain.ReportVersion;
import com.qfree.obo.report.domain.Widget;
import com.qfree.obo.report.util.ReportUtils;

@Component
@Transactional
public class ReportParameterService {

	private static final Logger logger = LoggerFactory.getLogger(ReportParameterService.class);

	private static final UUID DATA_TYPE_BOOLEAN = UUID.fromString("bfa09b13-ad55-481e-8c29-b047dc5d7f3e");
	private static final UUID DATA_TYPE_DATE = UUID.fromString("12d3f4f8-468d-4faf-be3a-5c15eaba4eb6");
	private static final UUID DATA_TYPE_DATETIME = UUID.fromString("abce5a38-b1e9-42a3-9962-19227d51dd4a");
	private static final UUID DATA_TYPE_DECIMAL = UUID.fromString("f2bfa3f9-f446-49dd-ad0e-6a02b3af1023");
	private static final UUID DATA_TYPE_FLOAT = UUID.fromString("8b0bfc37-5fb4-4dea-87fc-3e2c3313af17");
	private static final UUID DATA_TYPE_INTEGER = UUID.fromString("807c64b1-a59b-465c-998b-a399984b5ef4");
	private static final UUID DATA_TYPE_STRING = UUID.fromString("9b0af697-8bc9-49e2-b8b6-136ced83dbd8");
	private static final UUID DATA_TYPE_TIME = UUID.fromString("da575eee-e5a3-4149-8ea3-1fd86015bbb9");
	private static final UUID DATA_TYPE_ANY = UUID.fromString("2bc62461-6ddb-4e86-b46d-080cd5e9cf83");

	private static final UUID WIDGET_TEXTBOX = UUID.fromString("e5b4cebb-1852-41a1-9fdf-bb4b8da82ef9");
	private static final UUID WIDGET_LISTBOX = UUID.fromString("4d0842e4-ba65-4064-8ab1-556e90e3953b");
	private static final UUID WIDGET_RADIO_BUTTON = UUID.fromString("864c60a8-6c48-4efb-84dd-fc79502899fe");
	private static final UUID WIDGET_CHECKBOX = UUID.fromString("b8e91527-8b0e-4ed2-8cba-8cb8989ba8e2");

	//	private final ReportVersionRepository reportVersionRepository;
	//	private final ReportRepository reportRepository;
	private final ReportParameterRepository reportParameterRepository;
	private final ParameterTypeRepository parameterTypeRepository;
	private WidgetRepository widgetRepository;

	@Autowired
	public ReportParameterService(
			//			ReportVersionRepository reportVersionRepository,
			//			ReportRepository reportRepository,
			ReportParameterRepository reportParameterRepository,
			ParameterTypeRepository parameterTypeRepository,
			WidgetRepository widgetRepository) {
		//		this.reportVersionRepository = reportVersionRepository;
		//		this.reportRepository = reportRepository;
		this.reportParameterRepository = reportParameterRepository;
		this.parameterTypeRepository = parameterTypeRepository;
		this.widgetRepository = widgetRepository;
	}

	@Transactional
	public Map<String, Map<String, Serializable>> createParametersForReport(ReportVersion reportVersion)
			throws IOException, BirtException {

		/*
		 * Extract all parameters and their metadata from the rptdesign.
		 */
		Map<String, Map<String, Serializable>> parameters = ReportUtils.parseReportParams(reportVersion.getRptdesign());

		/*
		 * For each report parameter, create a ReportParameter entity which is
		 * stored in the report server database. "parameters" is a LinkedHashMap
		 * so this "for" loop will iterate through the parameters in the order
		 * that they are defined in the rptdesign file.
		 */
		Integer orderIndex = 0;
		for (Map.Entry<String, Map<String, Serializable>> parametersEntry : parameters.entrySet()) {
			logger.info("Parameter = {}", parametersEntry.getKey());
			Map<String, Serializable> parameter = parametersEntry.getValue();
			//for (Map.Entry<String, Serializable> parameterEntry : parameter.entrySet()) {
			//	String parameterAttrName = parameterEntry.getKey();
			//	logger.info("  {} = {}", parameterAttrName, parameterEntry.getValue());
			//}

			orderIndex += 1;
			logger.info("parameter #{}. Name = {}", orderIndex, parameter.get("Name"));

			/*
			 * Select a ParameterType that matches the parameter data type,
			 * parameter.get("DataType"):
			 */
			UUID parameterDataTypeId = DATA_TYPE_ANY;
			if (parameter.get("DataType").equals(IParameterDefn.TYPE_BOOLEAN)) {
				parameterDataTypeId = DATA_TYPE_BOOLEAN;
			} else if (parameter.get("DataType").equals(IParameterDefn.TYPE_DATE)) {
				parameterDataTypeId = DATA_TYPE_DATE;
			} else if (parameter.get("DataType").equals(IParameterDefn.TYPE_DATE_TIME)) {
				parameterDataTypeId = DATA_TYPE_DATETIME;
			} else if (parameter.get("DataType").equals(IParameterDefn.TYPE_DECIMAL)) {
				parameterDataTypeId = DATA_TYPE_DECIMAL;
			} else if (parameter.get("DataType").equals(IParameterDefn.TYPE_FLOAT)) {
				parameterDataTypeId = DATA_TYPE_FLOAT;
			} else if (parameter.get("DataType").equals(IParameterDefn.TYPE_INTEGER)) {
				parameterDataTypeId = DATA_TYPE_INTEGER;
			} else if (parameter.get("DataType").equals(IParameterDefn.TYPE_STRING)) {
				parameterDataTypeId = DATA_TYPE_STRING;
			} else if (parameter.get("DataType").equals(IParameterDefn.TYPE_TIME)) {
				parameterDataTypeId = DATA_TYPE_TIME;
			} else {
				parameterDataTypeId = DATA_TYPE_ANY;
			}
			logger.debug("parameterDataTypeId = {}", parameterDataTypeId);
			ParameterType parameterDataType = parameterTypeRepository.findOne(parameterDataTypeId);
			logger.debug("parameterDataType = {}", parameterDataType);

			/*
			 * Select a WidgetId that matches the "control" type,
			 * parameter.get("ControlType"):
			 */
			UUID widgetId = WIDGET_TEXTBOX;
			if (parameter.get("ControlType").equals(IScalarParameterDefn.LIST_BOX)) {
				widgetId = WIDGET_LISTBOX;
			} else if (parameter.get("ControlType").equals(IScalarParameterDefn.RADIO_BUTTON)) {
				widgetId = WIDGET_RADIO_BUTTON;
			} else if (parameter.get("ControlType").equals(IScalarParameterDefn.CHECK_BOX)) {
				widgetId = WIDGET_CHECKBOX;
			} else {// includes case: parameter.get("ControlType").equals(IScalarParameterDefn.TEXT_BOX)
				widgetId = WIDGET_TEXTBOX;
			}
			logger.debug("widgetId = {}", widgetId);
			Widget widget = widgetRepository.findOne(widgetId);
			logger.debug("widget = {}", widget);

			Boolean multivalued = Boolean.FALSE;
			if (parameter.get("ScalarParameterType") != null) {
				if (parameter.get("ScalarParameterType").equals(DesignChoiceConstants.SCALAR_PARAM_TYPE_SIMPLE)) {
					multivalued = false;
				} else if (parameter.get("ScalarParameterType")
						.equals(DesignChoiceConstants.SCALAR_PARAM_TYPE_MULTI_VALUE)) {
					multivalued = true;
					//} else if (parameter.get("ScalarParameterType").equals(DesignChoiceConstants.SCALAR_PARAM_TYPE_AD_HOC)) {
					//	// I hope this case does not occur because I don't know how to handle it.
				}
			}

			String promptText;
			if (parameter.get("PromptText") != null && ((String) parameter.get("PromptText")).isEmpty()) {
				promptText = (String) parameter.get("PromptText");
			} else {
				promptText = (String) parameter.get("Name") + ":";// sensible default value
			}

			//TODO Should group details be stored in a related table? This will be more work, but better normalization.
			String groupName = null;
			String groupPromptText = null;
			Integer GroupParameterType = null;
			if (parameter.get("GroupDetails") != null) {
				HashMap<String, Serializable> groupDetails = (HashMap<String, Serializable>) parameter
						.get("GroupDetails");
				groupName = (String) parameter.get("GroupName");
				groupPromptText = (String) groupDetails.get("GroupPromptText");
				GroupParameterType = (Integer) groupDetails.get("GroupParameterType");
			}
			if (groupPromptText == null && groupName != null) {
				/*
				 * This is a sensible value that will be used for normal 
				 * parameter groups, i.e., not cascading parameter groups, where
				 * the "GroupPromptText" value seems to always be null,
				 * unfortunately.
				 */
				groupPromptText = groupName;//TODO place this logic in the constructor!!!!!
			}
			logger.debug("groupName={}, groupPromptText={}, GroupParameterType={}", groupName, groupPromptText,
					GroupParameterType);

			String displayName = parameter.get("DisplayName") != null ? (String) parameter.get("DisplayName") : null;
			String helpText = parameter.get("HelpText") != null ? (String) parameter.get("HelpText") : null;
			if ("".equals(helpText)) {
				/*
				 * If helpText is blank, we store null instead since this is
				 * a better way to determine if help text has been provided or 
				 * not.
				 */
				helpText = null;//TODO place this logic in the setter/constructor!!!!!
			}
			String defaultValue = parameter.get("DefaultValue") != null ? (String) parameter.get("DefaultValue") : null;
			if ("".equals(defaultValue)) {
				/*
				 * If defaultValue is blank, we store null instead since this is
				 * a better way to determine if a default value has been 
				 * provided or not.
				 */
				defaultValue = null;//TODO place this logic in the setter/constructor!!!!!
			}
			String displayFormat = parameter.get("DisplayFormat") != null ? (String) parameter.get("DisplayFormat")
					: null;
			Integer alignment = parameter.get("Alignment") != null ? (Integer) parameter.get("Alignment")
					: IScalarParameterDefn.AUTO;
			Boolean hidden = (Boolean) parameter.get("Hidden");
			Boolean valueConcealed = (Boolean) parameter.get("ValueConcealed");
			Boolean allowNewValues = (Boolean) parameter.get("AllowNewValues");
			Boolean displayInFixedOrder = (Boolean) parameter.get("DisplayInFixedOrder");
			Integer parameterType = (Integer) parameter.get("ParameterType");
			String typeName = parameter.get("TypeName") != null ? (String) parameter.get("TypeName") : null;
			if ("".equals(typeName)) {
				/*
				 * If typeName is blank, we store null instead since this is
				 * a better way to determine if a value has been provided or 
				 * not.
				 */
				typeName = null;//TODO place this logic in the setter/constructor!!!!!
			}
			Integer autoSuggestThreshold = (Integer) parameter.get("AutoSuggestThreshold");
			Integer selectionListType = (Integer) parameter.get("SelectionListType");
			String valueExpr = parameter.get("ValueExpr") != null ? (String) parameter.get("ValueExpr") : null;
			if ("".equals(valueExpr)) {
				/*
				 * If valueExpr is blank, we store null instead since this is
				 * a better way to determine if a value has been provided or 
				 * not.
				 */
				valueExpr = null;//TODO place this logic in the setter/constructor!!!!!
			}

			//TODO These values should be added as arguments to the ReportParameter constructor:

			ReportParameter reportParameter = new ReportParameter(
					reportVersion,
					parameterDataType,
					widget,
					(String) parameter.get("Name"),
					promptText,
					parameter.get("Required") != null ? (Boolean) parameter.get("Required") : Boolean.TRUE,
					multivalued,
					orderIndex);

			reportParameter = reportParameterRepository.save(reportParameter);
		}

		return parameters;
	}
}

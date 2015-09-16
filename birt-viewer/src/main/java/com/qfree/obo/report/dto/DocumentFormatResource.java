package com.qfree.obo.report.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.ws.rs.core.UriInfo;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qfree.obo.report.domain.DocumentFormat;
import com.qfree.obo.report.rest.server.RestUtils.RestApiVersion;

@XmlRootElement
//@XmlJavaTypeAdapter(value = UuidAdapter.class, type = UUID.class) <- doesn't work
public class DocumentFormatResource extends AbstractBaseResource {

	private static final Logger logger = LoggerFactory.getLogger(DocumentFormatResource.class);

	@XmlElement
	@XmlJavaTypeAdapter(UuidAdapter.class)
	private UUID documentFormatId;

	@XmlElement
	private String name;

	@XmlElement
	private String fileExtension;

	@XmlElement
	private String mediaType;

	@XmlElement
	private String birtFormat;

	@XmlElement
	private Boolean binaryData;

	@XmlElement
	private Boolean active;

	@XmlElement
	@XmlJavaTypeAdapter(DatetimeAdapter.class)
	private Date createdOn;

	// @XmlElement(name = "subscriptions")
	// private SubscriptionCollectionResource subscriptionCollectionResource;

	public DocumentFormatResource() {
	}

	public DocumentFormatResource(DocumentFormat documentFormat, UriInfo uriInfo,
			Map<String, List<String>> queryParams, RestApiVersion apiVersion) {

		super(DocumentFormat.class, documentFormat.getDocumentFormatId(), uriInfo, queryParams, apiVersion);

		List<String> expand = queryParams.get(ResourcePath.EXPAND_QP_KEY);

		String expandParam = ResourcePath.forEntity(DocumentFormat.class).getExpandParam();
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

			this.documentFormatId = documentFormat.getDocumentFormatId();
			this.name = documentFormat.getName();
			this.fileExtension = documentFormat.getFileExtension();
			this.mediaType = documentFormat.getMediaType();
			this.birtFormat = documentFormat.getBirtFormat();
			this.binaryData = documentFormat.getBinaryData();
			this.active = documentFormat.getActive();
			this.createdOn = documentFormat.getCreatedOn();
			// this.subscriptionCollectionResource = new
			// SubscriptionCollectionResource(documentFormat,
			// uriInfo, newQueryParams, apiVersion);
		}
		logger.debug("this = {}", this);
	}

}

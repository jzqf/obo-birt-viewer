package com.qfree.obo.report.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.ws.rs.core.UriInfo;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qfree.obo.report.domain.Report;
import com.qfree.obo.report.domain.ReportVersion;
import com.qfree.obo.report.util.RestUtils;
import com.qfree.obo.report.util.RestUtils.RestApiVersion;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class ReportVersionResource extends AbstractBaseResource {

	private static final Logger logger = LoggerFactory.getLogger(ReportVersionResource.class);

	@XmlElement
	@XmlJavaTypeAdapter(UuidAdapter.class)
	private UUID reportVersionId;

	@XmlElement(name = "report")
	private ReportResource reportResource;

	@XmlElement
	private String fileName;

	@XmlElement
	// Do not serialize this field - needs @XmlAccessorType(XmlAccessType.FIELD);
	//@XmlTransient
	private String rptdesign;

	@XmlElement
	private String versionName;

	@XmlElement
	private Integer versionCode;

	@XmlElement
	private Boolean active;

	@XmlElement
	@XmlJavaTypeAdapter(DatetimeAdapter.class)
	private Date createdOn;

	@XmlElement(name = "reportParameters")
	//	private List<ReportParameterResource> reportParameters;
	private ReportParameterCollectionResource reportParameters;

	public ReportVersionResource() {
	}

	public ReportVersionResource(ReportVersion reportVersion, UriInfo uriInfo, Map<String, List<String>> queryParams,
			RestApiVersion apiVersion) {

		super(ReportVersion.class, reportVersion.getReportVersionId(), uriInfo, queryParams, apiVersion);

		List<String> expand = queryParams.get(ResourcePath.EXPAND_QP_KEY);

		String expandParam = ResourcePath.forEntity(ReportVersion.class).getExpandParam();
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

			this.reportVersionId = reportVersion.getReportVersionId();
			this.reportResource = new ReportResource(reportVersion.getReport(),
					uriInfo, newQueryParams, apiVersion);
			this.fileName = reportVersion.getFileName();
			if (expand.contains(ResourcePath.RPTDESIGN_EXPAND_PARAM)) {
				this.rptdesign = reportVersion.getRptdesign();
				expandElementRemoved.remove(ResourcePath.RPTDESIGN_EXPAND_PARAM);  // probably not necessary
			} else {
				this.rptdesign = String.format("<%s bytes>",
						(reportVersion.getRptdesign() != null) ? reportVersion.getRptdesign().length() : 0);
			}
			this.versionName = reportVersion.getVersionName();
			this.versionCode = reportVersion.getVersionCode();
			this.active = reportVersion.isActive();
			this.createdOn = reportVersion.getCreatedOn();

			this.reportParameters = new ReportParameterCollectionResource(reportVersion,
					uriInfo, newQueryParams, apiVersion);
			//			if (reportVersion.getReportParameters() != null) {
			//
			//				List<ReportParameter> reportParameters = reportVersion.getReportParameters();
			//				List<ReportParameterResource> reportParameterResources = new ArrayList<>(reportParameters.size());
			//				for (ReportParameter reportParameter : reportParameters) {
			//					reportParameterResources.add(
			//							new ReportParameterResource(reportParameter, uriInfo, expandElementRemoved, apiVersion));
			//				}
			//				//this.reportParameters = reportParameterResources;
			//				this.reportParameters = new ReportParameterCollectionResource(reportParameterResources,
			//						ReportParameter.class, uriInfo, expand, apiVersion);
			//			}
		}
	}

	public static List<ReportVersionResource> listFromReport(Report report, UriInfo uriInfo,
			Map<String, List<String>> queryParams, RestApiVersion apiVersion) {
		if (report.getReportVersions() != null) {
			List<ReportVersion> reportVersions = report.getReportVersions();
			List<ReportVersionResource> reportVersionResources = new ArrayList<>(reportVersions.size());
			for (ReportVersion reportVersion : reportVersions) {
				List<String> showAll = queryParams.get(ResourcePath.SHOWALL_QP_KEY);
				if (reportVersion.isActive() ||
						RestUtils.FILTER_INACTIVE_RECORDS == false ||
						ResourcePath.showAll(ReportVersion.class, showAll)) {
					reportVersionResources.add(
							new ReportVersionResource(reportVersion, uriInfo, queryParams, apiVersion));
				}
			}
			return reportVersionResources;
		} else {
			return null;
		}
	}

	public UUID getReportVersionId() {
		return reportVersionId;
	}

	public void setReportVersionId(UUID reportVersionId) {
		this.reportVersionId = reportVersionId;
	}

	public ReportResource getReportResource() {
		return reportResource;
	}

	public void setReportResource(ReportResource reportResource) {
		this.reportResource = reportResource;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getRptdesign() {
		return rptdesign;
	}

	public void setRptdesign(String rptdesign) {
		this.rptdesign = rptdesign;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public Integer getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(Integer versionCode) {
		this.versionCode = versionCode;
	}

	public Boolean isActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public ReportParameterCollectionResource getReportParameters() {
		return reportParameters;
	}

	public void setReportParameters(ReportParameterCollectionResource reportParameters) {
		this.reportParameters = reportParameters;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ReportVersionResource [reportVersionId=");
		builder.append(reportVersionId);
		builder.append(", reportResource=");
		builder.append(reportResource);
		builder.append(", rptdesign=");
		builder.append(rptdesign);
		//		builder.append("<" + ((rptdesign != null) ? rptdesign.length() : 0) + " bytes>");
		builder.append(", versionName=");
		builder.append(versionName);
		builder.append(", versionCode=");
		builder.append(versionCode);
		builder.append(", active=");
		builder.append(active);
		builder.append(", createdOn=");
		builder.append(createdOn);
		builder.append(", href=");
		builder.append(href);
		builder.append(", mediaType=");
		builder.append(mediaType);
		builder.append("]");
		return builder.toString();
	}

}

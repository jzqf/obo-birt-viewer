package com.qfree.bo.report.dto;

import java.util.List;
import java.util.Map;

import javax.ws.rs.core.UriInfo;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qfree.bo.report.domain.Asset;
import com.qfree.bo.report.util.RestUtils.RestApiVersion;

@XmlRootElement
public class AssetCollectionResource extends AbstractCollectionResource<AssetResource, Asset> {

	private static final Logger logger = LoggerFactory.getLogger(AssetCollectionResource.class);

	@XmlElement
	private List<AssetResource> items;

	public AssetCollectionResource() {
	}

	public AssetCollectionResource(
			List<Asset> assets,
			Class<Asset> entityClass,
			UriInfo uriInfo,
			Map<String, List<String>> queryParams,
			RestApiVersion apiVersion) {
		this(
				assets,
				entityClass,
				null,
				null,
				uriInfo,
				queryParams,
				apiVersion);
	}

	public AssetCollectionResource(
			List<Asset> assets,
			Class<Asset> entityClass,
			String baseResourceUri,
			String collectionPath,
			UriInfo uriInfo,
			Map<String, List<String>> queryParams,
			RestApiVersion apiVersion) {

		super(
				assets,
				entityClass,
				baseResourceUri,
				collectionPath,
				uriInfo,
				queryParams,
				apiVersion);

		List<String> expand = queryParams.get(ResourcePath.EXPAND_QP_KEY);
		if (ResourcePath.expand(entityClass, expand)) {
			/*
			 * We pass null for apiVersion since the version used in the 
			 * original request does not necessarily apply here.
			 */
			apiVersion = null;
			this.items = AssetResource.assetResourceListPageFromAssets(
					assets, uriInfo, queryParams, apiVersion);
		}
	}

	public List<AssetResource> getItems() {
		return items;
	}

	public void setItems(List<AssetResource> items) {
		this.items = items;
	}

}

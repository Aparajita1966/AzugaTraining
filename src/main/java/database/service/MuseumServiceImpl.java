package database.service;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import database.jdbc.DatabaseConnection;

public class MuseumServiceImpl implements MuseumService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MuseumServiceImpl.class);

	@Override
	public void addMuseum(JSONObject jsonObject) throws SQLException {
		LOGGER.info("Adding object data to database");
		JSONArray constituentsArray = null;
		JSONArray measurementsArray = null;
		JSONArray tagsArray = null;
		if (jsonObject.has("constituents")) {
			if (!jsonObject.get("constituents").equals(null)) {
				if(jsonObject.get("constituents").toString() != "null") {
					constituentsArray = jsonObject.getJSONArray("constituents");
				}
			}
			jsonObject.remove("constituents");
		}
		if (jsonObject.has("measurements")) {
			if (!jsonObject.get("measurements").equals(null)) {
				if(jsonObject.get("measurements").toString() != "null") {
					measurementsArray = jsonObject.getJSONArray("measurements");
				}
			}
			jsonObject.remove("measurements");
		}
		if (jsonObject.has("tags")) {
			if (!jsonObject.get("tags").equals(null)) {
				if(!jsonObject.get("tags").toString().equals("null")) {
					tagsArray = jsonObject.getJSONArray("tags");
				}
			}
			jsonObject.remove("tags");
		}
		DatabaseConnection.insertObject(jsonObject, "objects");
		if (null != constituentsArray) {
			for (int i = 0; i < constituentsArray.length(); i++) {
				JSONObject jsonObj = constituentsArray.getJSONObject(i);
				jsonObj.put("objectID", jsonObject.getInt("objectID"));
				DatabaseConnection.insertObject(jsonObj, "constituents");
			}
			jsonObject.remove("constituents");
		}
		if (null != measurementsArray) {
			for (int i = 0; i < measurementsArray.length(); i++) {
				JSONObject jsonObj = measurementsArray.getJSONObject(i);
				if (!jsonObj.get("elementMeasurements").equals(null)) {
					JSONObject elemJson = new JSONObject(String.valueOf(jsonObj.get("elementMeasurements")));
					if (elemJson.has("Depth")) {
						jsonObj.put("Depth", elemJson.getDouble("Depth"));
					}
					if (elemJson.has("Height")) {
						jsonObj.put("Height", elemJson.getDouble("Height"));
					}
					if (elemJson.has("Width")) {
						jsonObj.put("Width", elemJson.getDouble("Width"));
					}
					if (elemJson.has("Diameter")) {
						jsonObj.put("Diameter", elemJson.getDouble("Diameter"));
					}
					jsonObj.remove("elementMeasurements");
				}
				jsonObj.put("objectID", jsonObject.getInt("objectID"));
				DatabaseConnection.insertObject(jsonObj, "measurements");
			}
			jsonObject.remove("measurements");
		}
		if (null != tagsArray) {
			for (int i = 0; i < tagsArray.length(); i++) {
				JSONObject jsonObj = tagsArray.getJSONObject(i);
				jsonObj.put("objectID", jsonObject.getInt("objectID"));
				DatabaseConnection.insertObject(jsonObj, "tags");
			}
			jsonObject.remove("tags");
		}
	}

	@Override
	public JSONObject getMuseum(int objectId) throws SQLException {
		JSONObject json = DatabaseConnection.getObjectById(objectId);
		getOtherParameter(objectId, json);
		return json;
	}

	private void getOtherParameter(int objectId, JSONObject json) throws SQLException {
		JSONArray constituentsArray = new JSONArray();
		List<JSONObject> constituentsList = DatabaseConnection.getData(objectId, "constituents");
		if (!CollectionUtils.isEmpty(constituentsList)) {
			constituentsArray.putAll(constituentsList);
			json.put("constituents", constituentsArray);
		}
		JSONArray measurementsArray = new JSONArray();
		List<JSONObject> measurementsList = DatabaseConnection.getData(objectId, "measurements");
		if (!CollectionUtils.isEmpty(measurementsList)) {
			measurementsArray.putAll(measurementsList);
			json.put("measurements", measurementsArray);
		}
		JSONArray tagsArray = new JSONArray();
		List<JSONObject> tagsList = DatabaseConnection.getData(objectId, "tags");
		if (!CollectionUtils.isEmpty(tagsList)) {
			tagsArray.putAll(tagsList);
			json.put("tags", tagsArray);
		}
	}

	@Override
	public List<JSONObject> getMuseumAll(String tableName) throws SQLException {
		List<JSONObject> list = DatabaseConnection.getObjects(tableName);
		// Table Name check to fetch other field data
		if (StringUtils.isBlank(tableName)) {
			for (JSONObject jsonObject : list) {
				getOtherParameter(jsonObject.getInt("objectID"), jsonObject);
			}
		}
		return list;
	}

	@Override
	public void deleteMuseumObject(int objectId) throws SQLException {
		DatabaseConnection.deleteMuseumObject(objectId);
	}

	@Override
	public void updateMuseumObject(JSONObject jsonObject) throws SQLException {
		if (jsonObject.has("objectID") && jsonObject.keySet().size() > 1) {
			int objectId = jsonObject.getInt("objectID");
			jsonObject.remove("objectID");
			if (jsonObject.has("constituents") && !jsonObject.get("constituents").equals(null)) {
				JSONArray jsonArray = jsonObject.getJSONArray("constituents");
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObj = jsonArray.getJSONObject(i);
					DatabaseConnection.updateMuseumObject(jsonObj, objectId, "constituents");
				}
				jsonObject.remove("constituents");
			}
			if (jsonObject.has("measurements") && !jsonObject.get("measurements").equals(null)) {
				JSONArray jsonArray = jsonObject.getJSONArray("measurements");
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObj = jsonArray.getJSONObject(i);
					if (!jsonObj.get("elementMeasurements").equals(null)) {
						JSONObject elemJson = new JSONObject(String.valueOf(jsonObj.get("elementMeasurements")));
						if (elemJson.has("Depth")) {
							jsonObj.put("Depth", elemJson.getDouble("Depth"));
						}
						if (elemJson.has("Height")) {
							jsonObj.put("Height", elemJson.getDouble("Height"));
						}
						if (elemJson.has("Width")) {
							jsonObj.put("Width", elemJson.getDouble("Width"));
						}
						if (elemJson.has("Diameter")) {
							jsonObj.put("Diameter", elemJson.getDouble("Diameter"));
						}
						jsonObj.remove("elementMeasurements");
					}
					DatabaseConnection.updateMuseumObject(jsonObj, objectId, "measurements");
				}
				jsonObject.remove("measurements");
			}
			if (jsonObject.has("tags") && !jsonObject.get("tags").equals(null)) {
				JSONArray jsonArray = jsonObject.getJSONArray("tags");
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject jsonObj = jsonArray.getJSONObject(i);
					DatabaseConnection.updateMuseumObject(jsonObj, objectId, "tags");
				}
				jsonObject.remove("tags");
			}
			DatabaseConnection.updateMuseumObject(jsonObject, objectId, "objects");
		}
	}
}

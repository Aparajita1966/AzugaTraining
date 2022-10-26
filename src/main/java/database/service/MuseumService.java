package database.service;

import java.sql.SQLException;
import java.util.List;

import org.json.JSONObject;

public interface MuseumService {
	void addMuseum(JSONObject jsonObject) throws SQLException;
	
	JSONObject getMuseum(int objectId) throws SQLException;
	
	List<JSONObject> getMuseumAll(String tableName) throws SQLException;

	void deleteMuseumObject(int objectId) throws SQLException;
	
	void updateMuseumObject(JSONObject jsonObject) throws SQLException;

}

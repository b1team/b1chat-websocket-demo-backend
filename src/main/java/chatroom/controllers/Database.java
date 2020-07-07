package chatroom.controllers;

import java.util.Map;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.InsertOneResult;
import org.bson.Document;

import chatroom.config.Config;

public class Database {
	private Map<String, Object> mongodbConfig;
	private String objectName;

	public Database(String objectName) {
		this.objectName = objectName;
		Map<String, Object> allConfig = Config.createConfig();
		if (allConfig != null) {
			mongodbConfig = (Map<String, Object>) allConfig.get("mongodb");
		}
	}

	public MongoClient getClient(String uri) {
		return MongoClients.create(uri);
	}

	public MongoDatabase getDatabase(){
		Map<String, Object> objectConfig = (Map<String, Object>)mongodbConfig.get(this.objectName);
		if(objectConfig != null){
			String uri = (String)objectConfig.get("uri");
			String dbname = (String)objectConfig.get("dbname");
			MongoClient client = getClient(uri);
			if(client != null){
				return client.getDatabase(dbname);
			}
			else{
				return null;
			}
		}
		else {
			return null;
		}
	}

	public MongoCollection<Document> getCollection() {
		Map<String, Object> objectConfig = (Map<String, Object>)mongodbConfig.get(this.objectName);
		if(objectConfig != null){
			String collection = (String)objectConfig.get("collection");
			MongoDatabase db = getDatabase();
			if(db != null){
				return db.getCollection(collection);
			}
			else{
				return null;
			}
		}
		else {
			return null;
		}
	}

	public void insert(Document account) {
		try {
			InsertOneResult result = getCollection().insertOne(account);
			System.out.println(result.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public FindIterable<Document> findOne(String fieldName, String value) {
		return getCollection().find(Filters.eq(fieldName, value));
	}

	public FindIterable<Document> findDouble(String fieldName1, String value1, String fieldName2, String value2) {
		return getCollection().find(Filters.and(Filters.eq(fieldName1, value1), Filters.eq(fieldName2, value2)));
	}
}
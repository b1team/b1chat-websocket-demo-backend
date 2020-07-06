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
	private MongoDatabase db;
	private String collectionName;
	private MongoCollection<Document> collection;
	private static Map<String, Object> config = Config.createConfig();

	public MongoDatabase getDatabase() {
		return db;
	}

	public void setDatabase(MongoDatabase value) {
		this.db = value;
	}

	public MongoCollection<Document> getCollection() {
		return collection;
	}

	public void setCollection(MongoCollection<Document> value) {
		this.collection = value;
	}

	public Database(String MongoConfig) {
		try {
			Map<String, Object> userConfig = (Map<String, Object>) config.get(MongoConfig);
			String mongoUri = userConfig.get("uri").toString();
			String mongoDbName = userConfig.get("dbname").toString();
			collectionName = userConfig.get("collection").toString();

			try (MongoClient mongoClient = MongoClients.create(mongoUri)) {
				db = mongoClient.getDatabase(mongoDbName);
				collection = db.getCollection(collectionName);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void insert(Document account) {
		try {
			InsertOneResult result = collection.insertOne(account);
			System.out.println(result.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public FindIterable<Document> findOne(String fieldName, String value) {
		return collection.find(Filters.eq(fieldName, value));
	}

	public FindIterable<Document> findDouble(String fieldName1, String value1, String fieldName2, String value2) {
		return collection.find(Filters.and(Filters.eq(fieldName1, value1), Filters.eq(fieldName2, value2)));
	}
}

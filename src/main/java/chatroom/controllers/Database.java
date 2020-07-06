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
	private static MongoDatabase mongoDB= null;
	private static String mongoCollection= null;
	private static Map<String, Object> config = Config.createConfig();

	public Database(String MongoConfig){
		try{
			Map<String, Object> userConfig = (Map<String, Object>) config.get(MongoConfig);
			String mongoUri = userConfig.get("uri").toString();
			String mongoDbName = userConfig.get("dbname").toString();
			mongoCollection = userConfig.get("collection").toString();
			MongoClient mongoClient = MongoClients.create(mongoUri);
			mongoDB = mongoClient.getDatabase(mongoDbName);
		}catch(Exception e){
			e.printStackTrace();
			mongoDB= null;
			mongoCollection= null;
		}
	}

	public void insert(Document account) {
		try{
			MongoCollection<Document> col = mongoDB.getCollection(mongoCollection);
			InsertOneResult result = col.insertOne(account);
			System.out.println(result.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public FindIterable<Document> findOne(String fieldName, String value) {
		MongoCollection<Document> col = mongoDB.getCollection(mongoCollection);
		FindIterable<Document> result = col.find(Filters.eq(fieldName, value));
		return result;
	}
	public FindIterable<Document> findDouble(String fieldName1, String value1, String fieldName2, String value2) {
		MongoCollection<Document> col = mongoDB.getCollection(mongoCollection);
		FindIterable<Document> result = col
			.find(Filters.and(Filters.eq(fieldName1, value1), Filters.eq(fieldName2, value2)));
		return result;
	}
}

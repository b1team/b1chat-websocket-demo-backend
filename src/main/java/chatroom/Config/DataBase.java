package chatroom.Config;

import java.util.HashMap;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.InsertOneResult;

import org.bson.Document;
import org.json.simple.JSONObject;

public class DataBase {
	private static String mongodbCol = null;
	private static MongoDatabase mongoDB = null;

	public void init() {
		Config config = new Config();
		JSONObject jsonObject = config.ReadFile();
		try{
			String mongoURI = (String) jsonObject.get("mongodb_uri");
			String mongodbName = (String) jsonObject.get("mongodb_name");
			MongoClient mongoClient = MongoClients.create(mongoURI);
			mongoDB = mongoClient.getDatabase(mongodbName);
			mongodbCol = (String) jsonObject.get("mongodb_collection");
		}catch(Exception e){
			e.printStackTrace();
			mongodbCol = null;
			mongoDB = null;
		}
	}

	public void insert(HashMap<String, Object> account) {
		JSONObject user = new JSONObject(account);
		Document doc = Document.parse(user.toJSONString());
		MongoCollection<Document> col = mongoDB.getCollection(mongodbCol);
		InsertOneResult result = col.insertOne(doc);
		System.out.println(result.toString());
	}

	public FindIterable<Document> findOne(String fieldName, String value) {
		MongoCollection<Document> col = mongoDB.getCollection(mongodbCol);
		FindIterable<Document> result = col.find(Filters.eq(fieldName, value));
		return result;
	}

	public FindIterable<Document> findDouble(String fieldName1, String value1, String fieldName2, String value2) {
		MongoCollection<Document> col = mongoDB.getCollection(mongodbCol);
		FindIterable<Document> result = col
			.find(Filters.and(Filters.eq(fieldName1, value1), Filters.eq(fieldName2, value2)));
		return result;
	}
}

package chatroom.config;

import java.io.FileReader;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class Config {
    public JSONObject ReadFile() {
        JSONParser parser = new JSONParser();
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String configPath = rootPath + "config.json";
        try{
            Object obj = parser.parse(new FileReader(configPath));
            JSONObject jsonObject = (JSONObject) obj;
            return jsonObject;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
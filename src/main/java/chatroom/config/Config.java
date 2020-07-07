package chatroom.config;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Config {
    private Config() {
        // create config instance
    }

    public static Map<String, Object> createConfig() {
        String rootPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        String configPath = rootPath + "config.json";
        try{
            HashMap<String, Object> mapper = new ObjectMapper().readValue(new File(configPath), HashMap.class);
            return mapper;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
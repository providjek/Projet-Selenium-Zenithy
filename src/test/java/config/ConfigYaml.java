package config;

import io.cucumber.java.zh_cn.而且;
import org.yaml.snakeyaml.Yaml;
import runners.AuthRunner;

import java.io.InputStream;
import java.util.Map;

public class ConfigYaml {
    private Yaml yaml = new Yaml();
    private InputStream inputStream = AuthRunner.class.getClassLoader().getResourceAsStream("configuration.yml");
    private Map<String, String> obj = yaml.load(inputStream);


    public String getBaseUrl(){
        return this.obj.get("base-url");
    }
    public String getLogUrl(){
        return this.obj.get("log-url");
    }
    public String getHomeUrl(){
        return this.obj.get("auth-url");
    }


    public Map<String, String> getObj() {
        return obj;
    }
}

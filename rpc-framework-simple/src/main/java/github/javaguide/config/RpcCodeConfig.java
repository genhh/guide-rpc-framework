package github.javaguide.config;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class RpcCodeConfig {
    private static final Properties properties = new Properties();

    static {
        ClassLoader classLoader = RpcCodeConfig.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("codeConfig.properties");
        try {
            if (inputStream != null) {
                properties.load(inputStream);
            } else {
                throw new RuntimeException("Could not find config.properties");
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load configuration file", e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    RpcCodeConfig(){

    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}

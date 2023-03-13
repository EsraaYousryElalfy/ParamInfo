package PageMethods;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigFileReader {

    private Properties properties;

    private String GetProperties() {
        String EnvironmentName = System.getProperty("build.profile.id");
        System.out.println("build.profile.id: " + EnvironmentName);
        String propertyFilePath = null;
        if (EnvironmentName.equals("dev")) {
            propertyFilePath = "src//main//resources//dev//config.properties";
        } else if (EnvironmentName.equals("test")) {
            propertyFilePath = "src//main//resources//test//config.properties";
        }
        return propertyFilePath;
    }

    public String GetApplicationBaseURL() {
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(GetProperties()));
            properties = new Properties();
            try {
                properties.load(reader);
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("Config.properties not found at " + GetProperties());
        }
        String url = properties.getProperty("url");
        if (url != null) return url;
        else throw new RuntimeException("url not specified in the Config.properties file.");
    }

    public String GetReportConfigPath() {
        String reportConfigPath = properties.getProperty("reportConfigPath");
        if (reportConfigPath != null) return reportConfigPath;
        else
            throw new RuntimeException("Report Config Path not specified in the Configuration.properties file for the Key:reportConfigPath");
    }
}
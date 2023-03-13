package CommonClasses;

import org.apache.commons.configuration.PropertiesConfiguration;

import java.io.File;
import java.io.FileInputStream;

public class Property extends AbstractPageObject {

    /**
     * gets properties file path based on test environment
     */
    public static String getPropsFilePath() {
        String EnvironmentName = System.getProperty("build.profile.id");
        String propertyFilePath = null;
        if (EnvironmentName.equals("testEnv")) {
            propertyFilePath = "src//main//resources//test//config.properties";
        }
        return propertyFilePath;
    }

    /**
     * gets properties
     */
    public static PropertiesConfiguration getProperties() {
        PropertiesConfiguration props = new PropertiesConfiguration();
        try {
            File propsFile = new File(getPropsFilePath());
            FileInputStream inputStream = new FileInputStream(propsFile);
            props.load(inputStream);
            inputStream.close();
        } catch (Exception e) {
            return null;
        }
        return props;
    }

    /**
     * gets string data from any properties file on given path
     */
    public static String getProperty(String key) {
        return getProperties().getString(key);
    }

    /**
     * gets value for variable based on preference of system property first then environment variable
     */
    public static String getVariable(String propname) {
        String val = System.getProperty(propname, null);
        val = (val == null ? System.getenv(propname) : val);
        return val;
    }
}

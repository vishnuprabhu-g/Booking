/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author vishnu-pt517
 */
public class DB {

    private String getPropValues(String name) {
        Properties prop = new Properties();
        String propFileName = "config.properties";

        InputStream inputStream;
        inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

        if (inputStream != null) {
            try {
                prop.load(inputStream);
            } catch (IOException ex) {
                System.err.println(ex);
            }
        }
        return prop.getProperty(name);
    }

    public static String getUsername() {
        String val = new DB().getPropValues("user");
        if (val == null) {
            val = "";
        }
        return val;
    }

    public static String getPassword() {
        String val = new DB().getPropValues("password");
        if (val == null) {
            val = "";
        }
        return val;
    }

    public static String getDb() {
        String val = new DB().getPropValues("db");
        if (val == null) {
            val = "";
        }
        return val;
    }

    public static String getDomain() {
        String val = new DB().getPropValues("domain");
        if (val == null) {
            val = "";
        }
        return val;
    }
}

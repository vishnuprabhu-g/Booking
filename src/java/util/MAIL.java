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
public class MAIL {

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
        String val = new MAIL().getPropValues("mail_user");
        if (val == null) {
            val = "root";
        }
        return val;
    }

    public static String getPassword() {
        String val = new MAIL().getPropValues("mail_pass");
        if (val == null) {
            val = "";
        }
        return val;
    }

    public static String getHost() {
        String val = new MAIL().getPropValues("mail_host");
        if (val == null) {
            val = "smtp";
        }
        return val;
    }

    public static int getPort() {
        String val = new MAIL().getPropValues("mail_port");
        if (val == null) {
            val = "25";
        }
        return Integer.parseInt(val);
    }
}

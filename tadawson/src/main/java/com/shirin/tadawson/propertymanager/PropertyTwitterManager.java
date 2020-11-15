package com.shirin.tadawson.propertymanager;

import com.shirin.propertiesconfig.bean.PropertiesTwitter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import static java.nio.file.Paths.get;
import java.util.Properties;
import static java.nio.file.Files.newInputStream;
import static java.nio.file.Files.newOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class load text properties if it is exist and bind them with
 * twitterProperties java class
 *
 * @author Shirin Eskandari 10-2019
 */
public class PropertyTwitterManager {

    private final static Logger LOG = LoggerFactory.getLogger(PropertyTwitterManager.class);

    public final boolean loadTextProperties(final PropertiesTwitter twitterProperty, final String path, final String propFileName) throws IOException {

        boolean found = false;
        Properties prop = new Properties();

        Path txtFile = get(path, propFileName + ".properties");

        // File must exist
        if (Files.exists(txtFile)) {

            try ( InputStream propFileStream = newInputStream(txtFile);) {
                prop.load(propFileStream);
            }
            if (prop.getProperty("oauth.consumerKey") != null && !prop.getProperty("oauth.consumerKey").isEmpty()
                    && prop.getProperty("oauth.consumerSecret") != null && !prop.getProperty("oauth.consumerSecret").isEmpty()
                    && prop.getProperty("oauth.accessToken") != null && !prop.getProperty("oauth.accessToken").isEmpty()
                    && prop.getProperty("oauth.accessTokenSecret") != null && !prop.getProperty("oauth.accessTokenSecret").isEmpty()) {
                twitterProperty.setConsumerKey(prop.getProperty("oauth.consumerKey"));
                twitterProperty.setConsumerSecret(prop.getProperty("oauth.consumerSecret"));
                twitterProperty.setAccessToken(prop.getProperty("oauth.accessToken"));
                twitterProperty.setAccessTokenSecret(prop.getProperty("oauth.accessTokenSecret"));

                found = true;
            }
        }
        return found;
    }

    /**
     * It will writes all keys inside the property file
     *
     * @param path
     * @param propFileName
     * @param twitterConfig
     * @throws IOException
     */
    public final void writeTextProperties(final String path, final String propFileName, final PropertiesTwitter twitterConfig) throws IOException {

        Properties prop = new Properties();

        prop.setProperty("oauth.consumerKey", twitterConfig.getConsumerKey());
        prop.setProperty("oauth.consumerSecret", twitterConfig.getConsumerSecret());
        prop.setProperty("oauth.accessToken", twitterConfig.getAccessToken());
        prop.setProperty("oauth.accessTokenSecret", twitterConfig.getAccessTokenSecret());

        Path txtFile = get(path, propFileName + ".properties");

        // Creates the file or if file exists it is truncated to length of zero
        // before writing
        try ( OutputStream propFileStream = newOutputStream(txtFile)) {
            prop.store(propFileStream, "Twitter Properties");
        }
    }
}

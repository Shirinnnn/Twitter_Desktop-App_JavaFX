
package com.shirin.propertiesconfig.bean;

import java.util.Objects;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class creates property Bean for twitter4j keys it will use in the
 * MainApp class in the starting of the application, for checking if the user
 * has been set 4 keys for using twitter app
 *
 * @author Shirin Eskandari 10-2019
 */
public class PropertiesTwitter {

    private final static Logger LOG = LoggerFactory.getLogger(PropertiesTwitter.class);
    private final StringProperty consumerKey;
    private final StringProperty consumerSecret;
    private final StringProperty accessToken;
    private final StringProperty accessTokenSecret;

    /**
     * constructor
     *
     */
    public PropertiesTwitter() {
        this("", "", "", "");
    }

    /**
     * constructor
     *
     * @param consumerKey
     * @param consumerSecret
     * @param accessToken
     * @param accessTokenSecret
     */
    public PropertiesTwitter(final String consumerKey, final String consumerSecret, final String accessToken, final String accessTokenSecret) {
        super();
        this.consumerKey = new SimpleStringProperty(consumerKey);
        this.consumerSecret = new SimpleStringProperty(consumerSecret);
        this.accessToken = new SimpleStringProperty(accessToken);
        this.accessTokenSecret = new SimpleStringProperty(accessTokenSecret);
    }

    /**
     *
     * @return
     */
    public String getAccessTokenSecret() {
        return this.accessTokenSecret.get();
    }

    /**
     *
     * @return
     */
    public String getConsumerKey() {
        return this.consumerKey.get();
    }

    /**
     *
     * @return
     */
    public String getConsumerSecret() {
        return this.consumerSecret.get();
    }

    /**
     *
     * @return
     */
    public String getAccessToken() {
        return this.accessToken.get();
    }

    /**
     *
     * @param accessTokenSecret
     */
    public void setAccessTokenSecret(String accessToken) {
        this.accessTokenSecret.set(accessToken);
    }

    /**
     *
     * @param consumerKey
     */
    public void setConsumerKey(String consumerKey) {
        this.consumerKey.set(consumerKey);
    }

    /**
     *
     * @param consumerSecret
     */
    public void setConsumerSecret(String consumerSecret) {
        this.consumerSecret.set(consumerSecret);
    }

    /**
     *
     * @param accessToken
     */
    public void setAccessToken(String accessToken) {
        this.accessToken.set(accessToken);
    }

    /**
     *
     * @return
     */
    public StringProperty accessTokenSecretProperty() {
        return this.accessTokenSecret;
    }

    /**
     *
     * @return
     */
    public StringProperty consumerKeyProperty() {
        return this.consumerKey;
    }

    /**
     *
     * @return
     */
    public StringProperty consumerSecretProperty() {
        return this.consumerSecret;
    }

    /**
     *
     * @return
     */
    public StringProperty accessTokenProperty() {
        return this.accessToken;
    }

    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + Objects.hashCode(this.consumerKey);
        hash = 13 * hash + Objects.hashCode(this.consumerSecret);
        hash = 13 * hash + Objects.hashCode(this.accessToken);
        hash = 13 * hash + Objects.hashCode(this.accessTokenSecret);
        return hash;
    }

    /**
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PropertiesTwitter other = (PropertiesTwitter) obj;
        if (!Objects.equals(this.consumerKey.get(), other.consumerKey.get())) {
            return false;
        }
        if (!Objects.equals(this.consumerSecret.get(), other.consumerSecret.get())) {
            return false;
        }
        if (!Objects.equals(this.accessTokenSecret.get(), other.accessTokenSecret.get())) {
            return false;
        }
        return Objects.equals(this.accessToken.get(), other.accessToken.get());
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "TwitterProperties\n{\t" + "consumerKey=" + consumerKey.get() + "\n\tconsumerSecret="
                + consumerSecret.get() + "\n\taccessToken=" + accessToken.get() + "\n\taccessTokenSecret";

    }
}

package com.shirin.propertiesconfig.bean;

import java.util.Date;
import javafx.scene.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.Status;
import twitter4j.URLEntity;

/**
 * This class Is bean for Twitter information for each user It provides all
 * Information and used with TwitterEngine class And TwitterInfoCell
 *
 * @author Shirin Oct 2019
 */
public class TwitterInfo implements TwitterInterface {

    private final static Logger LOG = LoggerFactory.getLogger(TwitterInfo.class);
    private final Status status;

    public TwitterInfo(Status status) {
        this.status = status;
    }

    public String getName() {
        return status.getUser().getName();
    }

    public String getDescription() {
        return status.getUser().getDescription();
    }

    /**
     * get ScreenName of the user it gives UserScreen name without @
     *
     * @return
     */
    public String getScreenName() {
        LOG.info("inside ScreenName");
        return status.getUser().getScreenName();
    }

    public int getFollowersCount() {
        return status.getUser().getFollowersCount();
    }

    public int getFriendsCount() {
        return status.getUser().getFriendsCount();
    }

    /**
     * This method shows if follow request sent
     *
     * @return
     */
    public boolean isFollow() {
        LOG.info("inside IsFollow method");
        return status.getUser().isFollowRequestSent();
    }

    public Date CreatedAt() {
        LOG.info("inside IsFollow method");
        return status.getCreatedAt();
    }

    /**
     * it gets text of the status
     *
     * @return
     */
    @Override
    public String getText() {
        LOG.info("inside getText");
        return status.getText();

    }

    /**
     * Gives URL for image
     *
     * @return
     */
    public String getURL() {
        LOG.info("inside GetImageURL");
        return status.getUser().getProfileImageURL();
    }

    /**
     * get ScreenName of the user it gives UserScreen name without @
     *
     * @return
     */
    public String getHandle() {
        LOG.info("inside ScreenName");
        return status.getUser().getScreenName();

    }

    public int getRetweetCount() {
        return status.getRetweetCount();
    }

    /**
     * It gives number of likes for each Tweet
     *
     * @return
     */
    public int getFavoriteCount() {
        LOG.info("inside favoritecount");
        return this.status.getFavoriteCount();

    }

    /**
     * it checks if tweet is favorited
     *
     * @return
     */
    public boolean isFavorited() {
        LOG.info("inside is favorited");
        return status.isFavorited();
    }

    /**
     * gives if Status is retweeded
     *
     * @return
     */
    public Status getRetweetedStatus() {
        LOG.info("inside is getRetweetedStatus");
        return status.getRetweetedStatus();
    }

    /**
     * gives id of the status
     *
     * @return
     */
    public long getId() {
        LOG.info("inside getid");
        return this.status.getId();
    }

    /**
     * boolean if the tweet is retweed by me
     *
     * @return
     */
    public boolean isRetweetedByMe() {
        LOG.info("inside is retweetedbyme");
        return this.status.isRetweetedByMe();
    }

    /**
     * get total url of the status
     *
     * @return
     */
    public String geturl() {
        LOG.info("inside getUrl");
        String url = "";
        URLEntity[] urls = status.getURLEntities();
        for (URLEntity urlEntity : urls) {
            url = urlEntity.getURL();
        }
        return url;
    }

    /**
     * Gives id of the tweet
     *
     * @return
     */
    public long getQuotedId() {
        LOG.info("inside getQuotedStatusId");
        return status.getQuotedStatusId();
    }

    @Override
    public void setGraphic(Node arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}

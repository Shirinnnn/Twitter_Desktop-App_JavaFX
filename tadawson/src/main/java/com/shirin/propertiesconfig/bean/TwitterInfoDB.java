package com.shirin.propertiesconfig.bean;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javafx.scene.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.Status;
import twitter4j.URLEntity;

/**
 * This class has all information about a twitter account for database
 *
 * @author Shirin Eskandari
 */
public class TwitterInfoDB implements TwitterInterface {

    private final static Logger LOG = LoggerFactory.getLogger(TwitterInfoDB.class);
    private String name;
    private String screenName;
    private boolean isFollow;
    private Timestamp dateOfTweet;
    private String tweeText;
    private String profileURL;
    private int favoriteCount;
    private int retweetCount;
    private boolean isFavorited;
    private int id;
    private boolean isRetweetedByMe;
    private int quotedId;
    private List<TweetsDB> listOfTweets;
    private long iduser;

    public TwitterInfoDB() {
        this(-1, -1, "", "", false, Timestamp.valueOf(LocalDateTime.now()), "", "", -1, false, false, -1, -1);
    }

    public TwitterInfoDB(int id, int quotedId, String name, String screenName, boolean isFollow,
            Timestamp dateOfTweet, String tweeText, String profileURL, int favoriteCount, boolean isFavorited,
            boolean isRetweetedByMe, long iduse, int retweetCount) {
        this.name = name;
        this.screenName = screenName;
        this.isFollow = isFollow;
        this.dateOfTweet = dateOfTweet;
        this.tweeText = tweeText;
        this.profileURL = profileURL;
        this.favoriteCount = favoriteCount;
        this.isFavorited = isFavorited;
        this.id = id;
        this.isRetweetedByMe = isRetweetedByMe;
        this.quotedId = quotedId;
        this.listOfTweets = new ArrayList<>();
        this.iduser = iduse;
        this.retweetCount = retweetCount;
    }

    public int getRetweetCount() {
        return retweetCount;
    }

    public void setRetweetCount(int retweetCount) {
        this.retweetCount = retweetCount;
    }

    public Timestamp getDateOfTweet() {
        return dateOfTweet;
    }

    public void setDateOfTweet(Timestamp dateOfTweet) {
        this.dateOfTweet = dateOfTweet;
    }

    public String getTweeText() {
        return tweeText;
    }

    public void setTweeText(String tweeText) {
        this.tweeText = tweeText;
    }

    public String getProfileURL() {
        return profileURL;
    }

    public void setProfileURL(String profileURL) {
        this.profileURL = profileURL;
    }

    public boolean isIsFavorited() {
        return isFavorited;
    }

    public void setIsFavorited(boolean isFavorited) {
        this.isFavorited = isFavorited;
    }

    public boolean isIsRetweetedByMe() {
        return isRetweetedByMe;
    }

    public void setIsRetweetedByMe(boolean isRetweetedByMe) {
        this.isRetweetedByMe = isRetweetedByMe;
    }

    public long getIduse() {
        return iduser;
    }

    public void setIduse(long iduse) {
        this.iduser = iduse;
    }

    public List<TweetsDB> getListOfTweets() {
        return listOfTweets;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * get ScreenName of the user it gives UserScreen name without @
     *
     * @return
     */
    public String getScreenName() {
        LOG.info("inside ScreenName");
        return screenName;
    }

    public void setScreenName(String screenName) {
        LOG.info("inside set ScreenName");
        this.screenName = screenName;
    }

    /**
     * This method shows if follow request sent
     *
     * @return
     */
    public boolean getIsFollow() {
        LOG.info("inside IsFollow method");
        return isFollow;
    }

    public void setIsFollow(boolean bool) {
        LOG.info("inside IsFollow method");
        this.isFollow = bool;
    }

    public Timestamp getCreatedAt() {
        LOG.info("inside IsFollow method");
        return dateOfTweet;
    }

    public void setCreatedAt(Timestamp dateOfTweet) {
        LOG.info("inside IsFollow method");
        this.dateOfTweet = dateOfTweet;
    }

    /**
     * it gets text of the status
     *
     * @return
     */
    public String getTweetText() {
        LOG.info("inside getText");
        return tweeText;

    }

    public void setTweetText(String tweetText) {
        LOG.info("inside getText");
        this.tweeText = tweetText;

    }

    /**
     * Gives URL for profile photo
     *
     * @return
     */
    public String getURL() {
        LOG.info("inside GetURL");
        return profileURL;
    }

    public void setURL(String profileURL) {
        LOG.info("inside GetIURL");
        this.profileURL = profileURL;
    }

    /**
     * It gives number of likes for each Tweet
     *
     * @return
     */
    public int getFavoriteCount() {
        LOG.info("inside favoritecount");
        return favoriteCount;

    }

    public void setFavoriteCount(int favoriteCount) {
        LOG.info("inside favoritecount");
        this.favoriteCount = favoriteCount;

    }

    /**
     * it checks if tweet is favorited
     *
     * @return
     */
    public boolean isFavorited() {
        LOG.info("inside is favorited");
        return isFavorited;
    }

    public void isFavorited(boolean bool) {
        LOG.info("inside is favorited");
        this.isFavorited = isFavorited;
    }

    /**
     * gives id of the status
     *
     * @return
     */
    public long getId() {
        LOG.info("inside getid");
        long idd = id;
        return idd;
    }

    public void setId(int id) {
        LOG.info("inside getid");
        this.id = id;
    }

    /**
     * boolean if the tweet is retweed by me
     *
     * @return
     */
    public boolean isRetweetedByMe() {
        LOG.info("inside is retweetedbyme");
        return isRetweetedByMe;
    }

    public void isRetweetedByMe(boolean bool) {
        LOG.info("inside is retweetedbyme");
        this.isRetweetedByMe = isRetweetedByMe;
    }

    /**
     * get total url of the status
     *
     * @return
     */

    /**
     * Gives id of the tweet
     *
     * @return
     */
    public long getQuotedId() {
        LOG.info("inside getQuotedStatusId");
        long quotedIdd = quotedId;
        return quotedIdd;
    }

    public void setQuotedId(int quotedId) {
        LOG.info("inside getQuotedStatusId");
        this.quotedId = quotedId;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + Objects.hashCode(this.name);
        hash = 11 * hash + Objects.hashCode(this.screenName);
        hash = 11 * hash + (this.isFollow ? 1 : 0);
        hash = 11 * hash + Objects.hashCode(this.dateOfTweet);
        hash = 11 * hash + Objects.hashCode(this.tweeText);
        hash = 11 * hash + Objects.hashCode(this.profileURL);
        hash = 11 * hash + this.favoriteCount;
        hash = 11 * hash + (this.isFavorited ? 1 : 0);
        hash = 11 * hash + this.id;
        hash = 11 * hash + (this.isRetweetedByMe ? 1 : 0);
        hash = 11 * hash + this.quotedId;
        hash = 11 * hash + Objects.hashCode(this.listOfTweets);
        return hash;
    }

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
        final TwitterInfoDB other = (TwitterInfoDB) obj;
        if (this.isFollow != other.isFollow) {
            return false;
        }
        if (this.favoriteCount != other.favoriteCount) {
            return false;
        }
        if (this.isFavorited != other.isFavorited) {
            return false;
        }
        if (this.id != other.id) {
            return false;
        }
        if (this.isRetweetedByMe != other.isRetweetedByMe) {
            return false;
        }
        if (this.quotedId != other.quotedId) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.screenName, other.screenName)) {
            return false;
        }
        if (!Objects.equals(this.tweeText, other.tweeText)) {
            return false;
        }
        if (!Objects.equals(this.profileURL, other.profileURL)) {
            return false;
        }
        if (!Objects.equals(this.dateOfTweet, other.dateOfTweet)) {
            return false;
        }
        //  return Objects.equals(this.listOfGames, other.listOfGames);
        return true;
    }

    @Override
    public String toString() {
        return "TwitterInfoDB{" + "name=" + name + ", screenName=" + screenName
                + ", isFollow=" + isFollow + ", dateOfTweet=" + dateOfTweet + ", tweeText=" + tweeText
                + ", profileURL=" + profileURL + ", favoriteCount=" + favoriteCount + ", isFavorited="
                + isFavorited + ", id=" + id + ", isRetweetedByMe=" + isRetweetedByMe + ", quotedId="
                + quotedId + ", listOfTweets=" + listOfTweets + '}';
    }
//override methods from interface

    @Override
    public void setGraphic(Node arg0) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getText() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean isFollow() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Date CreatedAt() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getHandle() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Status getRetweetedStatus() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String geturl() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getDescription() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getFollowersCount() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getFriendsCount() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

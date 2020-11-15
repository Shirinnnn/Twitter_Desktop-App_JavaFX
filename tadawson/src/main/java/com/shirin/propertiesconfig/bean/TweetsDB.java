package com.shirin.propertiesconfig.bean;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class has all information about a tweet for database
 *
 * @author Shirin Eskandari
 */
public class TweetsDB {

    private final static Logger LOG = LoggerFactory.getLogger(TweetsDB.class);
    private int Ttwitterid;
    private String tweet_text;
    private int favorite_count;
    private boolean is_favorited;
    private boolean retweeted_by_me;
    private int tweetId;
    private Timestamp dateOfTweetdb;

    /**
     * gets date of the tweet
     *
     * @return
     */
    public Timestamp getDateOfTweetdb() {
        return dateOfTweetdb;
    }

    /**
     * set for date of tweet in database
     *
     * @param dateOfTweetdb
     */
    public void setDateOfTweetdb(Timestamp dateOfTweetdb) {
        this.dateOfTweetdb = dateOfTweetdb;
    }

    /**
     * constructor
     */
    public TweetsDB() {
        this(-1, -1, "", -1, false, false, Timestamp.valueOf(LocalDateTime.now()));
    }

    public TweetsDB(int twitterId, int TtweetId, String tweet_text, int favorite_count,
            boolean is_favorited, boolean retweeted_by_me, Timestamp dateOfTweetdb) {
        this.Ttwitterid = twitterId;
        this.tweet_text = tweet_text;
        this.favorite_count = favorite_count;
        this.is_favorited = is_favorited;
        this.retweeted_by_me = retweeted_by_me;
        this.tweetId = tweetId;
        this.dateOfTweetdb = dateOfTweetdb;

    }

    public Timestamp getDateOfTweet() {
        return dateOfTweetdb;
    }

    public void setDateOfTweet(Timestamp dateOfTweetdb) {
        this.dateOfTweetdb = dateOfTweetdb;
    }

    public int getTtwitterid() {
        return Ttwitterid;
    }

    public void setTtwitterid(int Ttwitterid) {
        this.Ttwitterid = Ttwitterid;
    }

    public String getTweet_text() {
        return tweet_text;
    }

    public void setTweet_text(String tweet_text) {
        this.tweet_text = tweet_text;
    }

    public int getFavorite_count() {
        return favorite_count;
    }

    public void setFavorite_count(int favorite_count) {
        this.favorite_count = favorite_count;
    }

    public boolean getIs_favorited() {
        return is_favorited;
    }

    public void setIs_favorited(boolean is_favorited) {
        this.is_favorited = is_favorited;
    }

    public boolean getRetweeted_by_me() {
        return retweeted_by_me;
    }

    public void setRetweeted_by_me(boolean retweeted_by_me) {
        this.retweeted_by_me = retweeted_by_me;
    }

    public int getTweetId() {
        return tweetId;
    }

    public void setTweetId(int tweetId) {
        this.tweetId = tweetId;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + this.Ttwitterid;
        hash = 53 * hash + Objects.hashCode(this.tweet_text);
        hash = 53 * hash + this.favorite_count;
        hash = 53 * hash + (this.is_favorited ? 1 : 0);
        hash = 53 * hash + (this.retweeted_by_me ? 1 : 0);
        hash = 53 * hash + this.tweetId;
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
        final TweetsDB other = (TweetsDB) obj;
        if (this.Ttwitterid != other.Ttwitterid) {
            return false;
        }
        if (this.favorite_count != other.favorite_count) {
            return false;
        }
        if (this.is_favorited != other.is_favorited) {
            return false;
        }
        if (this.retweeted_by_me != other.retweeted_by_me) {
            return false;
        }
        if (this.tweetId != other.tweetId) {
            return false;
        }
        if (!Objects.equals(this.tweet_text, other.tweet_text)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TweetsDB{" + "Ttwitterid=" + Ttwitterid + ", tweet_text=" + tweet_text
                + ", favorite_count=" + favorite_count + ", is_favorited=" + is_favorited
                + ", retweeted_by_me=" + retweeted_by_me + ", tweetId=" + tweetId + '}';
    }
}

package com.shirin.tadawson.persistence;

import com.shirin.propertiesconfig.bean.TweetsDB;
import com.shirin.propertiesconfig.bean.TwitterInfoDB;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * CRUD implementations
 *
 * @author eskan
 */
public class TwitterDAOImpl implements TwitterDAO {

    private final static Logger LOG = LoggerFactory.getLogger(TwitterDAOImpl.class);

    private final String url = "jdbc:mysql://localhost:3306/TWITTERDB?autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true";
    private final String user = "root";
    private final String password = "dawson";

    /**
     * Default constructor
     */
    public TwitterDAOImpl() {
        super();
    }

    /**
     * create Twitter user
     *
     * @param infoDb
     * @return
     * @throws SQLException
     */
    public int create(TwitterInfoDB infoDb) throws SQLException {
        LOG.info("Create Twitter user");
        int records;
        int recordNum;
        String sql = "INSERT INTO TWITTER (SCREENNAME,FULL_NAME,PROFILE_IMAGE_URL,CREATED_AT,FOLLOWERS_COUNT,IDUSE,RETWEET_COUNT)"
                + "values (?, ?, ?, ?,?,?,?)";
        try ( Connection connection = DriverManager.getConnection(url, user, password);  PreparedStatement pStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            pStatement.setString(1, infoDb.getScreenName());
            pStatement.setString(2, infoDb.getName());
            pStatement.setString(3, infoDb.getURL());
            pStatement.setTimestamp(4, infoDb.getCreatedAt());
            pStatement.setBoolean(5, infoDb.getIsFollow());
            pStatement.setLong(6, infoDb.getIduse());
             pStatement.setLong(7, infoDb.getRetweetCount());
            records = pStatement.executeUpdate();
            try ( ResultSet rs = pStatement.getGeneratedKeys();) {
                recordNum = -1;
                if (rs.next()) {
                    recordNum = rs.getInt(1);
                }
                infoDb.setId(recordNum);
                LOG.debug("New record ID is " + recordNum);
            }
        } catch (SQLException ex) {
            LOG.debug("create error", ex.getMessage());
            throw ex;
        }
        return recordNum;
    }

    /**
     * creates Tweet
     *
     * @param tweetDb
     * @return
     * @throws SQLException
     */
    public int createTweet(TweetsDB tweetDb) throws SQLException {
        LOG.info("Create Tweet");
        int records;
        int recordNum;
        String sql = "INSERT INTO TWEETS (TWITTERID,DATEOFTWEET,TWEETEXT,FAVORITECOUNT,IS_FAVORITED,RETWEETED_BY_ME)"
                + "values (?, ?, ?, ?,?,?)";
        try ( Connection connection = DriverManager.getConnection(url, user, password);  PreparedStatement pStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            pStatement.setInt(1, tweetDb.getTtwitterid());
            pStatement.setTimestamp(2, tweetDb.getDateOfTweet());
            pStatement.setString(3, tweetDb.getTweet_text());
            pStatement.setInt(4, tweetDb.getFavorite_count());
            pStatement.setBoolean(5, tweetDb.getIs_favorited());
            pStatement.setBoolean(6, tweetDb.getRetweeted_by_me());
            records = pStatement.executeUpdate();
            try ( ResultSet rs = pStatement.getGeneratedKeys();) {
                recordNum = -1;
                if (rs.next()) {
                    recordNum = rs.getInt(1);
                }
                tweetDb.setTweetId(recordNum);
                LOG.debug("New record ID is " + recordNum);
            }
        } catch (SQLException ex) {
            LOG.debug("create error", ex.getMessage());
            throw ex;
        }
        return recordNum;
    }

    /**
     * finds all information about twitter user with specific id
     *
     * @param id
     * @return
     * @throws SQLException
     */
    public TwitterInfoDB findID(int id) throws SQLException {
        LOG.info("find twitter user with id");
        TwitterInfoDB infoDb = new TwitterInfoDB();
        String selectQuery = "SELECT TWITTERID,SCREENNAME,FULL_NAME,PROFILE_IMAGE_URL,CREATED_AT,FOLLOWERS_COUNT FROM TWITTER WHERE IDUSE = ?";
        try ( Connection connection = DriverManager.getConnection(url, user, password); // You must use PreparedStatements to guard against SQL Injection
                  PreparedStatement pStatement = connection.prepareStatement(selectQuery);) {
            pStatement.setLong(1, id);

            try ( ResultSet resultSet = pStatement.executeQuery()) {
                if (resultSet.next()) {

                    LOG.info("INT IS" + resultSet.getInt("TWITTERID"));
                    infoDb = makeTwitterUser(resultSet);
                    findAllTweets(infoDb);

                }
            }
        } catch (SQLException ex) {
            LOG.debug("findID error", ex.getMessage());
            throw ex;
        }
        return infoDb;
    }

    /**
     * Create a Twitter object from the current row in the ResultSet
     *
     * @param rs
     * @return
     * @throws SQLException
     */
    private TwitterInfoDB makeTwitterUser(ResultSet rs) throws SQLException {
        LOG.info("makeTwitterUser");
        TwitterInfoDB infoDb = new TwitterInfoDB();
        infoDb.setId(rs.getInt("TWITTERID"));
        infoDb.setScreenName(rs.getString("SCREENNAME"));
        infoDb.setName(rs.getString("FULL_NAME"));
        infoDb.setURL(rs.getString("PROFILE_IMAGE_URL"));
        infoDb.setCreatedAt(rs.getTimestamp("CREATED_AT"));
        infoDb.setIsFollow(rs.getBoolean("FOLLOWERS_COUNT"));
        infoDb.setIduse(rs.getLong("IDUSE"));
        infoDb.setRetweetCount(rs.getInt("RETWEET_COUNT"));
        return infoDb;
    }

    /**
     * get all tweets belongs to that twitterid
     *
     * @param infoDb
     * @throws SQLException
     */
    private void findAllTweets(TwitterInfoDB infoDb) throws SQLException {
        LOG.info("get all tweets belongs to that twitterid");
        String selectQuery = "SELECT TWEET_ID,TWITTERID, DATEOFTWEET, TWEETEXT, FAVORITECOUNT,"
                + "IS_FAVORITED,RETWEETED_BY_ME FROM TWEETS WHERE TWITTERID = ?";

        try ( Connection connection = DriverManager.getConnection(url, user, password);  PreparedStatement pStatement = connection.prepareStatement(selectQuery)) {
            pStatement.setInt(1, (int) infoDb.getId());
            try ( ResultSet resultSet = pStatement.executeQuery()) {
                while (resultSet.next()) {
                    infoDb.getListOfTweets().add(makeTweets(resultSet));
                }
            }
        } catch (SQLException ex) {
            LOG.debug("findAllTweets error", ex.getMessage());
            throw ex;
        }
    }

    /**
     * Create a Tweet object from the current row in the ResultSet
     *
     * @param rs
     * @return
     * @throws SQLException
     */
    private TweetsDB makeTweets(ResultSet rs) throws SQLException {
        LOG.info("makeTweets");
        TweetsDB tweets = new TweetsDB();
        tweets.setTweetId(rs.getInt("TWEET_ID"));
        tweets.setTweet_text(rs.getString("TWEETEXT"));
        tweets.setFavorite_count(rs.getInt("FAVORITECOUNT"));
        tweets.setRetweeted_by_me(rs.getBoolean("RETWEETED_BY_ME"));
        tweets.setIs_favorited(rs.getBoolean("IS_FAVORITED"));
        tweets.setTtwitterid(rs.getInt("TWITTERID"));
        return tweets;
    }

    /**
     * This method makes timeline from the database
     *
     * @return
     * @throws SQLException
     */
    private ObservableList<TwitterInfoDB> TwitterTimeline() throws SQLException {
        LOG.info("TwitterTimeline");
        ObservableList<TwitterInfoDB> dataTwitter = FXCollections.observableArrayList();

        String selectQuery = "SELECT * from twitter";

        try ( Connection connection = DriverManager.getConnection(url, user,
                password);  PreparedStatement pStatement = connection
                        .prepareStatement(selectQuery);  ResultSet resultSet = pStatement.executeQuery()) {
            while (resultSet.next()) {
                dataTwitter.add(makeTwitterUser(resultSet));
            }
        }
        LOG.info("# of records found : " + dataTwitter.size());
        return dataTwitter;
    }

    /**
     * Retrieve all the Twitter users records and add the tweets of each user
     *
     * @return
     * @throws SQLException
     */
    public List<TwitterInfoDB> findAll() throws SQLException {
        LOG.info("find all info inside the db");
        List<TwitterInfoDB> rows = new ArrayList<>();

        String selectQuery = "SELECT TWITTERID,SCREENNAME,FULL_NAME,PROFILE_IMAGE_URL,CREATED_AT,FOLLOWERS_COUNT,IDUSE,RETWEET_COUNT"
                + " FROM TWITTER";
        try ( Connection connection = DriverManager.getConnection(url, user, password);  PreparedStatement pStatement = connection.prepareStatement(selectQuery);  ResultSet resultSet = pStatement.executeQuery()) {
            while (resultSet.next()) {
                TwitterInfoDB twitterUser = makeTwitterUser(resultSet);
                findAllTweets(twitterUser);
                rows.add(twitterUser);
            }
        } catch (SQLException ex) {
            LOG.debug("findAll error", ex.getMessage());
            throw ex;
        }
        return rows;
    }

    /**
     * This method deletes a single record based on the criteria of the primary
     * key field ID value. It should return either 0 meaning that there is no
     * record with that ID or 1 meaning a single record was deleted. If the
     * value is greater than 1 then something unexpected has happened. A
     * criteria other than ID may delete more than one record.
     *
     * @param id The primary key to use to identify the record that must be
     * deleted
     * @return The number of records deleted, should be 0 or 1
     * @throws SQLException
     */
    public int deleteTwitterUser(int id) throws SQLException {
        LOG.info("deletes twitter user");

        int result;

        String deleteQuery = "DELETE FROM TWITTER WHERE TWITTERID = ?";

        // Connection is only open for the operation and then immediately closed
        try ( Connection connection = DriverManager.getConnection(url, user, password);  PreparedStatement ps = connection.prepareStatement(deleteQuery);) {
            ps.setInt(1, id);
            result = ps.executeUpdate();
        }
        LOG.info("# of records deleted : " + result);
        return result;
    }

    /**
     * Delete each tweet which belongs to specific user
     *
     * @param id
     * @return
     * @throws SQLException
     */
    public int deleteTweet(int id) throws SQLException {
        LOG.info("deletes tweet");
        int result;
        String deleteQuery = "DELETE FROM TWEETS WHERE TWITTERID = ?";
        try ( Connection connection = DriverManager.getConnection(url, user, password);  PreparedStatement ps = connection.prepareStatement(deleteQuery);) {
            ps.setInt(1, id);
            result = ps.executeUpdate();
        }
        LOG.info("# of records deleted : " + result);
        return result;
    }

    /**
     * This method will update all the fields of a record of twitter user except
     * ID. Usually updates are tied to specific fields and so only those fields
     * need appear in the SQL statement.
     *
     * @param infoDB An object with an existing ID and new data in the fields
     * @return The number of records updated, should be 0 or 1
     * @throws SQLException
     *
     */
    public int updateTwitter(TwitterInfoDB infoDB) throws SQLException {
        LOG.info("updateTwitter");
        int result;
        String updateQuery = "UPDATE TWITTER SET SCREENNAME=?, FULL_NAME=?, PROFILE_IMAGE_URL=?, CREATED_AT=?, FOLLOWERS_COUNT=?WHERE TWITTERID = ?";
        try ( Connection connection = DriverManager.getConnection(url, user, password);  PreparedStatement ps = connection.prepareStatement(updateQuery);) {
            ps.setString(1, infoDB.getScreenName());
            ps.setString(2, infoDB.getName());
            ps.setString(3, infoDB.getURL());
            ps.setTimestamp(4, infoDB.getCreatedAt());
            ps.setBoolean(5, infoDB.getIsFollow());
            ps.setInt(6, (int) infoDB.getId());

            result = ps.executeUpdate();
        }
        LOG.info("# of records updated : " + result);
        return result;
    }

    /**
     * This method will update all the fields of a record of tweet except ID.
     * Usually updates are tied to specific fields and so only those fields need
     * appear in the SQL statement.
     *
     * @param infoDB An object with an existing ID and new data in the fields
     * @return The number of records updated, should be 0 or 1
     * @throws SQLException
     *
     */
    public int updateTweet(TweetsDB tweets) throws SQLException {
        LOG.info("update tweet");
        int result;
        String updateQuery = "UPDATE TWEETS SET DATEOFTWEET=?, TWEETEXT=?, FAVORITECOUNT=?, IS_FAVORITED=?, RETWEETED_BY_ME=?WHERE TWITTERID = ?";
        try ( Connection connection = DriverManager.getConnection(url, user, password);  PreparedStatement ps = connection.prepareStatement(updateQuery);) {
            ps.setTimestamp(1, tweets.getDateOfTweet());
            ps.setString(2, tweets.getTweet_text());
            ps.setInt(3, tweets.getFavorite_count());
            ps.setBoolean(4, tweets.getIs_favorited());
            ps.setBoolean(5, tweets.getRetweeted_by_me());

            result = ps.executeUpdate();
        }
        LOG.info("# of records updated : " + result);
        return result;
    }

}

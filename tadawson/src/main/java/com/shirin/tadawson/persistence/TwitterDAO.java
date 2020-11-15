package com.shirin.tadawson.persistence;

import com.shirin.propertiesconfig.bean.TweetsDB;
import com.shirin.propertiesconfig.bean.TwitterInfoDB;
import java.sql.SQLException;
import java.util.List;

/**
 * Interface for CRUD methods
 *
 * @author eskan
 */
public interface TwitterDAO {

    public int create(TwitterInfoDB infoDb) throws SQLException;

    public int createTweet(TweetsDB tweetDb) throws SQLException;

    public TwitterInfoDB findID(int id) throws SQLException;

    public List<TwitterInfoDB> findAll() throws SQLException;

    public int updateTwitter(TwitterInfoDB infoDB) throws SQLException;

    public int updateTweet(TweetsDB tweets) throws SQLException;

    public int deleteTwitterUser(int id) throws SQLException;

    public int deleteTweet(int id) throws SQLException;
}

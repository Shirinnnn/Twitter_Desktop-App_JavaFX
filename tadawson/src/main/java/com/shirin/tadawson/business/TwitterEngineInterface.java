package com.shirin.tadawson.business;

import java.util.List;
import twitter4j.OEmbedRequest;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.User;

/**
 * Interface for TwitterEngine Class
 *
 * @author Shirin oct2019
 */
public interface TwitterEngineInterface {

    public Status createFavorite(long id) throws TwitterException;

    public String createTweet(String tweet) throws TwitterException;

    public Status destroyFavorite(long id) throws TwitterException;

    public boolean friendshipStatus(String targetId) throws TwitterException;

    public String sendDirectMessage(String recipientName, String msg) throws TwitterException;

    public String createTweet2(String msg, String username, long tweetId) throws TwitterException;

    public String replyToComment(String msg, long replyId, String username) throws TwitterException;

    public User showUser(long id) throws TwitterException;

    public List<Status> getTimeLine(int page) throws TwitterException;

    public List<Status> getMentionsTimeLine(int page) throws TwitterException;

    public List<Status> searchQuery(String searchText) throws TwitterException;

    public User createFriendship(String id) throws TwitterException;

    public User destroyFriendship(String id) throws TwitterException;

    public Status retweetStatus(long id) throws TwitterException;

    public Status unRetweet(long id) throws TwitterException;

    public void helperMakeComment(String replyMessage, long messageId) throws TwitterException;

    public OEmbedRequest makeEmbed(String url, long id) throws TwitterException;

}

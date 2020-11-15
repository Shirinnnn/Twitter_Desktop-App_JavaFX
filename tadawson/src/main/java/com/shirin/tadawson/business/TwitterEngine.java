package com.shirin.tadawson.business;

import com.shirin.propertiesconfig.bean.TwitterInfoDB;
import com.shirin.tadawson.persistence.TwitterDAOImpl;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.DirectMessage;
import twitter4j.DirectMessageList;
import twitter4j.IDs;
import twitter4j.OEmbedRequest;
import twitter4j.Paging;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Relationship;
import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;

/**
 * methods to send and receive tweet and direct message It is responsible for
 * all methods for creation/deletion/change of the status of the twitter info
 *
 * @author Shirin Eskandari
 */
public class TwitterEngine implements TwitterEngineInterface {

    private final static Logger LOG = LoggerFactory.getLogger(TwitterEngine.class);
    Twitter twitterInstance = TwitterFactory.getSingleton();

    /**
     * Send direct message
     *
     * @param recipientName
     * @param msg
     * @return
     * @throws TwitterException
     */
    public String sendDirectMessage(String recipientName, String msg) throws TwitterException {
        LOG.debug("sendDirectMessage");
        LOG.info(recipientName);
        LOG.info(msg);
        Twitter twitter = twitterInstance;
        DirectMessage message = twitter.sendDirectMessage(recipientName, msg);
        return message.getText();
    }

    /**
     * receive direct messages set the max number and cursor
     *
     * @param maxNumber
     * @param next_cursor
     * @return
     * @throws TwitterException
     */
    public DirectMessageList receiveDirectMessages(int maxNumber, String next_cursor) throws TwitterException {
        LOG.debug("receiveDirectMessages");
        Twitter twitter = twitterInstance;
        return twitter.directMessages().getDirectMessages(maxNumber, next_cursor);
    }

    /**
     * get all the direct messages
     *
     * @param page
     * @return
     * @throws TwitterException
     */
    public DirectMessageList getDirectMessages(int page) throws TwitterException {
        LOG.debug("getDirectMessages");
        Twitter twitter = twitterInstance;
        return twitter.getDirectMessages(page);
    }

    /**
     * show direct message of special id (user)
     *
     * @param id
     * @return
     * @throws TwitterException
     */
    public DirectMessage showDirectMessage(long id) throws TwitterException {
        LOG.debug("showDirectMessages");
        Twitter twitter = twitterInstance;
        return twitter.showDirectMessage(id);
    }

    /**
     * get list of the users who sent direct messages
     *
     * @param list
     * @return
     * @throws TwitterException
     */
    public List<User> usersDirectMessage(DirectMessageList list) throws TwitterException {
        LOG.debug("usersDirectMessages");
        Twitter twitter = twitterInstance;

        long ids[] = new long[list.size()];
        for (int i = 0; i < list.size(); i++) {
            ids[i] = list.get(i).getRecipientId();
            LOG.info(list.get(i).getId() + "id");
            LOG.info(list.get(i).getRecipientId() + "id");
            LOG.info(list.get(i).getSenderId() + "id");
        }
        List<User> users = twitter.lookupUsers(ids);
        return users;
    }

    /**
     * Get time line of all tweets of current user for profile
     *
     * @param page
     * @return
     * @throws TwitterException
     */
    public List<Status> getTweetsTimeLine(int page) throws TwitterException {
        LOG.debug("getTweetsTimeLine");
        Twitter twitter = twitterInstance;
        Paging paging = new Paging();
        paging.setCount(20);
        paging.setPage(page);
        List<Status> tweetTimeline = twitter.getUserTimeline(paging);
        return tweetTimeline;
    }

    /**
     * Get time line of all getMyTweetsRetweetedTimeLine of current user for
     * profile
     *
     * @param page
     * @return
     * @throws TwitterException
     */
    public List<Status> getMyTweetsRetweetedTimeLine(int page) throws TwitterException {
        LOG.debug("getMyTweetsRetweetedTimeLine");
        Twitter twitter = twitterInstance;
        Paging paging = new Paging();
        paging.setCount(20);
        paging.setPage(page);
        List<Status> myTweetsRetweetedTimeline = twitter.getRetweetsOfMe(paging);
        return myTweetsRetweetedTimeline;
    }

    /**
     * Get time line of all getRetweetsByMeTimeLine of current user for profile
     *
     * @param page
     * @return
     * @throws TwitterException
     */
    public List<Status> getRetweetsByMeTimeLine(int page) throws TwitterException {
        LOG.debug("getRetweetsByMeTimeLine");
        Twitter twitter = twitterInstance;
        Paging paging = new Paging();
        paging.setCount(20);
        paging.setPage(page);
        List<Status> retweetsByMeTimeline = getRetweetsByMeList(twitter.getUserTimeline());
        return retweetsByMeTimeline;
    }

    /**
     * Helper method for this method -> getRetweetsByMeTimeLine
     *
     * @param statuses
     * @return
     */
    private List<Status> getRetweetsByMeList(List<Status> statuses) {
        LOG.debug("getRetweetsByMeList");
        List<Status> statusesForRetweet = new ArrayList<>();
        for (Status status : statuses) {
            if (status.isRetweeted()) {
                statusesForRetweet.add(status);
            }
        }
        return statusesForRetweet;
    }

    /**
     * create tweet for current user
     *
     * @param tweet
     * @return
     * @throws TwitterException
     */
    public String createTweet(String tweet) throws TwitterException {
        LOG.debug("createTweet: " + tweet);
        Twitter twitter = twitterInstance;
        Status status = twitter.updateStatus(tweet);
        return status.getText();
    }

    /**
     * Send a tweet Embed
     *
     *
     * @param tweet
     * @return
     * @throws TwitterException
     */
    public String createTweet(String tweet, OEmbedRequest embed) throws TwitterException {
        LOG.debug("createTweet: " + tweet);
        Status status;
        Twitter twitter = twitterInstance;

        if (embed == null) {
            status = twitter.updateStatus(tweet);
            return status.getText();
        } else {
            tweet = tweet + twitter.tweets().getOEmbed(embed).getURL();
            status = twitter.updateStatus(tweet);
            return status.getText();
        }
    }

    /**
     * get followers of the current user who they can direct message
     *
     * @return
     * @throws TwitterException
     */
    public List<Status> getFollowers() throws TwitterException {
        LOG.debug("getFollowers");
        List<Status> friendlist = new ArrayList<>();
        Twitter twitter = twitterInstance;
         IDs ids2 = twitter.friendsFollowers().getFriendsIDs(twitter.getId());
        IDs ids = twitter.getFollowersIDs(twitter.getId());
        
        long idss[] = ids2.getIDs();
        for (int i = 0; i < idss.length; i++) {
            friendlist.add(getStatus(idss[i]));
            LOG.info(idss[i] + "");
        }
        return friendlist;
    }

    /**
     * create url for retweet which is a message plus regular tweet
     *
     * @param msg
     * @param username
     * @param tweetId
     * @return
     * @throws TwitterException
     */
    public String createTweet2(String msg, String username, long tweetId) throws TwitterException {
        LOG.debug("create tweet helper");
        Twitter twitter = twitterInstance;
        String url = msg + "   https://twitter.com/" + username + "/status/" + tweetId;
        StatusUpdate statusUpdate = new StatusUpdate(url);
        statusUpdate.inReplyToStatusId(tweetId);
        Status status = twitter.updateStatus(statusUpdate);

        return status.getText();
    }

    /**
     * This method is for reply to the comment
     *
     * @param msg
     * @param replyId
     * @return
     * @throws TwitterException
     */
    public String replyToComment(String msg, long replyId, String username) throws TwitterException {
        LOG.info("Inside replyToComment method");
        String url = msg + "   https://twitter.com/" + username + "/status/" + replyId;
        Twitter twitter = twitterInstance;
        Status status = twitter.showStatus(replyId);
        Status reply = twitter.updateStatus(new StatusUpdate("@" + status.getUser().getScreenName()
                + " " + url).inReplyToStatusId(status.getId()));
        return Long.toString(reply.getId());
    }

    /**
     * Get information of current user
     *
     * @return
     * @throws TwitterException
     */
    public Status getStatus() throws TwitterException {
        LOG.info("get status of current user");
        Twitter twitter = twitterInstance;
        return twitter.showStatus(twitter.getId());
    }

    /**
     * Get status and information of given id user
     *
     * @param id
     * @return
     * @throws TwitterException
     */
    public Status getStatus(long id) throws TwitterException {
        LOG.info("get status of given id");
        Twitter twitter = twitterInstance;
        return twitter.showStatus(id);
    }

    /**
     * Show the user with the given id
     *
     * @param id
     * @throws TwitterException
     */
    public User showUser(long id) throws TwitterException {
        LOG.info("showUser");
        Twitter twitter = twitterInstance;
        return twitter.showUser(id);
    }

    /**
     * Get the timeline.This call uses count to set the number of tweets to
     * retrieve from the timeline and page to represent the page number.
     * Basically FX multiplies the page number by the count to figure out what
     * the next set of tweets to retrieve will be.
     *
     * @param page
     * @return
     * @throws TwitterException
     */
    public List<Status> getTimeLine(int page) throws TwitterException {
        LOG.debug("getTHomeimeLine");
        Twitter twitter = twitterInstance;
        Paging paging = new Paging();
        paging.setCount(20);
        paging.setPage(page);
        List<Status> statuses = twitter.getHomeTimeline(paging);
        return statuses;
    }

    /**
     * Get timeline from database to show the saved data on the twitter app
     *
     * @param page
     * @return
     * @throws TwitterException
     * @throws SQLException
     */
    public List<Status> getTimeLinedb(int page) throws TwitterException, SQLException {
        LOG.debug("getTimeLine from database");
        Twitter twitter = twitterInstance;
        TwitterDAOImpl data = new TwitterDAOImpl();
        List<TwitterInfoDB> listdb = data.findAll();
        long ids[] = new long[listdb.size()];
        listdb.forEach((datadb) -> {
            for (int i = 0; i < listdb.size(); i++) {
                ids[i] = listdb.get(i).getIduse();
            }
        });
        List<Status> statuses = twitter.lookup(ids);
        Paging paging = new Paging();
        paging.setCount(20);
        paging.setPage(page);
        return statuses;
    }

    /**
     * Get the timeline.This call uses count to set the number of tweets that
     * mention the owner to retrieve from the timeline and page to represent the
     * page number. Basically FX multiplies the page number by the count to
     * figure out what the next set of tweets to retrieve will be.
     *
     * @param page
     * @return
     * @throws TwitterException
     */
    public List<Status> getMentionsTimeLine(int page) throws TwitterException {
        LOG.debug("getMentions timeline");
        Twitter twitter = twitterInstance;
        Paging paging = new Paging();
        paging.setCount(20);
        paging.setPage(page);
        List<Status> statuses = twitter.getMentionsTimeline(paging);

        return statuses;
    }

    /**
     * This method searches with the given text
     *
     * @param searchText
     * @return
     */
    public List<Status> searchQuery(String searchText) {
        LOG.info("searchText in the twitter");
        List<Status> tweets = new ArrayList();
        Twitter twitter = twitterInstance;
        QueryResult result = null;
        try {
            Query query = new Query(searchText);
            //   query.setCount(50);         // just put number to get me back 50 result only for not having slow app
            result = twitter.search(query);
            tweets = result.getTweets();
            for (Status tweet : tweets) {
                LOG.info("@" + tweet.getUser().getScreenName() + " - " + tweet.getText());
            }

        } catch (TwitterException te) {
            LOG.info("Failed to search tweets: " + te.getMessage());
        }
        return tweets;
    }

    /**
     * It makes the likes for tweet with the given id
     *
     * @param id
     * @return
     * @throws TwitterException
     */
    public Status createFavorite(long id) throws TwitterException {
        LOG.info("make likes");
        Twitter twitter = twitterInstance;
        return twitter.createFavorite(id);
    }

    /**
     * It destroys the like
     *
     * @param id
     * @return
     * @throws TwitterException
     */
    public Status destroyFavorite(long id) throws TwitterException {
        LOG.info("Dislike");
        Twitter twitter = twitterInstance;
        return twitter.destroyFavorite(id);
    }

    /**
     * It shows is source followed by target uses this method to see who follows
     * the current user and user follows whom
     *
     * @param targetId
     * @return
     * @throws TwitterException
     */
    public boolean friendshipStatus(String targetId) throws TwitterException {
        LOG.info("inside friendship" + targetId);
        String sourceId = getOwnerId();
        Twitter twitter = twitterInstance;
        Relationship rr = twitter.friendsFollowers().showFriendship(sourceId, targetId);
        LOG.info("isSourceFollowedByTarget: " + rr.isTargetFollowedBySource());
        return twitter.friendsFollowers().showFriendship(sourceId, targetId).isTargetFollowedBySource();
    }

    /**
     * Get the ScreenName of the owner of the twitter
     *
     * @return
     * @throws TwitterException
     */
    private String getOwnerId() throws TwitterException {
        LOG.info("get id of current user");
        Twitter twitter = twitterInstance;
        LOG.info("" + twitter.getId());
        return twitter.getScreenName();
    }

    /**
     * Creats Friendship follow someone
     *
     * @param id
     * @return
     * @throws TwitterException
     */
    public User createFriendship(String id) throws TwitterException {
        LOG.info("inside createFriendship");
        Twitter twitter = twitterInstance;
        return twitter.createFriendship(id);
    }

    /**
     * Destroy Friendship unfollow some user with given id
     *
     * @param id
     * @return
     * @throws TwitterException
     */
    public User destroyFriendship(String id) throws TwitterException {
        LOG.info("inside destroy friendship");
        Twitter twitter = twitterInstance;
        return twitter.destroyFriendship(id);
    }

    /**
     * It shows status of the retweet
     *
     * @param id
     * @return
     * @throws TwitterException
     */
    public Status retweetStatus(long id) throws TwitterException {
        LOG.info("inside retweetstatus");
        Twitter twitter = twitterInstance;
        return twitter.retweetStatus(id);

    }

    /**
     * unretweet a tweet undo retweet
     *
     * @param id
     * @return
     * @throws TwitterException
     */
    public Status unRetweet(long id) throws TwitterException {
        LOG.info("undo the retweet ");
        Twitter twitter = twitterInstance;
        return twitter.unRetweetStatus(id);
    }

    /**
     * make embed of url that we are retweeting to show completely the source
     * message
     *
     * @param url
     * @param id
     * @return
     * @throws TwitterException
     */
    public OEmbedRequest makeEmbed(String url, long id) throws TwitterException {
        LOG.info("helper method to create an embed of original message for retweet");
        Twitter twitter = twitterInstance;
        OEmbedRequest oEmbedRequest = new OEmbedRequest(id, url);
        oEmbedRequest.setHideMedia(true);
        oEmbedRequest.setOmitScript(true);
        oEmbedRequest.setHideThread(true);
        oEmbedRequest.setHideTweet(true);
        return oEmbedRequest;
    }

    /**
     * This method helps to make a comment with embeded data I will use more
     * probably in the next phase just here to remember
     *
     * @param replyMessage
     * @param messageId
     * @throws TwitterException
     */
    public void helperMakeComment(String replyMessage, long messageId) throws TwitterException {
        LOG.info("helper method to make the comment");
        long inReplyToStatusId = messageId;
        Twitter twitter = twitterInstance;
        StatusUpdate statusUpdate = new StatusUpdate(replyMessage);
        statusUpdate.inReplyToStatusId(inReplyToStatusId);
        statusUpdate.setMediaIds(messageId);
    }
}

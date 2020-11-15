package com.shirin.tadawson.business;

import com.shirin.propertiesconfig.bean.TwitterInfo;
import com.shirin.propertiesconfig.bean.TwitterInterface;
import java.util.List;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.Status;
import twitter4j.TwitterException;

/**
 * Task of retrieving user's timeline
 *
 * @author Shirin oct 2019
 */
public class TwitterTimeLine {

    private final static Logger LOG = LoggerFactory.getLogger(TwitterTimeLine.class);

    private final ObservableList<TwitterInterface> list;

    private final TwitterEngine twitterEngine;

    private int page;

    /**
     * Non-default constructor initializes instance variables.
     *
     * @param list
     */
    public TwitterTimeLine(ObservableList<TwitterInterface> list) {
        twitterEngine = new TwitterEngine();
        this.list = list;
        page = 1;
    }

    /**
     * Add new Status objects to the ObservableList. Additions occur at the end
     * of the list.
     *
     * @throws Exception
     */
    public void fillTimeLine() throws Exception {
        LOG.info("homeline list");
        List<Status> homeline = twitterEngine.getTimeLine(page);
        homeline.forEach((status) -> {
            list.add(list.size(), new TwitterInfo(status));
        });
        page += 1;
    }

    /**
     * clear the timeline
     *
     * @throws Exception
     */
    public void clearTimeLine() throws Exception {
        LOG.info("clearTimeLine");

        this.list.clear();
    }

    /**
     * fill timeline with database saved data
     *
     * @throws Exception
     */
    public void fillTimeLinedb() throws Exception {
        LOG.info("fillTimeLinedb list");
        List<Status> homeline = twitterEngine.getTimeLinedb(page);
        homeline.forEach((status) -> {
            this.list.add(this.list.size(), new TwitterInfo(status));
        });
        page += 1;
    }

    /**
     * Add new Status objects to the ObservableList. Additions occur at the end
     * of the list.
     *
     * @throws Exception
     */
    public void fillMentionsTimeLine() throws Exception {
        LOG.info("inside mention timeline");
        List<Status> mentionline = twitterEngine.getMentionsTimeLine(page);
        mentionline.forEach((status) -> {
            list.add(list.size(), new TwitterInfo(status));
        });
        page += 1;
    }

    /**
     * Add new Status objects to the ObservableList. Additions occur at the end
     * of the list.
     *
     * @throws Exception
     */
    public void fillSearchTimeLine(String searchText) throws Exception {
        LOG.info("inside search timeline");
        List<Status> searchline = twitterEngine.searchQuery(searchText);
        searchline.forEach((status) -> {
            list.add(list.size(), new TwitterInfo(status));
        });
        page += 1;
    }

    /**
     * fill timeline of the profile for tweets of current user
     *
     * @throws TwitterException
     */
    public void fillTabTweetsTimeline() throws TwitterException {
        LOG.info("inside fillTabTweetsTimeline");
        List<Status> tweetsTimeline = twitterEngine.getTweetsTimeLine(page);
        tweetsTimeline.forEach((status) -> {
            list.add(list.size(), new TwitterInfo(status));
        });
        page += 1;
    }

    /**
     * fill timeline in profile page for current user tweets which are retweeted
     * with others
     *
     * @throws TwitterException
     */
    public void fillTabMyTweetsRetweeted() throws TwitterException {
        LOG.info("inside fillTabMyTweetsRetweeted");
        List<Status> tweetsTimeline = twitterEngine.getMyTweetsRetweetedTimeLine(page);
        tweetsTimeline.forEach((status) -> {
            list.add(list.size(), new TwitterInfo(status));
        });
        page += 1;
    }

    /**
     * fill timeline in the profile page for tweets which current user retweeted
     *
     * @throws TwitterException
     */
    public void fillTabRetweetsByMeTimeline() throws TwitterException {
        LOG.info("inside fillTabRetweetsByMeTimeline");
        List<Status> tweetsTimeline = twitterEngine.getRetweetsByMeTimeLine(page);
        tweetsTimeline.forEach((status) -> {
            list.add(list.size(), new TwitterInfo(status));
        });
        page += 1;
    }

    /**
     * fill list of friends of the current user for sending direct message
     *
     * @throws TwitterException
     */
    public void fillFollowersTimeLine() throws TwitterException {
        LOG.info("inside fillFollowersTimeLine");
        List<Status> tweetsTimeline = twitterEngine.getFollowers();
        tweetsTimeline.forEach((status) -> {
            list.add(list.size(), new TwitterInfo(status));
        });
        // page += 1;
    }
}

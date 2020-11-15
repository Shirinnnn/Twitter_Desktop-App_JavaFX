package com.shirin.tadawson.business;

import java.util.List;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.DirectMessageList;
import twitter4j.Status;
import twitter4j.User;

/**
 *
 * @author Shirin Eskandari
 */
public class TwitterTimeLineMessage {

    private final static Logger LOG = LoggerFactory.getLogger(TwitterTimeLineMessage.class);
    private ObservableList<TwitterInfoMessage> listMessage;
    private final TwitterEngine twitterEngine;
    private Status status;
    private int page;

    public TwitterTimeLineMessage(ObservableList<TwitterInfoMessage> listMessage) {
        twitterEngine = new TwitterEngine();
        this.listMessage = listMessage;
        page = 1;
    }

    public void fillMessagesTimeLine() throws Exception {
        DirectMessageList messagesLine = twitterEngine.getDirectMessages(50);
        List<User> userss = twitterEngine.usersDirectMessage(messagesLine);

        messagesLine.forEach((message) -> {

            listMessage.add(listMessage.size(), new TwitterInfoMessage(message));

        });
        page += 1;
    }

}




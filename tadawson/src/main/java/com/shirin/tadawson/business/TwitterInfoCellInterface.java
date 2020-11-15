package com.shirin.tadawson.business;

import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import twitter4j.TwitterException;

/**
 * Interface for TwitterInfoCell class and info db
 *
 * @author Shirin oct 2019
 */
public interface TwitterInfoCellInterface {

    public void setActionComment(Button comment) throws TwitterException;

    public void setActionRetweetWithMessage(MenuItem menuItem2) throws TwitterException;

    public void setActionRetweet(MenuItem menuItem1);

    public void setActionFollow(Button follow) throws TwitterException;

    public void setActionFavorite(Button countFavorite);
}

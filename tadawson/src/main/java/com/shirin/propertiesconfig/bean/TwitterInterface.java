package com.shirin.propertiesconfig.bean;

import java.util.Date;
import javafx.scene.Node;
import twitter4j.Status;

/**
 * Interface for TwitterInfo and TwitterInfoDb to use mutual classes for live
 * data and database offline data
 *
 * @author Shirin Eskandari
 */
public interface TwitterInterface {

    public void setGraphic(Node arg0);

    public String getText();

    public String getName();

    public String getScreenName();

    public boolean isFollow();

    public Date CreatedAt();

    public String getURL();

    public String getHandle();

    public int getFavoriteCount();

    public int getRetweetCount();

    public boolean isFavorited();

    public Status getRetweetedStatus();

    public long getId();

    public boolean isRetweetedByMe();

    public String geturl();

    public long getQuotedId();
     public String getDescription() ;
     public int  getFollowersCount() ;
     public int  getFriendsCount();

}

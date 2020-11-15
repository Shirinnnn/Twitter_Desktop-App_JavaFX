/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.shirin.tadawson.business;

import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.DirectMessage;
import twitter4j.URLEntity;

/**
 *
 * @author eskan
 */
public class TwitterInfoMessage {
      private final static Logger LOG = LoggerFactory.getLogger(TwitterInfoMessage.class);

    private final DirectMessage message;


    public TwitterInfoMessage(DirectMessage message) {
     
        this.message = message;
    }

    public long getMessageSenderId() {
        return this.message.getSenderId();
    }

    public String getMessage() {
        return this.message.getText();
    }

    public Date getMessageCreateDate() {
        return this.message.getCreatedAt();
    }

    public long getMessageId() {
        return this.message.getId();
    }

    public long getMessageRecipeintId() {
        return this.message.getRecipientId();
    }
      public String geturl() {
        String url = "";
        String url2 = "";
          String url3 = "";
        URLEntity[] urls = message.getMediaEntities();
        for (URLEntity urlEntity : urls) {
            url = urlEntity.getURL();
            url2=urlEntity.getExpandedURL();
           url3 = urlEntity.getDisplayURL();
            
            LOG.info("Website link URL in the tweet: " + url);
            LOG.info("EXPAND link URL in the tweet: " + url2);
            LOG.info("DIPLAYtweet: " + url3);
        }
        return url;
    }
}

/**
 * Sample Skeleton for 'NotificationFXML.fxml' Controller Class
 */
package com.shirin.tadawson.controller;

import com.shirin.propertiesconfig.bean.TwitterInfo;
import com.shirin.propertiesconfig.bean.TwitterInterface;
import com.shirin.tadawson.business.TwitterInfoCell;
import com.shirin.tadawson.business.TwitterTimeLine;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This controller makes notification list view from tweets that mention the
 * owner of the twitter
 *
 * @author Shirin OCT 2019
 */
public class NotificationFXMLController {

    private final static Logger LOG = LoggerFactory.getLogger(NotificationFXMLController.class);
    private TwitterTimeLine timeline;
    private TwitterInfo twitterInfo;
    private TwitterInfoCell infoCell;
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="notificationBoard"
    private BorderPane notificationBoard; // Value injected by FXMLLoader

    @FXML // fx:id="notificationListView"
    private ListView<TwitterInterface> notificationListView; // Value injected by FXMLLoader

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert notificationBoard != null : "fx:id=\"notificationBoard\" was not injected: check your FXML file 'NotificationFXML.fxml'.";
        assert notificationListView != null : "fx:id=\"notificationListView\" was not injected: check your FXML file 'NotificationFXML.fxml'.";
        setUpTimeline();
        createFirstTimeline();
    }

    /**
     * prepare all list to show up on notification list pane
     */
    private void setUpTimeline() {
        LOG.info("inside setup method");
        ObservableList<TwitterInterface> list = FXCollections.observableArrayList();
        notificationListView.setItems(list);
        notificationListView.setCellFactory(p -> new TwitterInfoCell());
        notificationListView.getItems().addAll(list);
        notificationListView.getSelectionModel().selectedItemProperty()
                .addListener((ObservableValue<? extends TwitterInterface> ov, TwitterInterface t, TwitterInterface t1) -> {
                    //   LOG.debug("isfollower: " + t1.isFollow());
                    if (t != null) {
                        LOG.debug("Previous handle: " + t.getHandle());
                    }
                    if (t1 != null) {
                        LOG.debug("New handle: " + t1.getHandle());
                    }
                });
    }

    /**
     * This method uses TwitterTimeLine class to fill the notification list view
     */
    private void createFirstTimeline() {
        LOG.info("inside fill timeline method");
        if (timeline == null) {
            timeline = new TwitterTimeLine(notificationListView.getItems());
        }
        try {
            timeline.fillMentionsTimeLine();
        } catch (Exception ex) {
            LOG.error("Unable to display timeline", ex);
        }
    }
}

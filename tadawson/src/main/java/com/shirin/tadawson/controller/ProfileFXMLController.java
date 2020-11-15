package com.shirin.tadawson.controller;

import com.shirin.propertiesconfig.bean.TwitterInfo;
import com.shirin.propertiesconfig.bean.TwitterInterface;
import com.shirin.tadawson.business.TwitterEngine;
import com.shirin.tadawson.business.TwitterInfoCell;
import com.shirin.tadawson.business.TwitterTimeLine;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProfileFXMLController {

    private final static Logger LOG = LoggerFactory.getLogger(ProfileFXMLController.class);
    private TwitterTimeLine timeline;
    private TwitterEngine twitterEngine = new TwitterEngine();
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="profileBoard"
    private BorderPane profileBoard; // Value injected by FXMLLoader

    @FXML // fx:id="tweettimeline"
    private ListView<TwitterInterface> tweettimeline; // Value injected by FXMLLoader

    @FXML // fx:id="showTweet"
    private Button showTweet; // Value injected by FXMLLoader

    @FXML // fx:id="mytweetRetweeted"
    private Button mytweetRetweeted; // Value injected by FXMLLoader
    @FXML // fx:id="retweetsByMe"
    private Button retweetsByMe; // Value injected by FXMLLoader
    @FXML // fx:id="profilehbox"
    private HBox profilehbox; // Value injected by FXMLLoader

    @FXML
    void RetweetsByMe(ActionEvent event) throws Exception {
        createThirdTimeline();
    }

    @FXML
    void myTweetRetweeted(ActionEvent event) throws Exception {
        createSecondTimeline();
//TwitterInterface twitter = new TwitterInfo(twitterEngine.getStatus());

    }

    @FXML
    void showTweetClick(ActionEvent event) throws Exception {

        createFirstTimeline();
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() throws Exception {
        assert profileBoard != null : "fx:id=\"profileBoard\" was not injected: check your FXML file 'ProfileFXML.fxml'.";
        assert tweettimeline != null : "fx:id=\"tweettimeline\" was not injected: check your FXML file 'ProfileFXML.fxml'.";
        assert showTweet != null : "fx:id=\"showTweet\" was not injected: check your FXML file 'ProfileFXML.fxml'.";
        assert mytweetRetweeted != null : "fx:id=\"mytweetRetweeted\" was not injected: check your FXML file 'ProfileFXML.fxml'.";
        assert retweetsByMe != null : "fx:id=\"retweetsByMe\" was not injected: check your FXML file 'ProfileFXML.fxml'.";
        assert profilehbox != null : "fx:id=\"profilehbox\" was not injected: check your FXML file 'ProfileFXML.fxml'.";

        setUpTimeline();
        createFirstTimeline();
    }

    /**
     * prepare all list to show up on notification list pane
     */
    private void setUpTimeline() {
        LOG.info("inside setup method");
        ObservableList<TwitterInterface> list = FXCollections.observableArrayList();
        tweettimeline.setItems(list);
        tweettimeline.setCellFactory(p -> new TwitterInfoCell());
        tweettimeline.getItems().addAll(list);
        tweettimeline.getSelectionModel().selectedItemProperty()
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
    private void createFirstTimeline() throws Exception {
        LOG.info("inside fill timeline method");
        //timeline.clearTimeLine();
        if (timeline != null) {
            timeline.clearTimeLine();
        }
        if (timeline == null) {
            timeline = new TwitterTimeLine(tweettimeline.getItems());
        }
        try {
            timeline.fillTabTweetsTimeline();
        } catch (Exception ex) {
            LOG.error("Unable to display timeline", ex);
        }
    }

    private void createSecondTimeline() throws Exception {
        LOG.info("inside fill timeline method");
        //timeline.clearTimeLine();
        if (timeline != null) {
            timeline.clearTimeLine();
        }
        if (timeline == null) {
            timeline = new TwitterTimeLine(tweettimeline.getItems());
        }
        try {
            timeline.fillTabMyTweetsRetweeted();
        } catch (Exception ex) {
            LOG.error("Unable to display timeline", ex);
        }

    }

    private void createThirdTimeline() throws Exception {
        LOG.info("inside fill timeline method");
        //timeline.clearTimeLine();
        if (timeline != null) {
            timeline.clearTimeLine();
        }
        if (timeline == null) {
            timeline = new TwitterTimeLine(tweettimeline.getItems());
        }
        try {
            timeline.fillTabRetweetsByMeTimeline();
        } catch (Exception ex) {
            LOG.error("Unable to display timeline", ex);
        }

    }

}

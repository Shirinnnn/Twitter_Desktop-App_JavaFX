package com.shirin.tadawson.controller;

import com.shirin.tadawson.business.TwitterEngine;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.TwitterException;

/**
 * This class load the page for tweet
 *
 * @author Shirin Eskandari
 */
public class SendTweetFXMLController {

    private final static Logger LOG = LoggerFactory.getLogger(SendTweetFXMLController.class);
    private AlertMessages alert = new AlertMessages();
    private final TwitterEngine twitterEngine = new TwitterEngine();
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;
    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;
    @FXML // fx:id="sendTweetBoard"
    private BorderPane sendTweetBoard; // Value injected by FXMLLoader
    @FXML // fx:id="textAreaTweet"
    private TextArea textAreaTweet; // Value injected by FXMLLoader
    @FXML // fx:id="hboxTweet"
    private HBox hboxTweet; // Value injected by FXMLLoader
    @FXML // fx:id="sendTweetBtn"
    private Button sendTweetBtn; // Value injected by FXMLLoader
    @FXML // fx:id="exitTweet"
    private Button exitTweet; // Value injected by FXMLLoader

    @FXML
    void exitTweetAction(ActionEvent event) {
        LOG.info("inside exitTweetAction method");
        Platform.exit();
    }

    @FXML
    /**
     * This methods Send a tweet Message limit characters is 280 character
     */
    void sendTweetAction(ActionEvent event) {
        LOG.info("inside send tweet method");
        try {
            twitterEngine.createTweet(textAreaTweet.getText(), null);
            alert.infoAllert("Successfully Tweeted");
        } catch (TwitterException ex) {
            LOG.error("Unable to send tweet", ex);
            alert.errorAlert("Unable to send tweet");
        }
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert sendTweetBoard != null : "fx:id=\"sendTweetBoard\" was not injected: check your FXML file 'SendTweetFXML.fxml'.";
        assert textAreaTweet != null : "fx:id=\"textAreaTweet\" was not injected: check your FXML file 'SendTweetFXML.fxml'.";
        assert hboxTweet != null : "fx:id=\"hboxTweet\" was not injected: check your FXML file 'SendTweetFXML.fxml'.";
        assert sendTweetBtn != null : "fx:id=\"sendTweetBtn\" was not injected: check your FXML file 'SendTweetFXML.fxml'.";
        assert exitTweet != null : "fx:id=\"exitTweet\" was not injected: check your FXML file 'SendTweetFXML.fxml'.";
       textAreaTweet.setWrapText(true);
        alert.validateText(textAreaTweet, 280);
    }
}

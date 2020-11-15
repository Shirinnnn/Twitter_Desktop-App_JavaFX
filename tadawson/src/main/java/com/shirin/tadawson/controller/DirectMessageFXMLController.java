/**
 * Sample Skeleton for 'DirectMessageFXML.fxml' Controller Class
 */
package com.shirin.tadawson.controller;

import com.shirin.propertiesconfig.bean.TwitterInfo;
import com.shirin.tadawson.business.TwitterEngine;
import com.shirin.tadawson.business.TwitterInfoCellMessage;
import com.shirin.tadawson.business.TwitterInfoMessage;
import com.shirin.tadawson.business.TwitterTimeLine;
import com.shirin.tadawson.business.TwitterTimeLineMessage;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.Status;
import twitter4j.TwitterException;

/**
 * This class is for display the Direct Message page
 *
 * @author Shirin Eskandari 10-2019
 */
public class DirectMessageFXMLController {

    private final static Logger LOG = LoggerFactory.getLogger(DirectMessageFXMLController.class);
    private final TwitterEngine twitterEngine = new TwitterEngine();
    private AlertMessages alerts = new AlertMessages();
    private TwitterTimeLineMessage messagesTimeline;
    private TwitterTimeLine timeline;
    private ObservableList<String> myComboBoxData;
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="directMessageBorderPane"
    private BorderPane directMessageBorderPane; // Value injected by FXMLLoader

    @FXML // fx:id="textAreaDirectMsg"
    private TextArea textAreaDirectMsg; // Value injected by FXMLLoader

    @FXML // fx:id="downHboxDirectMsg"
    private HBox downHboxDirectMsg; // Value injected by FXMLLoader

    @FXML // fx:id="sendDirectMsg"
    private Button sendDirectMsg; // Value injected by FXMLLoader

    @FXML // fx:id="exitDirect"
    private Button exitDirect; // Value injected by FXMLLoader

    @FXML // fx:id="topHbox"
    private HBox topHbox; // Value injected by FXMLLoader

    @FXML // fx:id="labelRecipient"
    private Label labelRecipient; // Value injected by FXMLLoader

    @FXML // fx:id="textFieldDirectMsg"
    private TextField textFieldDirectMsg; // Value injected by FXMLLoader
    @FXML // fx:id="downAnchor"
    private AnchorPane downAnchor; // Value injected by FXMLLoader
    @FXML // fx:id="messagesListView"
    private ListView<TwitterInfoMessage> messagesListView; // Value injected by FXMLLoader

    @FXML // fx:id="topAnchor"
    private AnchorPane topAnchor; // Value injected by FXMLLoader

    @FXML
    void exitDirectBtn(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    /**
     * This method uses twitterEngine class to send a direct message to special
     * person Message limit character is 140 characters
     */
    void sendDirectBtn(ActionEvent event) {
        LOG.info("Inside send direct message");
        try {
            twitterEngine.sendDirectMessage(textFieldDirectMsg.getText(), textAreaDirectMsg.getText());
            alerts.infoAllert("message sent");
        } catch (TwitterException ex) {
            LOG.error("Unable to send tweet", ex);
            alerts.errorAlert("Sorry There is some technical issues, try later");
        }
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() throws TwitterException {
        assert directMessageBorderPane != null : "fx:id=\"directMessageBorderPane\" was not injected: check your FXML file 'DirectMessageFXML.fxml'.";
        assert textAreaDirectMsg != null : "fx:id=\"textAreaDirectMsg\" was not injected: check your FXML file 'DirectMessageFXML.fxml'.";
        assert downHboxDirectMsg != null : "fx:id=\"downHboxDirectMsg\" was not injected: check your FXML file 'DirectMessageFXML.fxml'.";
        assert sendDirectMsg != null : "fx:id=\"sendDirectMsg\" was not injected: check your FXML file 'DirectMessageFXML.fxml'.";
        assert exitDirect != null : "fx:id=\"exitDirect\" was not injected: check your FXML file 'DirectMessageFXML.fxml'.";
        assert topHbox != null : "fx:id=\"topHbox\" was not injected: check your FXML file 'DirectMessageFXML.fxml'.";
        assert labelRecipient != null : "fx:id=\"labelRecipient\" was not injected: check your FXML file 'DirectMessageFXML.fxml'.";
        assert textFieldDirectMsg != null : "fx:id=\"textFieldDirectMsg\" was not injected: check your FXML file 'DirectMessageFXML.fxml'.";
        assert messagesListView != null : "fx:id=\"messagesListView\" was not injected: check your FXML file 'DirectMessageFXML.fxml'.";
        assert topAnchor != null : "fx:id=\"topAnchor\" was not injected: check your FXML file 'DirectMessageFXML.fxml'.";
        assert downAnchor != null : "fx:id=\"downAnchor\" was not injected: check your FXML file 'DirectMessageFXML.fxml'.";
        textAreaDirectMsg.setWrapText(true);
        List<Status> users2 = twitterEngine.getFollowers();

        alerts.validateText(textAreaDirectMsg, 140);
        setUpTimeline();

        createFirstTimeline();
    }

    private void setUpTimeline() {
        ObservableList<TwitterInfoMessage> list = FXCollections.observableArrayList();
        messagesListView.setItems(list);
        messagesListView.setCellFactory(p -> new TwitterInfoCellMessage());
        messagesListView.getItems().addAll(list);
        messagesListView.getSelectionModel().selectedItemProperty()
                .addListener((ObservableValue<? extends TwitterInfoMessage> ov, TwitterInfoMessage t, TwitterInfoMessage t1) -> {
                    //   LOG.debug("isfollower: " + t1.isFollow());
                    if (t != null) {
                        textAreaDirectMsg.setText(t1.getMessage());
                        //      LOG.debug("Previous handle: " + t.getHandle());
                    }
                    if (t1 != null) {
                        //     LOG.debug("New handle: " + t.getHandle());
                    }
                });
    }

    private void createFirstTimeline() {
        if (messagesTimeline == null) {
            messagesTimeline = new TwitterTimeLineMessage(messagesListView.getItems());

        }
        try {
            messagesTimeline.fillMessagesTimeLine();
        } catch (Exception ex) {

        }
    }

}

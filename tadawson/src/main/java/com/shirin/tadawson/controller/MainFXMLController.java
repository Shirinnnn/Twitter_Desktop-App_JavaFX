/**
 * Sample Skeleton for 'MainFXML.fxml' Controller Class
 */
package com.shirin.tadawson.controller;

import com.shirin.propertiesconfig.bean.PropertiesTwitter;
import com.shirin.tadawson.MainApp;
import com.shirin.tadawson.propertymanager.PropertyTwitterManager;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is the main board of the program and other scenes will load on the
 * center of this BorderPane. click on each button on the main board, initialize
 * a scene related to that button on center of the main board
 *
 * @author Shirin Eskandari 10-2019
 */
public class MainFXMLController {

    private final static Logger LOG = LoggerFactory.getLogger(MainFXMLController.class);
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;
    @FXML // fx:id="explore"
    private Button explore; // Value injected by FXMLLoader
    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="bord"
    private BorderPane rootBoard; // Value injected by FXMLLoader

    @FXML // fx:id="home"
    private Button home; // Value injected by FXMLLoader

    @FXML
    private Button sendTweetBtn; // Value injected by FXMLLoader

    @FXML // fx:id="notification"
    private Button notification; // Value injected by FXMLLoader

    @FXML // fx:id="messages"
    private Button messages; // Value injected by FXMLLoader

    @FXML // fx:id="profile"
    private Button profile; // Value injected by FXMLLoader
    @FXML
    private BorderPane messageBoard;
    @FXML
    private BorderPane profileBoard;
    @FXML
    private BorderPane sendTweetBoard;
    @FXML
    private BorderPane homeTimeLineBoard;
    @FXML
    private BorderPane exploreBoard;
    @FXML
    private BorderPane textlogBoard;
    @FXML
    private BorderPane notificationBoard;

    @FXML
    private FXMLLoader notificationLoader;
    @FXML
    private FXMLLoader textlogLoader;
    @FXML
    private FXMLLoader messageLoader;
    private TextArea textArea;
    @FXML
    private FXMLLoader profileLoader;
    @FXML
    private FXMLLoader sendTweetLoader;
    @FXML
    private FXMLLoader homeTimeLineLoader;
    @FXML
    private FXMLLoader exploreLoader;
    

    /**
     * It loads home time line on the center of the main board
     *
     * @param event
     */
    @FXML
    void goHome(ActionEvent event) {
        LOG.info("Home Button clicked");
        rootBoard.setCenter(homeTimeLineBoard);
    }

    /**
     * It loads Explore page for search in the tweitter, on the center of the
     * main board
     *
     * @param event
     */
    @FXML
    void goExplore(ActionEvent event) {
        LOG.info("Explore Button Clicked");
        rootBoard.setCenter(exploreBoard);
    }

   
    /**
     * It loads direct message page in the center of the main board
     *
     * @param event
     */
    @FXML
    void goMessages(ActionEvent event) {
        LOG.info("Messages Button Clicked");
        rootBoard.setCenter(messageBoard);
    }

    /**
     * It load notification page on the center of the main board
     *
     * @param event
     */
    @FXML
    void goNotification(ActionEvent event) {
        LOG.info("Notifications Button Clicked");
        rootBoard.setCenter(notificationBoard);
    }

    /**
     * It loads profile page on the center of the main board
     *
     * @param event
     */
    @FXML
    void goProfile(ActionEvent event) {
        LOG.info("Profile Button Clicked");
        rootBoard.setCenter(profileBoard);
    }

    /**
     * It loads tweet page on the center of the main board
     *
     * @param event
     */
    @FXML
    void sendTweetBtnAction(ActionEvent event) {
        LOG.info("sendTweet Button Clicked");
        rootBoard.setCenter(sendTweetBoard);
    }

    /**
     * This method gives some information about app
     *
     * @param event
     */
    @FXML
    void handleAbout(ActionEvent event) {
        LOG.info("Inside About handle");
        Alert dialog = new Alert(Alert.AlertType.INFORMATION);
        dialog.setTitle("Tadawson");
        dialog.setHeaderText("About");
        dialog.setContentText("This is a mini twitter app");
        dialog.show();
    }

    /**
     * This method closes the scene
     */
    @FXML
    private void handleClose() {
        LOG.info("inside Close Handle");
        Platform.exit();
    }

    /**
     * It checks if the properties file exist and has been loaded then it shows
     * home page of the twitter
     *
     * @throws IOException
     */
    public void displayPropertiesInTextArea() throws IOException {
        LOG.info("Inside displayPropertiesInTextArea");
        PropertyTwitterManager pm = new PropertyTwitterManager();
        PropertiesTwitter mcp = new PropertiesTwitter();

        try {
            if (pm.loadTextProperties(mcp, "", "twitter4j")) {
                rootBoard.setCenter(homeTimeLineBoard);
            }
        } catch (IOException ex) {
            LOG.error("displayPropertiesInTextArea error", ex);
            errorAlert("displayPropertiesInTextArea()");
            Platform.exit();
        }
    }

    /**
     * This method is called by the FXMLLoader when initialization is complete
     * This method load and initialize all other scene connected to this main
     * board
     */
    @FXML
    void initialize() {
        assert rootBoard != null : "fx:id=\"bord\" was not injected: check your FXML file 'MainFXML.fxml'.";
        assert home != null : "fx:id=\"home\" was not injected: check your FXML file 'MainFXML.fxml'.";
        assert explore != null : "fx:id=\"explore\" was not injected: check your FXML file 'MainFXML.fxml'.";
        assert notification != null : "fx:id=\"notification\" was not injected: check your FXML file 'MainFXML.fxml'.";
        assert messages != null : "fx:id=\"messages\" was not injected: check your FXML file 'MainFXML.fxml'.";
        assert profile != null : "fx:id=\"profile\" was not injected: check your FXML file 'MainFXML.fxml'.";
        assert sendTweetBtn != null : "fx:id=\"sendTweetBtn\" was not injected: check your FXML file 'MainFXML.fxml'.";
       

        messageLoader = new FXMLLoader();
        messageLoader.setLocation(MainApp.class
                .getResource("/fxml/DirectMessageFXML.fxml"));
        try {
            messageBoard = messageLoader.load();
        } catch (IOException e) {
            LOG.error("Load Direct Message page  error", e);
            errorAlert("Load Direct Message page  erro");
            Platform.exit();
        }

        //Load for Profile Page
        profileLoader = new FXMLLoader();
        profileLoader.setLocation(MainApp.class
                .getResource("/fxml/profileFXML.fxml"));
        try {
            profileBoard = profileLoader.load();
        } catch (IOException e) {
            LOG.error("Load profile page  error", e);
            errorAlert("Load profile page  erro");
            Platform.exit();
        }
        //Load for Notification Page
        notificationLoader = new FXMLLoader();
        notificationLoader.setLocation(MainApp.class
                .getResource("/fxml/NotificationFXML.fxml"));
        try {
            notificationBoard = notificationLoader.load();
        } catch (IOException e) {
            LOG.error("Load NotificationFXML  error", e);
            errorAlert("Load NotificationFXML  error");
            Platform.exit();
        }

        //load for sendTweet Board
        sendTweetLoader = new FXMLLoader();
        sendTweetLoader.setLocation(MainApp.class
                .getResource("/fxml/SendTweetFXML.fxml"));
        try {
            sendTweetBoard = sendTweetLoader.load();
        } catch (IOException e) {
            LOG.error("Load  sendTweet page  error", e);
            errorAlert("Load  sendTweet page  erro");
            Platform.exit();

        }
        //load for homeTimeLine Board
        homeTimeLineLoader = new FXMLLoader();
        homeTimeLineLoader.setLocation(MainApp.class
                .getResource("/fxml/HomeTimeLineFXML.fxml"));
        try {
            homeTimeLineBoard = homeTimeLineLoader.load();
        } catch (IOException e) {
            LOG.error("Load  homeTimeLine page  error", e);
            errorAlert("Load  homeTimeLine page  error");
            Platform.exit();
        }
        //load for ExploreBoard
        exploreLoader = new FXMLLoader();
        exploreLoader.setLocation(MainApp.class
                .getResource("/fxml/ExploreFXML.fxml"));
        try {
            exploreBoard = exploreLoader.load();
        } catch (IOException e) {
            LOG.error("Load  explore page  error", e);
            errorAlert("Load  explore page  erro");
            Platform.exit();
        }
    }

    /**
     * Error message popup dialog
     *
     * @param msg
     */
    private void errorAlert(String msg) {
        LOG.info("Inside error alert");
        Alert dialog = new Alert(Alert.AlertType.ERROR);
        dialog.setTitle(resources.getString("Error"));
        dialog.setHeaderText(resources.getString("Error"));
        dialog.setContentText(resources.getString(msg));
        dialog.show();
    }
}

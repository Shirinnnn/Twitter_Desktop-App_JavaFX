package com.shirin.tadawson.controller;

import com.shirin.propertiesconfig.bean.TwitterInfo;
import com.shirin.propertiesconfig.bean.TwitterInterface;
import com.shirin.tadawson.business.TwitterEngine;
import com.shirin.tadawson.business.TwitterInfoCell;
import com.shirin.tadawson.business.TwitterTimeLine;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HomeTimeLineFXMLController {

    private final static Logger LOG = LoggerFactory.getLogger(HomeTimeLineFXMLController.class);
    private TwitterInterface twitterInterface;
    private TwitterTimeLine timeline;
    private TwitterInfo twitterInfo;
    private TwitterInfoCell infoCell;
    private final TwitterEngine twitterEngine = new TwitterEngine();
    private AlertMessages alerts = new AlertMessages();
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;
    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;
    @FXML
    private HBox hbox2;
    @FXML // fx:id="listviewBorderPane"
    private BorderPane listviewBorderPane; // Value injected by FXMLLoader
    @FXML // fx:id="homeLabel"
    private Label homeLabel; // Value injected by FXMLLoader
    @FXML
    private Button next20;
    @FXML // fx:id="live"
    private Button live; // Value injected by FXMLLoader
    @FXML // fx:id="sql"
    private Button sql; // Value injected by FXMLLoader
    @FXML // fx:id="listviewHome"
    private ListView<TwitterInterface> listviewHome; // Value injected by FXMLLoader

    @FXML
    /**
     * This methods brings another 20 recent tweets on the list view
     */
    void showNextTwenty(ActionEvent event) throws SQLException, Exception {
        LOG.info("show next 20 button clicked");
        if (timeline == null) {
            timeline = new TwitterTimeLine(listviewHome.getItems());
        }
        try {
            timeline.fillTimeLine();
        } catch (Exception ex) {
            LOG.error("Unable to display timeline", ex);
        }
    }

    /**
     * This method uses Online live data from the twitter
     *
     * @param event
     * @throws Exception
     */
    @FXML
    void liveHomeline(ActionEvent event) throws Exception {
        LOG.info("liveHomeline");
        createFirstTimeline();
    }

    /**
     * This method uses Database offline data for timeline
     *
     * @param event
     * @throws Exception
     */
    @FXML
    void sqlTimeline(ActionEvent event) throws Exception {
        LOG.info("sqlHomeline");
        if (timeline != null) {
            timeline.clearTimeLine();
        }
        if (timeline == null) {
            timeline = new TwitterTimeLine(listviewHome.getItems());
        }
        try {
            timeline.fillTimeLinedb();
        } catch (Exception ex) {
            LOG.error("Unable to display timeline", ex);
        }

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() throws Exception {
        assert listviewBorderPane != null : "fx:id=\"listviewBorderPane\" was not injected: check your FXML file 'HomeTimeLineFXML.fxml'.";
        assert homeLabel != null : "fx:id=\"homeLabel\" was not injected: check your FXML file 'HomeTimeLineFXML.fxml'.";
        assert listviewHome != null : "fx:id=\"listviewHome\" was not injected: check your FXML file 'HomeTimeLineFXML.fxml'.";
        assert hbox2 != null : "fx:id=\"hbox2\" was not injected: check your FXML file 'HomeTimeLineFXML.fxml'.";
        assert next20 != null : "fx:id=\"next20\" was not injected: check your FXML file 'HomeTimeLineFXML.fxml'.";
        assert live != null : "fx:id=\"live\" was not injected: check your FXML file 'HomeTimeLineFXML.fxml'.";
        assert sql != null : "fx:id=\"sql\" was not injected: check your FXML file 'HomeTimeLineFXML.fxml'.";
        setUpTimeline();
        createFirstTimeline();

    }

    /**
     * This method adds event listener to each cell of list view
     */
    private void setUpTimeline() throws Exception {
        LOG.info("inside setup method");
        ObservableList<TwitterInterface> list = FXCollections.observableArrayList();
        listviewHome.setItems(list);
        listviewHome.setCellFactory(p -> new TwitterInfoCell());
        listviewHome.getItems().addAll(list);
        listviewHome.getSelectionModel().selectedItemProperty()
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
     * This method filled the list view with help of TwitterTimeLine class
     */
    private void createFirstTimeline() throws Exception {
        LOG.info("inside first timeline method");
        if (timeline != null) {
            timeline.clearTimeLine();
        }
        if (timeline == null) {
            timeline = new TwitterTimeLine(listviewHome.getItems());
        }
        try {
            timeline.fillTimeLine();
        } catch (Exception ex) {
            LOG.error("Unable to display timeline", ex);
        }
    }

}

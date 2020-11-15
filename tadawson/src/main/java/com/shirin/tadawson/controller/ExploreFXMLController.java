
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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This controller has listview to show searched Items
 *
 * @author Shirin Oct 2019
 */
public class ExploreFXMLController {

    private final static Logger LOG = LoggerFactory.getLogger(ExploreFXMLController.class);
    private TwitterTimeLine searchtimeline;
    private TwitterInfo twitterInfo;
    private TwitterInfoCell infoCell;
    private String dataSearch;
    private final TwitterEngine twitterEngine = new TwitterEngine();
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="exploreBoard"
    private BorderPane exploreBoard; // Value injected by FXMLLoader

    @FXML // fx:id="exploreListView"
    private ListView<TwitterInterface> exploreListView; // Value injected by FXMLLoader

    @FXML // fx:id="topAnchoreExplore"
    private AnchorPane topAnchoreExplore; // Value injected by FXMLLoader

    @FXML // fx:id="searchButton"
    private Button searchButton; // Value injected by FXMLLoader

    @FXML // fx:id="searchTextField"
    private TextField searchTextField; // Value injected by FXMLLoader

    @FXML
    /**
     * This Action make the app search in tweets
     */
    void goSearch(ActionEvent event) {
        LOG.info("search button clicked");
        dataSearch = searchTextField.getText();

        dataSearch = searchTextField.getText();
        setUpTimeline();
        createFirstTimeline();

    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert exploreBoard != null : "fx:id=\"exploreBoard\" was not injected: check your FXML file 'ExploreFXML.fxml'.";
        assert exploreListView != null : "fx:id=\"exploreListView\" was not injected: check your FXML file 'ExploreFXML.fxml'.";
        assert topAnchoreExplore != null : "fx:id=\"topAnchoreExplore\" was not injected: check your FXML file 'ExploreFXML.fxml'.";
        assert searchButton != null : "fx:id=\"searchButton\" was not injected: check your FXML file 'ExploreFXML.fxml'.";
        assert searchTextField != null : "fx:id=\"searchTextField\" was not injected: check your FXML file 'ExploreFXML.fxml'.";
    }

    /**
     * This method makes event listener to each cell of the list view
     */
    private void setUpTimeline() {
        LOG.info("inside setup method");
        ObservableList<TwitterInterface> list = FXCollections.observableArrayList();
        exploreListView.setItems(list);
        exploreListView.setCellFactory(p -> new TwitterInfoCell());
        exploreListView.getItems().addAll(list);
        exploreListView.getSelectionModel().selectedItemProperty()
                .addListener((ObservableValue<? extends TwitterInterface> ov, TwitterInterface t, TwitterInterface t1) -> {
                    LOG.debug("isfollower: " + t1.isFollow());
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
    private void createFirstTimeline() {
        LOG.info("inside first timeline method");
        if (searchtimeline == null) {
            searchtimeline = new TwitterTimeLine(exploreListView.getItems());
        }
        try {
            searchtimeline = new TwitterTimeLine(exploreListView.getItems());
            searchtimeline.fillSearchTimeLine(dataSearch);
        } catch (Exception ex) {
        }
    }

}

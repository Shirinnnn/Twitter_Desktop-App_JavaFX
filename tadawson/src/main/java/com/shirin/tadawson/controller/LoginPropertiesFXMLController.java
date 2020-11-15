
package com.shirin.tadawson.controller;

import com.shirin.propertiesconfig.bean.PropertiesTwitter;
import com.shirin.tadawson.propertymanager.PropertyTwitterManager;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class is for showing login page to the client when they did not set
 * their twitter keys
 *
 * @author Shirin Eskandari 10-2019
 */
public class LoginPropertiesFXMLController {

    private final static Logger LOG = LoggerFactory.getLogger(LoginPropertiesFXMLController.class);
    Scene scene;
    Stage stage;
    MainFXMLController mainController;

    private final PropertiesTwitter propertyTwitter;
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;
    @FXML // fx:id="consumerKey"
    private TextField consumerKey; // Value injected by FXMLLoader

    @FXML // fx:id="consumerSecret"
    private TextField consumerSecret; // Value injected by FXMLLoader

    @FXML // fx:id="accessToken"
    private TextField accessToken; // Value injected by FXMLLoader

    @FXML // fx:id="accessTokenSecret"
    private TextField accessTokenSecret; // Value injected by FXMLLoader

    @FXML // fx:id="gridpanelogin"
    private GridPane gridpanelogin; // Value injected by FXMLLoader
    @FXML
    private BorderPane homeBoard;
    @FXML
    private FXMLLoader homeLoader;
    @FXML
    private BorderPane textlogBoard;
    @FXML
    private FXMLLoader textlogLoader;
    @FXML
    private Button onsave;
    private TextArea textArea;

    /**
     * Constructor
     */
    public LoginPropertiesFXMLController() {

        propertyTwitter = new PropertiesTwitter();
    }

    /**
     * It sets scen and stage to this class
     *
     * @param scene
     * @param stage
     * @param mainController
     */
    public void setSceneStageController(Scene scene, Stage stage, MainFXMLController mainController) {
        LOG.info("set scene and stage");
        this.scene = scene;
        this.stage = stage;
        this.mainController = mainController;
    }

    /**
     * When client press on save button, it writes client's keys on the property
     * file
     *
     * @param event
     */
    @FXML
    void onSave(ActionEvent event) {
        LOG.info("onsave Button clicked");
        PropertyTwitterManager propertyManager = new PropertyTwitterManager();
        try {
            propertyManager.writeTextProperties("", "twitter4j", propertyTwitter);

            if (propertyManager.loadTextProperties(propertyTwitter, "", "twitter4j")) {
                mainController.displayPropertiesInTextArea();
                stage.setScene(scene);
            }

        } catch (IOException ex) {
            LOG.error("onSave error", ex);
            errorAlert("onSave()");
            Platform.exit();
        }
    }

    /**
     * Error message popup dialog
     *
     * @param msg
     */
    private void errorAlert(String msg) {
        LOG.info("inside error alert");
        Alert dialog = new Alert(Alert.AlertType.ERROR);
        dialog.setTitle(resources.getString("Error"));
        dialog.setHeaderText(resources.getString("Error"));
        dialog.setContentText(resources.getString(msg));
        dialog.show();
    }

    /**
     * This method is called by the FXMLLoader when initialization is complete
     * bind directional, gets text from the client and binds it to the same
     * variable in the property java class
     */
    @FXML
    void initialize() {
        assert gridpanelogin != null : "fx:id=\"gridpanelogin\" was not injected: check your FXML file 'loginPropertiesFXML.fxml'.";
        assert consumerKey != null : "fx:id=\"consumerKey\" was not injected: check your FXML file 'loginPropertiesFXML.fxml'.";
        assert consumerSecret != null : "fx:id=\"consumerSecret\" was not injected: check your FXML file 'loginPropertiesFXML.fxml'.";
        assert accessToken != null : "fx:id=\"accessToken\" was not injected: check your FXML file 'loginPropertiesFXML.fxml'.";
        assert accessTokenSecret != null : "fx:id=\"accessTokenSecret\" was not injected: check your FXML file 'loginPropertiesFXML.fxml'.";
        Bindings.bindBidirectional(consumerKey.textProperty(), propertyTwitter.consumerKeyProperty());
        Bindings.bindBidirectional(consumerSecret.textProperty(), propertyTwitter.consumerSecretProperty());
        Bindings.bindBidirectional(accessToken.textProperty(), propertyTwitter.accessTokenProperty());
        Bindings.bindBidirectional(accessTokenSecret.textProperty(), propertyTwitter.accessTokenSecretProperty());
    }
}

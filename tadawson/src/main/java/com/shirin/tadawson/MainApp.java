package com.shirin.tadawson;

import com.shirin.propertiesconfig.bean.PropertiesTwitter;
import com.shirin.tadawson.controller.LoginPropertiesFXMLController;
import com.shirin.tadawson.controller.MainFXMLController;
import com.shirin.tadawson.propertymanager.PropertyTwitterManager;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is the starting point of the twitter app, it checks for twitter key
 * properties if there is client properties, it will go inside the twitter app
 * if not it will show login page to the client and after filling out,it will go
 * to twitter app
 *
 * @author Shirin Eskandari 10-2019
 */
public class MainApp extends Application {

    private final static Logger LOG = LoggerFactory.getLogger(MainApp.class);

    private Stage primaryStage;
    private MainFXMLController mainController;
    private ResourceBundle defaultMsg;
    private ResourceBundle msg;

    private final Locale currentLocale;
    private final Locale defaultLocale;

    /**
     *
     */
    public MainApp() {
        super();
        defaultLocale = Locale.getDefault();
        currentLocale = new Locale("fr", "CA");

    }

    /**
     * Standard start method. Checks for presence of a properties file and if
     * not found then it will display the properties form.
     *
     * @param primaryStage
     * @throws Exception
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        LOG.info("Inside starts method");
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("The Tadawson Twitter");

        this.primaryStage.getIcons().add(
                new Image(MainApp.class
                        .getResourceAsStream("/images/twitter-icon.png")));
        Scene scene2 = createRestOfTheProgram();
        Scene scene1 = createPropertiesForm(scene2);
        if (!checkForProperties()) {
            this.primaryStage.setScene(scene1);
            LOG.info(" properties scene because no properties file found");
        } else {
            this.primaryStage.setScene(scene2);
            LOG.info(" main page scene because properties file was found");
        }
        this.primaryStage.setTitle("Twitter Application");

        this.primaryStage.show();
    }

    /**
     * Create a stage with the scene showing the primary program window
     *
     * @return scene
     * @throws Exception
     */
    private Scene createRestOfTheProgram() throws Exception {
        LOG.info("inside createRestOfTheProgram method");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("/fxml/MainFXML.fxml"));
        loader.setResources(ResourceBundle.getBundle("MessagesBundle", defaultLocale));
        Parent borderRoot = (BorderPane) loader.load();
        mainController = loader.getController();
        mainController.displayPropertiesInTextArea();
        Scene scene = new Scene(borderRoot);
        return scene;
    }

    /**
     * Assuming that properties are not found then here is the properties window
     *
     * @param scene2
     * @throws Exception
     */
    private Scene createPropertiesForm(Scene scene2) throws Exception {
        LOG.info("createPropertiesForm");
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("/fxml/loginPropertiesFXML.fxml"));
        loader.setResources(ResourceBundle.getBundle("MessagesBundle", defaultLocale));
        Parent root = (GridPane) loader.load();
        LoginPropertiesFXMLController controller = loader.getController();
        controller.setSceneStageController(scene2, primaryStage, mainController);
        Scene scene = new Scene(root);
        return scene;
    }

    /**
     * Check if a Properties file exists and can be loaded into a bean. This
     * does not verify that the contents of the bean fields are valid.
     *
     * @return found Have we been able to load the properties
     */
    private boolean checkForProperties() {
        LOG.info("checkForProperties");
        boolean found = false;
        PropertiesTwitter propertyTwitter = new PropertiesTwitter();
        PropertyTwitterManager propertyManager = new PropertyTwitterManager();

        try {
            if (propertyManager.loadTextProperties(propertyTwitter, "", "twitter4j")) {
                found = true;
            }
        } catch (IOException ex) {
            LOG.error("checkForProperties error", ex);
        }
        return found;
    }

    /**
     * The main() method
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}

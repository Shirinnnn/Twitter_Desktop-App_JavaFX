package com.shirin.tadawson.business;

import com.shirin.propertiesconfig.bean.TwitterInfo;
import java.util.logging.Level;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.TwitterException;

/**
 *
 * @author Shirin Eskandari
 */
public class TwitterInfoCellMessage extends ListCell<TwitterInfoMessage> {

    private final static Logger LOG = LoggerFactory.getLogger(TwitterInfoCellMessage.class);
    private final TwitterEngine twitterEngine = new TwitterEngine();
    private TwitterInfoMessage infoMessage;
    private HBox node;
    private TwitterInfo twitterInfo;

    /**
     * This method is called when ever cells need to be updated
     *
     * @param item
     * @param empty
     */
    @Override
    protected void updateItem(TwitterInfoMessage item, boolean empty) {
        super.updateItem(item, empty);
        LOG.debug("updateItem");
        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            try {

                setGraphic(getTwitterInfoCellMessage(item));
            } catch (TwitterException ex) {
                java.util.logging.Logger.getLogger(TwitterInfoCellMessage.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private Node getTwitterInfoCellMessage(TwitterInfoMessage infoMessage) throws TwitterException {
        this.infoMessage = infoMessage;
        LOG.debug("getTwitterInfoCell");
        HBox node = new HBox();
        node.setSpacing(10);

        String name = twitterEngine.showUser(infoMessage.getMessageSenderId()).getName();
        String handleName = twitterEngine.showUser(infoMessage.getMessageSenderId()).getScreenName();
        Text text = new Text(name + "  @" + handleName);
        Text text2 = new Text(infoMessage.getMessage());

        text.setWrappingWidth(450);
        Label handle = new Label();

        VBox vbox = new VBox();
        vbox.getChildren().addAll(text, handle, text2);

        node = makeMainNode(twitterEngine.showUser(infoMessage.getMessageSenderId()).getOriginalProfileImageURL());
        node.getChildren().addAll(vbox);
        return node;
    }

    private HBox makeMainNode(String img) {
        node = makeHbox();
        Image image = new Image(img, 48, 48, true, false);
        ImageView imageView = new ImageView(image);
        node.getStylesheets().add(getClass().getResource("/styles/hometimelinefxml.css").toExternalForm());
        node.getStyleClass().add("cssLayout");
        node.getChildren().addAll(imageView);
        return node;
    }

    private HBox makeHbox() {
        HBox node = new HBox();
        node.setSpacing(15);
        node.setPadding(new Insets(10, 10, 10, 10));
        return node;
    }
}

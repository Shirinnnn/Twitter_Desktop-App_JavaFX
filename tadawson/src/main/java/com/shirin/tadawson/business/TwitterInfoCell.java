package com.shirin.tadawson.business;

import com.shirin.propertiesconfig.bean.TweetsDB;
import com.shirin.propertiesconfig.bean.TwitterInfo;
import com.shirin.propertiesconfig.bean.TwitterInfoDB;
import com.shirin.propertiesconfig.bean.TwitterInterface;
import com.shirin.tadawson.controller.AlertMessages;
import com.shirin.tadawson.controller.HomeTimeLineFXMLController;
import com.shirin.tadawson.controller.MainFXMLController;
import com.shirin.tadawson.persistence.TwitterDAOImpl;
import java.io.InputStream;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import twitter4j.OEmbedRequest;
import twitter4j.Status;
import twitter4j.TwitterException;

/**
 * ListCell for TwitterInfo This class represents the contents of an HBox that
 * contains tweet info
 *
 * This is based on the following
 * https://github.com/tomoTaka01/TwitterListViewSample.git Make other
 * information about twitter like comments, favorite button, retweet and put it
 * in seprate method for SOP, add some style to this timeline
 *
 * @author tomo
 * @author Ken Fogel
 * @author Shirin Eskandari
 */
public class TwitterInfoCell extends ListCell<TwitterInterface> {

    private final static Logger LOG = LoggerFactory.getLogger(TwitterInfoCell.class);
    private final TwitterEngine twitterEngine = new TwitterEngine();
    private Hyperlink link;
    private Button profilepicBtn;
    private ImageView imageViewPic;
    private TwitterInterface info;
    private HBox topNode;
    private HBox downHbox;
    private Button retweet;
    private Button countFavorite;
    private Button comment;
    private Button saveInDbBtn;
    private HBox node;
    private Button follow;
    private MenuButton retweetWithMsg;
    private MenuButton menuButton;
    private MenuItem menuItem1;
    private MenuItem menuItem2;
    private InputStream input2;
    private Image imagevieww;
    private MainFXMLController main;
    private HomeTimeLineFXMLController home;
    private Status status;
    private AlertMessages alerts = new AlertMessages();
    private TwitterInfoDB infoDb;
    private TweetsDB tweetDb;

    /**
     * This method is called when ever cells need to be updated
     *
     * @param item
     * @param empty
     */
    @Override
    protected void updateItem(TwitterInterface item, boolean empty) {

        LOG.info("inside update method");
        super.updateItem(item, empty);
        LOG.debug("updateItem");
        if (empty || item == null) {
            setText(null);
            setGraphic(null);
        } else {
            try {
                setGraphic(getTwitterInfoCell(item));
            } catch (TwitterException ex) {
                java.util.logging.Logger.getLogger(TwitterInfoCell.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * This method determines what the cell will look like. It makes the top of
     * the cell with HBOX for holding the name and handle of the twitterInfo
     * then adds content of the tweet at the end makes HBox which holds 3 image
     * buttons for comment,retweet, favorite count
     *
     * @param info
     * @return The node to be placed into the ListView
     */
    private Node getTwitterInfoCell(TwitterInterface info) throws TwitterException {
        LOG.info("inside getTwitterInfoCell");
        this.info = info;
        LOG.debug("getTwitterInfoCell");
        topNode = makeTopCell();
        Text text = new Text(info.getText());
        text.setWrappingWidth(450);
        downHbox = makeDownCell();
        VBox vbox = new VBox();
        vbox.getChildren().addAll(topNode, text, downHbox);
        node = makeMainNode();
        node.getChildren().addAll(vbox);
        return node;
    }

    /**
     * This helper method simply creates an Hbox with some styling
     *
     * @return HBox node
     */
    private HBox makeHbox() {
        LOG.info("Make Hbox ");
        HBox node = new HBox();
        node.setSpacing(12);
        node.setPadding(new Insets(3, 3, 3, 3));
        return node;
    }

    /**
     * This method simply creates an ImageButton with some styling
     *
     * @param imageAddres
     * @return Button with an image on it
     */
    private Button makeImageButton(String imageAddrres) {
        LOG.info("makeImageButton ");
        InputStream input = getClass().getResourceAsStream(imageAddrres);
        Image imageButton = new Image(input, 42, 28, true, true);
        Button button = new Button();
        button.setGraphic(new ImageView(imageButton));
        getStylesheets().add(getClass().getResource("/styles/hometimelinefxml.css").toExternalForm());
        button.setStyle("cssButton");
        return button;
    }

    /**
     * This helper method makes the down part of each twitterInfo which contains
     * comment, retweet and count of favorites
     *
     * @return HBox contains of 3 buttons
     */
    private HBox makeDownCell() throws TwitterException {
        LOG.info("Make comment/retweet/like buttons");
        downHbox = makeHbox();
        downHbox.getStyleClass().add("cssButton");
        comment = makeImageButton("/images/comment3.jpg");
        setActionComment(comment);
        menuButton = makeRetweetButton();
        countFavorite = makeFavoriteButton();
        saveInDbBtn = makeImageButton("/images/downloadd.png");
        setActionSave(saveInDbBtn);
        downHbox.getChildren().addAll(comment, menuButton, countFavorite, saveInDbBtn);
        return downHbox;
    }

    /**
     * Set action for Comment to show the dialog input to user for adding
     * comment to that tweet
     *
     * @param comment
     * @throws TwitterException
     */
    public void setActionComment(Button comment) throws TwitterException {
        LOG.info("Inside set Action for Comment button");
        OEmbedRequest embed;
        comment.setOnAction(event -> {
            String text2 = "";
            Dialog dialog = new Dialog();
            dialog.setTitle("Comment");
            dialog.setHeaderText("Comment");
            TextArea textarea = new TextArea();
            textarea.setWrapText(true);
            dialog.setGraphic(textarea);
            dialog.setWidth(500.0);
            dialog.setHeight(300.0);
            dialog.setResizable(true);
            dialog.getDialogPane().setPrefSize(380, 220);
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CLOSE, ButtonType.OK);
            Optional<ButtonType> result = dialog.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                text2 = textarea.getText();
                alerts.infoAllert("your retweet is submitted");
            } else {
                alerts.infoAllert("you did not provide any text");
            }
            if (result.isEmpty() || result.get() == ButtonType.CLOSE) {
                alerts.infoAllert("you did not provide any text");
            }
            long replyid = this.info.getId();
            try {
                String message = twitterEngine.replyToComment(text2 + " ", replyid, this.info.getScreenName());
                //   dialog.show();
                //   alerts.infoAllert("your comment submitted");
            } catch (TwitterException ex) {
                java.util.logging.Logger.getLogger(TwitterInfoCell.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    /**
     * This helper method creates total style of each twitterInfo cell It gets
     * image of the twitterInfo, add styles from css style resources
     *
     * @param info
     * @return HBox
     */
    private HBox makeMainNode() {
        LOG.info("Make Image url of each tweeter info ");
        node = makeHbox();
        profilepicBtn = new Button();
        Image image = new Image(this.info.getURL(), 52, 52, true, false);
        imageViewPic = new ImageView(image);
        profilepicBtn.setGraphic(imageViewPic);
        setActionProfilePic(profilepicBtn);
        node.getStylesheets().add(getClass().getResource("/styles/hometimelinefxml.css").toExternalForm());
        node.getStyleClass().add("cssLayout");
        node.getChildren().addAll(profilepicBtn);
        return node;
    }

    private void setActionProfilePic(Button profilepicBtn) {
        LOG.info("setActionProfilePic");
        profilepicBtn.setOnAction(event -> {
            Dialog<Pair<String, String>> dialog = new Dialog<>();
            dialog.setTitle("Profile");
            dialog.setHeaderText("Profile");
            dialog.setGraphic(new ImageView(this.info.getURL()));

            String description = this.info.getDescription();
            String username = this.info.getName();
            int followerscount = this.info.getFollowersCount();
            int following = this.info.getFriendsCount();
            dialog.setHeaderText(username);
            dialog.setContentText(description + "\n  followers : " + followerscount + "  following : " + following);

            dialog.setWidth(500.0);
            dialog.setHeight(300.0);
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CLOSE);
            dialog.show();

        });
    }

    /**
     * Set action for sending a message with retweet it opens an input dialog
     * for user input
     *
     * @param menuItem2
     * @throws TwitterException
     */
    public void setActionRetweetWithMessage(MenuItem menuItem2) throws TwitterException {
        LOG.info("Make Action for sending message with retweet");
        menuItem2.setOnAction(event -> {
            try {
                OEmbedRequest embed;
                String textretweet = "";

                Dialog dialog = new Dialog();
                dialog.setTitle("Retweet");
                dialog.setHeaderText("Retweet");
                TextArea textarea = new TextArea();
                textarea.setWrapText(true);
                dialog.setGraphic(textarea);
                dialog.setWidth(500.0);
                dialog.setHeight(300.0);
                dialog.setResizable(true);
                dialog.getDialogPane().setPrefSize(380, 220);
                dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CLOSE, ButtonType.OK);
                Optional<ButtonType> result = dialog.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    textretweet = textarea.getText();
                    alerts.infoAllert("your retweet is submitted");
                } else {
                    alerts.infoAllert("you did not provide any text");
                }
                embed = twitterEngine.makeEmbed(this.info.geturl(), this.info.getId());
                String tweet = twitterEngine.createTweet(textretweet + " ", embed);
                twitterEngine.helperMakeComment(tweet, this.info.getQuotedId());

                //alerts.infoAllert("your comment submitted");
            } catch (TwitterException ex) {
                java.util.logging.Logger.getLogger(TwitterInfoCell.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    /**
     * Make retweet button which is an menubutton consist of 2 buttons for
     * retweet and retweet with message
     *
     * @return
     * @throws TwitterException
     */
    private MenuButton makeRetweetButton() throws TwitterException {
        LOG.info("Make Retweet menuButton");
        menuItem1 = new MenuItem("retweet");
        menuItem2 = new MenuItem("retweet with message");
        if (this.info.isRetweetedByMe()) {
            menuItem1.setText("undoRetweet");
            menuButton = new MenuButton(" ", makeImageButton("/images/retweetp.png"), menuItem1, menuItem2);
        } else {
            menuItem1.setText("Retweet");
            menuButton = new MenuButton(" ", makeImageButton("/images/retweet.png"), menuItem1, menuItem2);
        }
        menuButton.setText(info.getRetweetCount() + "");
        menuButton.getStyleClass().add("cssButton");
        //   menuButton.setMaxWidth(USE_PREF_SIZE);
        setActionRetweet(menuItem1);
        setActionRetweetWithMessage(menuItem2);
        return menuButton;
    }

    /**
     * Set action for retweet the tweet
     *
     * @param menuItem1
     */
    public void setActionRetweet(MenuItem menuItem1) {
        LOG.info("Set action for retweet ");
        menuItem1.setOnAction(event -> {
            try {
                if (this.info.isRetweetedByMe()) {
                    status = twitterEngine.unRetweet(this.info.getId());
                    this.info = new TwitterInfo(status);
                    menuItem1.setText("undoRetweet");
                    setGraphic(getTwitterInfoCell(this.info));
                } else {
                    status = twitterEngine.retweetStatus(this.info.getId());
                    this.info = new TwitterInfo(status);
                    menuItem1.setText("Retweet");
                    setGraphic(getTwitterInfoCell(this.info));
                }

            } catch (TwitterException ex) {
                java.util.logging.Logger.getLogger(TwitterInfoCell.class.getName()).log(Level.INFO, null, ex);
                alerts.infoAllert("you can not retweet again");
            }
        });
    }

    /**
     * This helper method gets name and handle of TwitterInfo and makes an HBox
     * to hold them Make labels for screen name, handle name and follow/unfollow
     * button and showing the time"
     *
     * @param info
     * @return HBox
     */
    private HBox makeTopCell() {
        LOG.info("Make labels for screen name, handle name and follow/unfollow button and showing the time");
        topNode = makeHbox();
        link = makeHyperLink();

        Text handle = new Text("@" + this.info.getHandle());
        this.follow = makeFollowButton();
        Label showTime = new Label();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date datetweet = info.CreatedAt();
        String date5 = dateFormat.format(datetweet);
        showTime.setText(" Tweeted  " + date5);
        topNode.getChildren().addAll(link, handle, this.follow, showTime);
        return topNode;
    }

    /**
     * make link on the name of the twitter info
     *
     * @return
     */
    private Hyperlink makeHyperLink() {
        LOG.info("inside making link method");
        link = new Hyperlink();
        link.setText(this.info.getName());
        setActionClickProfile(link);
        return link;
    }

    private void setActionClickProfile(Hyperlink link) {
        LOG.info("setActionClickProfile");
        link.setOnAction(event -> {
            Dialog<Pair<String, String>> dialog = new Dialog<>();
            dialog.setTitle("Profile");
            dialog.setHeaderText("Profile");
            dialog.setGraphic(new ImageView(this.info.getURL()));
            Button follow_unfollowBtn = new Button("UnFololow");
            String description = this.info.getDescription();
            String username = this.info.getName();
            int followerscount = this.info.getFollowersCount();
            int following = this.info.getFriendsCount();
            dialog.setHeaderText(username);
            dialog.setContentText(description + "\n  followers : " + followerscount + "  following : " + following);

            dialog.setHeaderText(username);

            dialog.setWidth(600.0);
            dialog.setHeight(400.0);
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CLOSE);
            dialog.show();
            //dialog.close();
        });
    }

    /**
     * Make follow button
     *
     * @return
     */
    private Button makeFollowButton() {
        LOG.info("Inside making Follow button ");
        try {
            if (twitterEngine.friendshipStatus(this.info.getScreenName())) {
                this.follow = makeImageButton("/images/friend.jpg");
                this.follow.setText(" Following");
            } else {
                this.follow = makeImageButton("/images/nomember3.jpg");
                this.follow.setText(" FollowMePls");
            }
        } catch (TwitterException ex) {
            java.util.logging.Logger.getLogger(TwitterInfoCell.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            setActionFollow(this.follow);
        } catch (TwitterException ex) {
            java.util.logging.Logger.getLogger(TwitterInfoCell.class.getName()).log(Level.SEVERE, null, ex);

        }
        return this.follow;
    }

    /**
     * Set action on follow button for follow/unfollow
     *
     * @param follow
     * @throws TwitterException
     */
    public void setActionFollow(Button follow) throws TwitterException {
        LOG.info("set action on follow button");
        follow.setOnAction(event -> {
            try {
                if (twitterEngine.friendshipStatus(this.info.getScreenName())) {
                    twitterEngine.destroyFriendship(this.info.getScreenName());
                    setGraphic(getTwitterInfoCell(this.info));
                } else {
                    twitterEngine.createFriendship(this.info.getScreenName());
                    setGraphic(getTwitterInfoCell(this.info));
                }
            } catch (TwitterException ex) {
                java.util.logging.Logger.getLogger(TwitterInfoCell.class.getName()).log(Level.INFO, null, ex);
                alerts.errorAlert("You can not add yourself");
            }
        });
    }

    /**
     * This method calls when the user wants like or dislike a tweet it creates
     * and destroys the like status with TwitterEngin class then update the
     * status of the current cell
     *
     * @param countFavorite
     */
    public void setActionFavorite(Button countFavorite) {
        LOG.info("Set action on like button");
        countFavorite.setOnAction(event -> {
            long id = this.info.getId();
            LOG.info("favorit clicked");
            try {
                if (this.info.isFavorited()) {
                    Status status = twitterEngine.destroyFavorite(this.info.getId());
                    this.info = new TwitterInfo(status);
                    setGraphic(getTwitterInfoCell(this.info));
                } else {
                    Status status = twitterEngine.createFavorite(id);
                    this.info = new TwitterInfo(status);
                    setGraphic(getTwitterInfoCell(this.info));
                }
            } catch (TwitterException ex) {
                java.util.logging.Logger.getLogger(TwitterInfoCell.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    /**
     * This helper method checks the favorite button. on base of it isFavorited
     * or not changes the heart shape of the image and also gets the number of
     * favorites and shows it. notice, it uses isFavorited method in TwitterInfo
     * class
     *
     * @return Button
     */
    private Button makeFavoriteButton() {
        LOG.info("inside make favorite button");
        if (this.info.isFavorited()) {
            this.countFavorite = makeImageButton("/images/filledheart.png");
        } else {
            this.countFavorite = makeImageButton("/images/emptyheart2.jpg");
        }
        countFavorite.setText("" + this.info.getFavoriteCount());
        countFavorite.setStyle("cssButton");
        setActionFavorite(countFavorite);
        return countFavorite;
    }

    /**
     * save all data from single twitter user to the database
     *
     * @param saveInDbBtn
     */
    private void setActionSave(Button saveInDbBtn) {
        LOG.info("inside setActionSave");
        Timestamp timedb = new Timestamp(this.info.CreatedAt().getTime());
        saveInDbBtn.setOnAction(event -> {
            infoDb = new TwitterInfoDB((int) this.info.getId(), (int) this.info.getQuotedId(), this.info.getName(),
                    this.info.getScreenName(), this.info.isFollow(), timedb,
                    this.info.getText(), this.info.getURL(),
                    this.info.getFavoriteCount(), this.info.isFavorited(), this.info.isRetweetedByMe(),
                    this.info.getId(), this.info.getRetweetCount());
            tweetDb = new TweetsDB(1, (int) this.info.getId(), this.info.getText(), this.info.getFavoriteCount(),
                    this.info.isFavorited(), this.info.isRetweetedByMe(), timedb);
            TwitterDAOImpl twitterDAO = new TwitterDAOImpl();
            try {
                int records = twitterDAO.create(infoDb);
                tweetDb.setTtwitterid(records);
                int records2 = twitterDAO.createTweet(tweetDb);
            } catch (SQLException ex) {
                java.util.logging.Logger.getLogger(TwitterInfoCell.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
    }
}


package com.shirin.tadawson.controller;

import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Helper methods of alerts which I am using in all classes
 *
 * @author Shirin Oct 2019
 */
public class AlertMessages {

    private final static Logger LOG = LoggerFactory.getLogger(AlertMessages.class);

    /**
     * This method validate the number of characters, that user wants to enter
     * in the text area There is a limitation of 280 characters and if user
     * enters more there is a dialog error to show him the rule
     *
     */
    public void validateText(TextArea textArea, int limit) {
        LOG.info("inside validate text method");
        textArea.textProperty().addListener(
                (observable, oldValue, newValue) -> {
                    if (newValue.length() > limit) {
                        textArea.setText(oldValue);
                        LOG.info("you can not enter more than " + limit + " char");
                        errorAlert("you can not enter more than " + limit + "  char");
                    }
                }
        );
    }

    /**
     * Error Alert method
     *
     * @param msg
     */
    public void errorAlert(String msg) {
        LOG.info("inside Error Alert method");
        Alert dialog = new Alert(Alert.AlertType.ERROR);
        dialog.setTitle("Error");
        dialog.setHeaderText("Error");
        dialog.setContentText(msg);
        dialog.show();
    }

    /**
     * Allert Info Method
     *
     * @param msg
     */
    public void infoAllert(String msg) {
        LOG.info("inside Info Alert method");
        Alert dialog = new Alert(Alert.AlertType.INFORMATION);
        dialog.setTitle("Tadawson");
        dialog.setHeaderText("About");
        dialog.setContentText(msg);
        dialog.show();
    }
}

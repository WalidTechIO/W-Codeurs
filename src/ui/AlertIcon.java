package ui;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

class AlertIcon extends Alert {

  AlertIcon(AlertType arg0, String msg, ButtonType... bt) {
    super(arg0, msg, bt);
    Stage stage = (Stage) this.getDialogPane().getScene().getWindow();
    stage.getIcons().add(new Image(getClass().getResourceAsStream("icon.png")));
  }
  
}

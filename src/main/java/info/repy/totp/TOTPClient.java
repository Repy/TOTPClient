/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.repy.totp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 *
 * @author repy
 */
public class TOTPClient extends Application {
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		stage.getIcons().add(new Image(TOTPClient.class.getResourceAsStream("/icon.png")));
		stage.setTitle("TOTPClient");
		stage.setScene(new Scene(FXMLLoader.load(getClass().getClassLoader().getResource("Form.fxml"))));
		stage.show();
	}

	@Override
	public void stop() throws Exception {
	}


}

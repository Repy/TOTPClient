/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package info.repy.totp;

import java.io.*;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

/**
 * FXML Controller class
 *
 * @author repy
 */
public class FormController implements Initializable {
	private final ObservableList<NameValue> totpSelectList = FXCollections.observableArrayList();
	@FXML
	private ChoiceBox<NameValue> selectTotp;
	@FXML
	private TextField textName;
	@FXML
	private TextField textKey;
	@FXML
	private Label labelValue;
	
	private Timeline timer;
	
	private Properties config;

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		config = new Properties();
		try (InputStream inputStream = new FileInputStream(new File("setting.properties"))) {
			config.load(inputStream);
			for (String name : config.stringPropertyNames()) {
				totpSelectList.add(new NameValue(name, config.getProperty(name)));
			}
			selectTotp.setItems(totpSelectList);
		} catch (IOException ex) {
			Logger.getLogger(FormController.class.getName()).log(Level.SEVERE, null, ex);
		}
        timer = new Timeline(new KeyFrame(javafx.util.Duration.millis(1000), new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
				NameValue select = selectTotp.getSelectionModel().getSelectedItem();
				if (select == null) return;
				try{
					org.jboss.aerogear.security.otp.Totp totp = new org.jboss.aerogear.security.otp.Totp(select.value);
					labelValue.setText(totp.now());
				} catch (Exception ex) {
					labelValue.setText("Error");
					Logger.getLogger(FormController.class.getName()).log(Level.SEVERE, null, ex);
				}
            }
        }));
        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
	}

	@FXML
	private void buttonCopyAction(ActionEvent event) {
		final Clipboard clipboard = Clipboard.getSystemClipboard();
		final ClipboardContent content = new ClipboardContent();
		content.putString(labelValue.getText());
		clipboard.setContent(content);
	}
	
	@FXML
	private void buttonAddAction(ActionEvent event) {
		String name = textName.getText();
		String key = textKey.getText();
		NameValue nv = new NameValue(name, key);
		totpSelectList.add(nv);
		selectTotp.setItems(totpSelectList);
		selectTotp.getSelectionModel().select(nv);
		config.put(name, key);
		try (OutputStream outputStream = new FileOutputStream(new File("setting.properties"),false)) {
			config.store(outputStream, "");
		} catch (IOException ex) {
			Logger.getLogger(FormController.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	private static class NameValue {
		private final String name;
		private final String value;

		public NameValue(String name, String value) {
			this.name = name;
			this.value = value;
		}

		public String getName() {
			return name;
		}

		@Override
		public String toString() {
			return name;
		}

		public String getValue() {
			return value;
		}
	}

}

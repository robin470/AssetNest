package application.controller;


import java.io.IOException;
import java.net.URL;

import application.CommonObjs;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

//this NoticeController controls the notice view

public class NoticeController {


	private CommonObjs commonObjs = CommonObjs.getInstance();

	public void showHomeOp() {
		URL url = getClass().getClassLoader().getResource("view/Home.fxml");
		HBox mainBox = commonObjs.getMainBox();
		
		try {
			AnchorPane pane = (AnchorPane) FXMLLoader.load(url);
			
			if (mainBox.getChildren().size() > 1) {
				mainBox.getChildren().remove(1);
			}
			
			mainBox.getChildren().add(pane);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void showWarrantiesOp() {
		   
		URL url = getClass().getClassLoader().getResource("view/Warranties.fxml");
		HBox mainBox = commonObjs.getMainBox();
		
		try {
			AnchorPane pane = (AnchorPane) FXMLLoader.load(url);
			
			if (mainBox.getChildren().size() > 1) {
				mainBox.getChildren().remove(1);
			}
			
			mainBox.getChildren().add(pane);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}


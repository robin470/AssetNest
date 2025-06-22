package application.controller;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import application.CommonObjs;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

//this WarrantiesController controls the warranties view and return list of assets of expired warranties

public class WarrantiesController {




	   @FXML
	   TextField searchKeyword;
	   @FXML
	   ListView<String> resultList;


	   @FXML
	   public void initialize() {
	       loadExpiredWarranties();
	   }


	   private void loadExpiredWarranties() {
	       String csvFile = "src/asset.csv"; 
	       List<String> expiredItems = new ArrayList<>();
	       DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");


	       try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
	           String line;
	           while ((line = br.readLine()) != null) {
	               String[] parts = line.split(",");
	               if (parts.length > 3) {
	                   LocalDate warrantyExpDate = LocalDate.parse(parts[6], formatter);
	                   if (warrantyExpDate.isBefore(LocalDate.now())) {
	                       expiredItems.add(parts[0]+ ", " +parts[1]+ ", "+ parts[2]+ ", "+ parts[3]+ 
	                    		       ", "+ parts[4] + ", "+ parts[5] + ", " + parts[6]); 
	                   }
	               }
	           }
	       } catch (IOException e) {
	           e.printStackTrace();
	       }

	       resultList.getItems().setAll(expiredItems);
	   }

	   private CommonObjs commonObjs = CommonObjs.getInstance();


	   public void seeInfo(ActionEvent event) {
		   
		   String selectedAsset = resultList.getSelectionModel().getSelectedItem();
		      if (selectedAsset != null) {
		         try {  	
		            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/SeeInfo.fxml"));
		            AnchorPane pane = loader.load();
		            
		            HBox mainBox = commonObjs.getMainBox();
		            
		            if (mainBox.getChildren().size() > 1) {
		            	mainBox.getChildren().remove(1);
		            }
		            
		            mainBox.getChildren().add(pane);
		            
		            // Pass the selected asset to the controller of the seeInfo screen
		            SeeInfoController infoController = loader.getController();
		            infoController.setAssetInfo(selectedAsset);

		         } catch (IOException e) {
		            e.printStackTrace();
		         }
		      } else {
		         showAlert("No Selection", "No Asset Selected", "Please select an asset to view.");
		      }
		   }


	   private void showAlert(String title, String header, String content) {
	       Alert alert = new Alert(Alert.AlertType.WARNING);
	       alert.setTitle(title);
	       alert.setHeaderText(header);
	       alert.setContentText(content);
	       alert.showAndWait();
	   }


	}

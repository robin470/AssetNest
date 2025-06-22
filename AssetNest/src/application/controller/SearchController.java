package application.controller;


import java.io.IOException;
import java.util.List;

import application.CommonObjs;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import model.CSVReader;

//this searchController controls the search view and return list of assets when user search for an asset

public class SearchController {


   @FXML
   TextField searchKeyword;
   @FXML
   ListView<String> resultList;
   
   private CommonObjs commonObjs = CommonObjs.getInstance();
   @FXML ComboBox<String> filterCategory;
   @FXML ComboBox<String> filterLocation;

   public void initialize() {
		// Populate the ComboBoxes with data from the CSV files
		String categoryFilePath = "src/categories.csv";
		String locationFilePath = "src/locations.csv";
   
       CSVReader.populateDropdownButton(filterCategory, categoryFilePath);
       CSVReader.populateDropdownButton(filterLocation, locationFilePath);
   }
   
   @FXML
   public void searchAsset(ActionEvent event) {
      String keyword = searchKeyword.getText().trim();
      String selectedCategory = filterCategory.getValue();
      String selectedLocation = filterLocation.getValue();
      
      // Call a method to perform the search with the provided keyword
      List<String> searchResults = null;
      try {
         searchResults = SearchManager.searchAssets("src/asset.csv", keyword, selectedCategory, selectedLocation);
         /* for debugging purposes
         for (String result : searchResults) {
             System.out.println("t4"+result);
         }
         */
      } catch (Exception e) {
         e.printStackTrace();
      }
      // Check if searchResults is empty
      if (searchResults.isEmpty()) {
          showAlert("No Results", "No Assets Found", "No assets matching the search keyword were found.");
      } else {
    	  // Populate the ListView with the search results
          resultList.setItems(FXCollections.observableArrayList(searchResults));
      }
   }

   // Handle edit button action
   @FXML
   public void editAsset(ActionEvent event) {
      String selectedAsset = resultList.getSelectionModel().getSelectedItem();
      if (selectedAsset != null) {
         try {  	
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/EditAsset.fxml"));
            AnchorPane pane = loader.load();
            
            HBox mainBox = commonObjs.getMainBox();
            
            if (mainBox.getChildren().size() > 1) {
            	mainBox.getChildren().remove(1);
            }
            
            mainBox.getChildren().add(pane);
            
            /*
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(pane));
            stage.show();
            */
            
            // Pass the selected asset to the controller of the edit screen
            EditAssetController editController = loader.getController();
            editController.initData(selectedAsset);

         } catch (IOException e) {
            e.printStackTrace();
         }
      } else {
         showAlert("No Selection", "No Asset Selected", "Please select an asset to edit.");
      }
   }

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


   @FXML public void deleteAsset(ActionEvent event) {
	   String selectedAsset = resultList.getSelectionModel().getSelectedItem();
	    if (selectedAsset != null) {
	        AssetDeleter.deleteAsset(selectedAsset);
	        // Display success message
	        showAlert("Success", "Asset Deleted", "The asset has been successfully deleted.");
	        // Clear the search field
	        searchKeyword.clear();
	        
	        // Clear the results list
	        resultList.getItems().clear();

	    } else {
	        showAlert("No Selection", "No Asset Selected", "Please select an asset to delete.");
	    }
   }

   @FXML public void clear(ActionEvent event) {
	   searchKeyword.clear();
       filterCategory.getSelectionModel().clearSelection();
       filterLocation.getSelectionModel().clearSelection();
       resultList.getItems().clear();
   }
}


package application.controller;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.CSVReader;

public class AddAssetController {

	@FXML TextField assetName;
	@FXML ComboBox<String> categoryDropDown;
	@FXML ComboBox<String> locationDropDown;
	@FXML TextField assetValue;
	@FXML TextArea assetDescription;
	@FXML DatePicker assetPurchaseDate;
	@FXML DatePicker assetWarrantyExp;
	@FXML public void submitAsset(ActionEvent event) {}
	public void initialize() {
		// Populate the ComboBoxes with data from the CSV files
		String categoryFilePath = "src/categories.csv";
		String locationFilePath = "src/locations.csv";
    
        CSVReader.populateDropdownButton(categoryDropDown, categoryFilePath);
        CSVReader.populateDropdownButton(locationDropDown, locationFilePath);
    }
	
	@FXML public void submitAsset1(ActionEvent event) {
		String name = assetName.getText().trim();
	    String category = categoryDropDown.getValue();
	    String location = locationDropDown.getValue();
	    String value = assetValue.getText().trim();
	    String description = assetDescription.getText().trim();
	    LocalDate purchaseDate = assetPurchaseDate.getValue();
	    LocalDate warrantyExpiration = assetWarrantyExp.getValue();
	    // Validate input 
	    if (name.isEmpty() || category.isEmpty() || location.isEmpty()) {
	        showAlert(Alert.AlertType.ERROR, "Error", "* fields cannot be empty.");
	    } else {
	    	try {
	    		// Save to CSV file
	            saveToCSV(name, category, location, value, description, purchaseDate, warrantyExpiration);
	            showAlert(Alert.AlertType.INFORMATION, "Success", "Asset saved successfully.");
	            // Clear the text field after saving
	            assetName.clear();
	            assetValue.clear();
	            
	            
	        }catch(IOException e){
	        	showAlert(Alert.AlertType.ERROR, "Error", "Failed to save asset to CSV file.");
	        }
	    }	    
	}
	private void saveToCSV(String name, String category, String location, String value, String description, LocalDate purchaseDate, LocalDate warrantyExpiration) throws IOException {
	    try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/asset.csv", true))) {
	        // replace empty ones with a placeholder
	        if (name.isEmpty())               name = "N/A";
	        if (category.isEmpty())       category = "N/A";
	        if (location.isEmpty())       location = "N/A";
	        if (value.isEmpty())             value = "N/A";
	        if (description.isEmpty()) description = "N/A";
	        if (purchaseDate == null) {
	            purchaseDate = LocalDate.now(); // if empty set it as today's date
	        }
	        if (warrantyExpiration == null) {
	            warrantyExpiration = LocalDate.of(2099, 12, 31); // Or set it to a default value
	        }
	        // Append the location name and description to the CSV file
	        writer.write(name + "," + category + "," + location + "," + value + "," + description + "," + purchaseDate + "," + warrantyExpiration);
	        writer.newLine();
	    }
	}

	private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
	
}

package application.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import model.CSVReader;

//this editAssetController is used by search Controller when user selects an asset and choose to modify it
//it edits the selected asset and overwrite the csv file

public class EditAssetController {

    @FXML TextField editAssetName;
    @FXML ComboBox<String> editAssetCategory;
    @FXML ComboBox<String> editAssetLocation;
    @FXML TextArea editAssetDescription;
    @FXML TextField editAssetValue;
    @FXML DatePicker editAssetPurchaseDate;
    @FXML DatePicker editAssetExp;
    @FXML Button submitUpdate;
    
    public String selectedAsset;

    public void initData(String selectedAsset) {
        this.selectedAsset = selectedAsset;
        // Split the selected asset string by comma to extract its information
        String[] parts = selectedAsset.split(",");
        // Extract the information from the parts array
        if (parts.length >= 7) {
            // Extract the information from the parts array
            String assetName = parts[0].trim();
            String category = parts[1].trim();
            String location = parts[2].trim();
            String value = parts[3].trim();
            String description = parts[4].trim();
            String purchaseDate = parts[5].trim();
            String expirationDate = parts[6].trim();

            // Populate the fields with the extracted information
            editAssetName.setText(assetName);
            editAssetCategory.setValue(category);
            editAssetLocation.setValue(location);
            editAssetValue.setText(value);
            editAssetDescription.setText(description);

            // Try to parse and set the purchase date and expiration date
            try {
                editAssetPurchaseDate.setValue(LocalDate.parse(purchaseDate));
                editAssetExp.setValue(LocalDate.parse(expirationDate));
            } catch (DateTimeParseException e) {
                // Handle parsing exception
                showAlert(Alert.AlertType.ERROR, "Error", "Error parsing date: " + e.getMessage());
            }
        } else {
            // Handle case where the selected asset string does not have enough elements
            showAlert(Alert.AlertType.ERROR, "Error", "Selected asset information is incomplete.");
        }
    }


    @FXML
    private void submitUpdate() {
        // Get the updated values from the UI elements
        String updatedName = editAssetName.getText();
        String updatedCategory = editAssetCategory.getValue();
        String updatedLocation = editAssetLocation.getValue();
        String updatedValue = editAssetValue.getText();
        String updatedDescription = editAssetDescription.getText();
        String updatedPurchaseDate = editAssetPurchaseDate.getValue().toString();
        String updatedExpirationDate = editAssetExp.getValue().toString();
        
        if (updatedName.isEmpty() || updatedCategory.isEmpty() || updatedLocation.isEmpty()) {
	        showAlert(Alert.AlertType.ERROR, "Error", "* fields cannot be empty.");
	    } else {
	    	// Read all lines from the CSV file except the one that matches the selected asset
	    	try {
	    		List<String> lines = Files.readAllLines(Paths.get("src/asset.csv"));
	    		StringBuilder updatedContent = new StringBuilder();
	    		for (String line : lines) {
	    			if (line.equals(selectedAsset)) {
	    				// Replace the fields of the selected asset with the updated values
	    				line = String.join(",", updatedName, updatedCategory, updatedLocation, updatedValue, updatedDescription, updatedPurchaseDate, updatedExpirationDate);
	    			}
	    			updatedContent.append(line).append("\n");
	    		}
            // Write the updated content back to the CSV file
            Files.write(Paths.get("src/asset.csv"), updatedContent.toString().getBytes());
            // Display success message
            showAlert("Success", "Asset Updated", "The asset has been successfully updated.");
	    	} catch (IOException e) {
	    		e.printStackTrace();
	    	}
	    }
    }


    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

	private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
    public void initialize() {
		// Populate the ComboBoxes with data from the CSV files
		String categoryFilePath = "src/categories.csv";
		String locationFilePath = "src/locations.csv";
    
        CSVReader.populateDropdownButton(editAssetCategory, categoryFilePath);
        CSVReader.populateDropdownButton(editAssetLocation, locationFilePath);
    }

}

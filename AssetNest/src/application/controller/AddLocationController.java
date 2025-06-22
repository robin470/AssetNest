package application.controller;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import javafx.scene.control.TextArea;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

//this controller controls the add location view and adds location in a csv file

public class AddLocationController {
	
	@FXML TextField locationName;
	@FXML TextArea locationDescription;
	@FXML Button submitLocation;
	
	private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

	@FXML public void submitLocation() {
		String name = locationName.getText().trim();
        String description = locationDescription.getText().trim();

        if (name.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Location name cannot be empty.");
        } else {
        	try {
        		// Save to CSV file
                saveToCSV(name, description);
                showAlert(Alert.AlertType.INFORMATION, "Success", "Location saved successfully.");
                // Clear the text field after saving
                locationName.clear();
                locationDescription.clear();
            }catch(IOException e){
            	showAlert(Alert.AlertType.ERROR, "Error", "Failed to save category to CSV file.");
            }
        }
	}
	private void saveToCSV(String locationName, String locationDescription) throws IOException {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/locations.csv", true))) {
            // Append the location name and description to the CSV file
            writer.write(locationName + "" +locationDescription);
            writer.newLine();
        }
    }
}

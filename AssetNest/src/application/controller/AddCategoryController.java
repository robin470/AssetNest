package application.controller;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class AddCategoryController {

	@FXML TextField categoryName;
	
	private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

	@FXML public void submitCategory() {
        String name = categoryName.getText().trim();

        if (name.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Error", "Category name cannot be empty.");
        } else {
            try {
                // Save to CSV file
                saveToCSV(name);
                showAlert(Alert.AlertType.INFORMATION, "Success", "Category saved successfully.");
                // Clear the text field after saving
                categoryName.clear();
            } catch (IOException e) {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to save category to CSV file.");
            }
        }
    }
	private void saveToCSV(String categoryName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/categories.csv", true))) {
            // Append the category name to the CSV file
            writer.write(categoryName);
            writer.newLine();
        }
    }
}
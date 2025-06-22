package model;
import java.io.BufferedReader;
import java.io.FileReader;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

public class CSVReader {
	public static void populateDropdownButton(ComboBox<String> dropdownButton, String csvFilePath) {
        
		ObservableList<String> items = FXCollections.observableArrayList();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Add each line as an item to the drop down button
                items.add(line.trim()); // Trim to remove leading/trailing white spaces
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Set the items list to the drop down button
        dropdownButton.setItems(items);
    }
}

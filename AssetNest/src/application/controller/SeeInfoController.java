package application.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SeeInfoController {
	@FXML Label nameInfo;

    @FXML Label valueInfo;

    @FXML Label categoryInfo;

    @FXML Label locationInfo;

    @FXML Label descriptionInfo;

    @FXML Label purchaseInfo;

    @FXML Label warrantyInfo;

    public void setAssetInfo(String selectedAsset) {
        // Split the selected asset string to extract its information
        String[] parts = selectedAsset.split(",");
        if (parts.length >= 7) { // Check if there are enough fields
            String name = parts[0].trim();
            String category = parts[1].trim();
            String location = parts[2].trim();
            String value = parts[3].trim();
            String description = parts[4].trim();
            String purchaseDate = parts[5].trim();
            String warrantyExpiration = parts[6].trim();
            // Set the asset information in the see info screen
            nameInfo.setText(name);
            valueInfo.setText(value);
            categoryInfo.setText(category);
            locationInfo.setText(location);
            descriptionInfo.setText(description);
            purchaseInfo.setText(purchaseDate);
            warrantyInfo.setText(warrantyExpiration);
        } else {
        	 // Handle incomplete asset information
            nameInfo.setText("N/A");
            valueInfo.setText("N/A");
            categoryInfo.setText("N/A");
            locationInfo.setText("N/A");
            descriptionInfo.setText("N/A");
            purchaseInfo.setText("N/A");
            warrantyInfo.setText("N/A");
            System.err.println("Incomplete asset information: " + selectedAsset);
        }
    }
}

package application.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class SearchManager {

    // Method to search assets based on a keyword, category, and location in a CSV file

    public static List<String> searchAssets(String csvFilePath, String keyword, String category, String location) {
        List<String> searchResults = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Split the line by comma to extract asset name, category, location, and warranty expiration date
                String[] parts = line.split(",");
                if (parts.length >= 3) { // Ensure that the line contains at least category, location, and warranty expiration date
                    String assetName = parts[0].trim();
                    String assetCategory = parts[1].trim();
                    String assetLocation = parts[2].trim();
                    //String warrantyExpirationDate = parts[6].trim();

                    // Check if the asset matches the selected category and location
                    if ((category == null || assetCategory.equals(category)) && 
                        (location == null || assetLocation.equals(location)) &&
                        (keyword.isEmpty() || assetName.toLowerCase().contains(keyword.toLowerCase()))) {
                        // Construct the asset info string with required fields
                        
                        searchResults.add(line.trim()); // Add the asset info to search results
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return searchResults;
    }
}
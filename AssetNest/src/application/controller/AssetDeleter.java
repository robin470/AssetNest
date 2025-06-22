package application.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class AssetDeleter {

	public static void deleteAsset(String selectedAsset) {
		
		try {
            // Read all lines from the CSV file except the one that matches the selected asset
            List<String> updatedLines = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new FileReader("src/asset.csv"))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    if (!line.equals(selectedAsset)) {
                        updatedLines.add(line);
                    }
                }
            }

            // Write the updated content back to the CSV file
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/asset.csv"))) {
                for (String updatedLine : updatedLines) {
                    writer.write(updatedLine);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
	}

}

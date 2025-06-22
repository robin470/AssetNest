package application.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class MainController {
	
	@FXML HBox mainBox;
	
	@FXML
	public void initialize() {
		
		String csvFilePath = "src/asset.csv"; // Path to your CSV file
		boolean expiredWarranties = checkExpiredWarranties(csvFilePath);
		if (expiredWarranties) {
			showNoticeOp();
		} else {
			showHomeOp();
		}
		
	}
	
	public static boolean checkExpiredWarranties(String csvFile) {
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 7) { // Make sure the array has at least 7 elements
                    String expiryDateString = parts[6].trim(); // Seventh part (index 6) contains the expiration date
                    try {
                        LocalDate expiryDate = LocalDate.parse(expiryDateString);
                        if (expiryDate.isBefore(LocalDate.now())) {
                            System.out.println("Warning: Found an expired warranty.");
                            return true; // Found an expired warranty
                        }
                    } catch (DateTimeParseException e) {
                        // Ignore parsing errors for parts that are not valid dates
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false; // No expired warranties found
    }

	@FXML public void showNoticeOp() {
		
		URL url = getClass().getClassLoader().getResource("view/Notice.fxml");
		
		try {
			AnchorPane pane = (AnchorPane) FXMLLoader.load(url);
			
			if (mainBox.getChildren().size() > 1) {
				mainBox.getChildren().remove(1);
			}
			
			mainBox.getChildren().add(pane);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@FXML public void showHomeOp() {
		
		URL url = getClass().getClassLoader().getResource("view/Home.fxml");
		
		try {
			AnchorPane pane = (AnchorPane) FXMLLoader.load(url);
			
			if (mainBox.getChildren().size() > 1) {
				mainBox.getChildren().remove(1);
			}
			
			mainBox.getChildren().add(pane);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@FXML public void showAddCategoryOp() {
		
		URL url = getClass().getClassLoader().getResource("view/AddCategory.fxml");
		
		try {
			AnchorPane pane = (AnchorPane) FXMLLoader.load(url);
			
			if (mainBox.getChildren().size() > 1) {
				mainBox.getChildren().remove(1);
			}
			
			mainBox.getChildren().add(pane);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@FXML public void showAddLocationOp() {
		
		URL url = getClass().getClassLoader().getResource("view/AddLocation.fxml");
		
		try {
			AnchorPane pane = (AnchorPane) FXMLLoader.load(url);
			
			if (mainBox.getChildren().size() > 1) {
				mainBox.getChildren().remove(1);
			}
			
			mainBox.getChildren().add(pane);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@FXML public void showAddAssetOp() {
		
		URL url = getClass().getClassLoader().getResource("view/AddAsset.fxml");
		
		try {
			AnchorPane pane = (AnchorPane) FXMLLoader.load(url);
			
			if (mainBox.getChildren().size() > 1) {
				mainBox.getChildren().remove(1);
			}
			
			mainBox.getChildren().add(pane);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@FXML public void showSearchOp() {
		
		URL url = getClass().getClassLoader().getResource("view/Search.fxml");
		
		try {
			AnchorPane pane = (AnchorPane) FXMLLoader.load(url);
			
			if (mainBox.getChildren().size() > 1) {
				mainBox.getChildren().remove(1);
			}
			
			mainBox.getChildren().add(pane);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML public void showWarrantiesOp() {
		
		URL url = getClass().getClassLoader().getResource("view/Warranties.fxml");
		
		try {
			AnchorPane pane = (AnchorPane) FXMLLoader.load(url);
			
			if (mainBox.getChildren().size() > 1) {
				mainBox.getChildren().remove(1);
			}
			
			mainBox.getChildren().add(pane);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}

package com.cookiecoders.gamearcade.util;
import com.cookiecoders.gamearcade.config.ConfigManager;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.io.ByteArrayInputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


public class Utils {
    public static byte[] imageToByteArray(String filePath) {
        byte[] byteArray = null;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            byteArray = new byte[(int) file.length()];
            fis.read(byteArray);
            fis.close();
        }catch(Exception e){
            System.out.println(e);
        }
        return byteArray;
    }

    public static void byteArraytoImage(byte[] imageBytes, String imageName, String saveLocation) {
        try {
            // Convert saveLocation to URL
            URL saveURL = new URL(saveLocation);

            // Create directory if it doesn't exist
            File directory = new File(saveURL.getPath());
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Convert byte array to BufferedImage
            ByteArrayInputStream bais = new ByteArrayInputStream(imageBytes);
            BufferedImage image = ImageIO.read(bais);

            // Write the BufferedImage to a file
            File outputFile = new File(directory, imageName);
            String formatName = imageName.substring(imageName.lastIndexOf(".") + 1);
            ImageIO.write(image, formatName, outputFile);

            System.out.println("Image written to " + outputFile.getAbsolutePath());

        } catch (MalformedURLException e) {
            System.err.println("Invalid URL: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error writing image: " + e.getMessage());
        }
    }

    public static void saveFile(File file, String relativeFilePath){
        try {
            // Get the absolute path to the resources directory
            Path resourcesDirectory = Paths.get("src/main/resources").toAbsolutePath();

            // Combine with the relative file path
            Path fullPath = Paths.get(resourcesDirectory.toString(), relativeFilePath).normalize();

            // Extract the directory and file name
            Path targetDirectory = fullPath.getParent();
            Path targetFile = fullPath.getFileName();

            // Ensure the target directory exists
            if (!Files.exists(targetDirectory)) {
                Files.createDirectories(targetDirectory);
            }

            // Create the target file path
            Path targetFilePath = targetDirectory.resolve(targetFile);

            // Copy the file to the target directory
            Files.copy(file.toPath(), targetFilePath, StandardCopyOption.REPLACE_EXISTING);

            System.out.println("File saved to: " + targetFilePath.toString());
        } catch (IOException e) {
            e.printStackTrace();
            Logger.getInstance().log(Logger.LogLevel.ERROR, "Failed to save the file: " + e.getMessage());
        }
    }

    public static String getFileExtension(String fileUriString) {
        // Find the last occurrence of the dot
        int dotIndex = fileUriString.lastIndexOf('.');

        // Find the last occurrence of the slash
        int slashIndex = fileUriString.lastIndexOf('/');

        // Check if the dot is after the last slash and not at the end
        if (dotIndex > slashIndex && dotIndex < fileUriString.length() - 1) {
            // Extract and return the file extension
            return fileUriString.substring(dotIndex + 1);
        }

        // If no valid extension is found, return an empty string
        return "";
    }

}

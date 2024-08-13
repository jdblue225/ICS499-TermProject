package com.cookiecoders.gamearcade.util;
import com.cookiecoders.gamearcade.config.ConfigManager;
import com.cookiecoders.gamearcade.database.models.User;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.*;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.awt.Graphics2D;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritablePixelFormat;

import java.io.ByteArrayOutputStream;
import java.io.IOException;



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

    public static byte[] imageToByteArray(Image image) {
        int width = (int) image.getWidth();
        int height = (int) image.getHeight();

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        PixelReader pixelReader = image.getPixelReader();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int argb = pixelReader.getArgb(x, y);
                bufferedImage.setRGB(x, y, argb);
            }
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(bufferedImage, "jpg", baos);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return baos.toByteArray();
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

    public static BufferedImage byteArrayToImage(byte[] imageBytes) {
        BufferedImage image = null;
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(imageBytes);
            image = ImageIO.read(bais);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public static void saveImageAsJpg(BufferedImage image, String relativeOutputPath) {
        try {
            // Attempt to locate the resource
            URL resourceUrl = Utils.class.getResource(relativeOutputPath);
            File outputFile;

            if (resourceUrl != null) {
                // If the resource exists, convert the URL to a File object
                outputFile = new File(resourceUrl.toURI());
            } else {
                // If the resource does not exist, create a new file in the resources directory
                outputFile = new File("src/main/resources" + relativeOutputPath); // Adjust this base path as needed.
            }

            // Ensure the parent directories exist
            File parentDir = outputFile.getParentFile();
            if (parentDir != null && !parentDir.exists()) {
                parentDir.mkdirs();
            }

            // Write the BufferedImage to the output file as a JPG
            ImageIO.write(image, "jpg", outputFile);
            System.out.println("Image saved successfully at: " + outputFile.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean downladUserData(User user) {
        if (user.getImage() != null){
            // profile Image saved to drive
            String username = user.getUsername();
            String rootPath = ConfigManager.getProperty("root_path");
            String profImageDir = ConfigManager.getProperty("prof_image_path");
            String profImagePath = rootPath + profImageDir + username + ".jpg";
            BufferedImage profImage = byteArrayToImage(user.getImage());
            saveImageAsJpg(profImage, profImagePath);
            return true;
        }else {
            return false;
        }
    }

}

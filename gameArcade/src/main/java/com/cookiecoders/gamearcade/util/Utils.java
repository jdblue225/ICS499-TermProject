package com.cookiecoders.gamearcade.util;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.io.ByteArrayInputStream;
import java.net.MalformedURLException;


public class Utils {
    public static byte[] imageToByteArray(String filePath) throws IOException {
        File file = new File(filePath);
        FileInputStream fis = new FileInputStream(file);
        byte[] byteArray = new byte[(int) file.length()];
        fis.read(byteArray);
        fis.close();
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
}

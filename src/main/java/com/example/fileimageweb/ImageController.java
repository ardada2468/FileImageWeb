package com.example.fileimageweb;

import com.example.FileUtils.FileCreate;
import com.example.FileUtils.FileReader;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

// ... (other imports)

@RestController
@RequestMapping("/api")
public class ImageController {

    @PostMapping("/processImage")
    public ResponseEntity<byte[]> processImage(@RequestParam("file") MultipartFile file) {
        try {
            // Save the uploaded file to a temporary location
//            String filePath = ".\\" + file.getOriginalFilename();
//            file.transferTo(new File(filePath));

            // Read data from the saved file using FileReader
            byte[] fileData = file.getBytes();

            // Create an image using FileCreate
            BufferedImage generatedImage = new FileCreate().createImg(fileData, file.getOriginalFilename());
            ImageIO.write(generatedImage, "png", new File("./img/"));
            // Convert BufferedImage to byte array
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(generatedImage, "png", baos);
            baos.flush();
            byte[] imageBytes = baos.toByteArray();
            baos.close();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_PNG);
            headers.setContentLength(imageBytes.length);
            headers.setContentDispositionFormData("attachment", "processed_image.png");
            System.out.println("Process complete");
            return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exception appropriately (e.g., return an error response)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PostMapping("/processImageDecrypt")
    public ResponseEntity<byte[]> processImageDecrypt(@RequestParam("file") MultipartFile file) {
        try {
            System.out.println("Decrypt Started");
            // Save the uploaded file to a temporary location
//            String filePath = ".\\" + file.getOriginalFilename();
//            file.transferTo(new File(filePath));

            // Read data from the saved file using FileReader
            byte[] fileData = file.getBytes();
            BufferedImage image = ImageIO.read(file.getInputStream());

            byte[] data = FileReader.getData(image);

            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.);
            headers.setContentLength(data.length);
            headers.setContentDispositionFormData("attachment", "processed_file_change_name_to_original_file_type");
            System.out.println("Process complete");
            System.out.println("Decrypt Finsihed");
            return new ResponseEntity<>(data, headers, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            // Handle exception appropriately (e.g., return an error response)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}

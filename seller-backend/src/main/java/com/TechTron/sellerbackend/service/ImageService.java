package com.TechTron.sellerbackend.service;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class ImageService{

    @Autowired
    private AmazonS3 s3Client;
    //Provides methods for various S3 operations like uploading, downloading, listing, and managing objects (files) in S3 buckets.


    //MultipartFile Represents a file uploaded through a web form.
    //Contains additional information besides the data of the actual file, such as filename, content type, and headers.
    //By converting to a File object, you extract only the essential data needed for upload, which is the raw file content.
    //This simplifies the upload process and ensures compatibility with the S3 SDK.

    // Deleting the temporary file frees up disk space on the server.

    //dcd

    String fileName; // for returning the url

    @Transactional
    public String uploadFile(final MultipartFile multipartFile) {

        String contentType = multipartFile.getContentType();
        System.out.println("Content type is == "+contentType);

        if(contentType.equals("image/png") || contentType.equals("image/jpeg")){

            try {
                final File file = convertMultiPartFileToFile(multipartFile);
                fileName = uploadFileToS3Bucket("sellerproductimages", file);
                file.deleteOnExit();  // To remove the file locally created in the project folder.
//            file.delete();

            } catch (final AmazonServiceException ex) {

                System.out.println("Error while uploading file = "+ex.getMessage());
            }

            return String.format("https://s3.%s.amazonaws.com/%s/%s", "ap-south-1", "sellerproductimages", fileName);

        }
        else{
            System.out.println("Invalid Content Type");
            return "Invalid Content Type";
        }
    }

    //Use FileOutputStream whenever you need to write byte-oriented data to a file (like images, documents, or raw data streams) to a file.

    //Imagine a pipe (the FileOutputStream) that allows you to send data (the file content) to a specific destination (the temporary file).
    //This line creates that pipe connected to the newly created File object (the destination).

    //Think of the multipartFile.getBytes() method as gathering all the building blocks (bytes) that make up the uploaded file.
    //This line takes those building blocks and pushes them through the pipe (the outputStream) one by one.
    //As the building blocks go through the pipe, they get written to the temporary file on the server, essentially reconstructing the file there.

    //Extracts the raw data from the uploaded file and creates a temporary copy of it on the server's file system using the FileOutputStream for writing the data.

    private File convertMultiPartFileToFile(final MultipartFile multipartFile) {

        final File file = new File(multipartFile.getOriginalFilename());

        try (final FileOutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(multipartFile.getBytes());
        }
        catch (final IOException ex) {
            System.out.println("Error converting the multi-part file to file= "+ex.getMessage());
        }
        return file;
    }

    //The uploadFileToS3Bucket method handles the actual upload of the converted File object to Amazon S3.
    //PutObjectRequest object, which is used to specify the details of the upload operation to the S3 SDK.
    //The constructor takes three arguments:

    //      bucketName: The name of the S3 bucket where the file will be uploaded.
    //      uniqueFileName: The unique filename generated in the previous step.
    //      file: The File object containing the data to be uploaded.

    //This line calls the putObject method on the injected s3Client object.
    //The putObject method takes the PutObjectRequest object created earlier as an argument.
    //This method initiates the upload process, sending the data from the File object to the specified S3 bucket using the unique filename.

    @Transactional
    private String uploadFileToS3Bucket(final String bucketName, final File file) {

//        final String uniqueFileName = LocalDateTime.now() + "_" + file.getName();
        final String uniqueFileName = file.getName();

        final PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, uniqueFileName, file);
        s3Client.putObject(putObjectRequest);

        return uniqueFileName;
    }
}

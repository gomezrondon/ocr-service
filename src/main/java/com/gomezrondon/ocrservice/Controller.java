package com.gomezrondon.ocrservice;


import com.google.cloud.ReadChannel;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.stream.Stream;

@RestController
@CrossOrigin(origins = {"${settings.cors_origin}"})
public class Controller {


    @Value("${gcs-resource-test-bucket}")
    private String bucketName;

    private final Storage storage;

    public Controller(Storage storage) {
        this.storage = storage;



    }

    @GetMapping("/time")
    public String getTime(){
        return LocalDateTime.now().toString();
    }


    @PostMapping("/upload-file")
    public void uploadFile(@RequestParam("file") MultipartFile file ) throws IOException {
        String filename = file.getOriginalFilename();
        String filePath = "data/" + filename;
        file.transferTo(new File(filePath));
        System.out.println("Post request executed! "+filename);
    }

    @PostMapping("/upload-file/gcp")
    public void uploadFileGCP(@RequestParam("file") MultipartFile file ) throws IOException {
        String filename = file.getOriginalFilename();
        String filePath = "data/" + filename;
        file.transferTo(new File(filePath));
        this.upload(filePath, filename);
        System.out.println("uploaded to gcp! "+filename);
    }

/*    @GetMapping("/download-file")
    public void downloaddFile() {
        this.download("data/eng.traineddata");
        System.out.println("download executed! " );
    }*/

    @GetMapping("/v1/text/{image}")
    public String getTextFromImage(@PathVariable String image) throws TesseractException, IOException {
        var tesseract = new Tesseract();
        tesseract.setDatapath("data");
        File file = new File("data/" + image);
        String text = "file NO EXISTE";
        if (file.exists()) {
             tesseract.doOCR(file);
        }

        return text;
    }

    /**
     * Upload file to a gcp bucket
     * @param filePath path of the file
     * @throws IOException
     */
    public void upload(String filePath, String objectName) throws IOException {
        BlobId blobId = BlobId.of(bucketName, objectName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
        storage.create(blobInfo, Files.readAllBytes(Paths.get(filePath)));

        System.out.println("File " + filePath + " uploaded to bucket " + bucketName + " as " + objectName);
    }



}

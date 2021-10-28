package com.gomezrondon.ocrservice;



import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

@RestController
@CrossOrigin(origins = {"${settings.cors_origin}"})
public class Controller {


    @Value("${gcs-resource-test-bucket}")
    private String bucketName;

    private final Tesseract tesseract;

    public Controller(  Tesseract tesseract) {
        this.tesseract = tesseract;
    }

    @GetMapping("/time")
    public String getTime(){
        return LocalDateTime.now().toString();
    }


    @PostMapping("/upload-file/{language}")
    public String uploadFile(@RequestParam("file") MultipartFile file , @PathVariable String language) throws IOException, TesseractException {
        String filename = file.getOriginalFilename();
        String filePath = "data/" + filename;
        file.transferTo(new File(filePath));
        System.out.println("Post request executed! "+filename);
        //-----
        tesseract.setLanguage(language); // spa, eng
        File file2 = new File(filePath);
        String text = tesseract.doOCR(file2);
        System.out.println(text);
        return text;
    }

/*    @PostMapping("/upload-file/gcp")
    public void uploadFileGCP(@RequestParam("file") MultipartFile file ) throws IOException {
        String filename = file.getOriginalFilename();
        String filePath = "data/" + filename;
        file.transferTo(new File(filePath));
        this.upload(filePath, filename);
        System.out.println("uploaded to gcp! "+filename);
    }*/

/*    @GetMapping("/download-file")
    public void downloaddFile() {
        this.download("data/eng.traineddata");
        System.out.println("download executed! " );
    }*/

    @GetMapping("/v1/text/{language}/{image}")
    public String getTextFromImage(@PathVariable String language, @PathVariable String image) throws TesseractException {
/*        var tesseract = new Tesseract();
        tesseract.setDatapath("/usr/share/tessdata/");*/
        tesseract.setLanguage(language); // spa, eng
        File file = new File("data/" + image);
        String text = tesseract.doOCR(file);
        System.out.println(text);
        return text;
    }

    /**
     * Upload file to a gcp bucket
     * @param filePath path of the file
     * @throws IOException
     */
/*    public void upload(String filePath, String objectName) throws IOException {
        BlobId blobId = BlobId.of(bucketName, objectName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).build();
        storage.create(blobInfo, Files.readAllBytes(Paths.get(filePath)));

        System.out.println("File " + filePath + " uploaded to bucket " + bucketName + " as " + objectName);
    }*/



}

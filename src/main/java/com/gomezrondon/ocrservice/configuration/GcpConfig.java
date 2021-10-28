package com.gomezrondon.ocrservice.configuration;


import net.sourceforge.tess4j.Tesseract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;


@Configuration
public class GcpConfig {


/*    @Bean
    @Profile("gcp")
    public Storage getGCPStorage() throws IOException {
        return StorageOptions.getDefaultInstance().getService();
    }


    @Bean
    @Profile("!gcp")
    public Storage getStorage() throws IOException {
        Resource resource = new ClassPathResource("mycreds.json");
        InputStream inputStream = resource.getInputStream();

        GoogleCredentials credentials = ServiceAccountCredentials.fromStream(inputStream);

        return StorageOptions.newBuilder()
                .setCredentials(credentials)
                .build()
                .getService();
    }*/

    @Bean
    @Profile("local")
    public Tesseract getTesseractLocal() {
        var tesseract = new Tesseract();
        tesseract.setDatapath("tessdata");
        return tesseract;
    }


    @Bean
    @Profile("!local")
    public Tesseract getTesseract() {
        var tesseract = new Tesseract();
        tesseract.setDatapath("/usr/share/tessdata/");
        return tesseract;
    }

}

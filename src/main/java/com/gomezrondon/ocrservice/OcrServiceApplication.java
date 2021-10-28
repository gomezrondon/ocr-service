package com.gomezrondon.ocrservice;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.nativex.hint.NativeHint;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


//@NativeHint(options = "--enable-http --enable-https")
@SpringBootApplication
public class OcrServiceApplication implements CommandLineRunner {

/*	@Value("${gcs-resource-test-bucket}")
	private String bucketName;

	private final Storage storage;

	public OcrServiceApplication(Storage storage) {
		this.storage = storage;
	}*/

	public static void main(String[] args) {
		SpringApplication.run(OcrServiceApplication.class, args);
	}

	@Override
	public void run(String... args)  {
//		System.out.println(">>>>> 2" );
		createDirectory("data");
/*

		String filePath = "data/eng.traineddata";
		File file = new File(filePath);
		if (!file.exists()) {
			download(filePath);
			System.out.println("download executed! " );
		}*/

	}

/*	public void download(String filePath) {
		Blob blob = storage.get(bucketName, "eng.traineddata");
		blob.downloadTo(Paths.get(filePath));
		System.out.println("File " + filePath + " donwloaded from bucket " + bucketName + " as " );
	}*/

	private void createDirectory(String folderName) {
		String currentDir = System.getProperty("user.dir");
		String separator = System.getProperty("file.separator");
		String directory = currentDir + separator + folderName + separator;

		Path path = Paths.get(directory);
		if (!Files.exists(path)) {
			try {
				Files.createDirectories(path);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

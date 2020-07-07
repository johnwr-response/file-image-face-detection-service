package no.responseweb.imagearchive.imagefacedetection;

import lombok.extern.slf4j.Slf4j;
import nu.pattern.OpenCV;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@Slf4j
@SpringBootApplication
@EnableFeignClients
public class ImageFaceDetectionApplication {

	public static void main(String[] args) {
		SpringApplication.run(ImageFaceDetectionApplication.class, args);
		OpenCV.loadShared();
	}

}



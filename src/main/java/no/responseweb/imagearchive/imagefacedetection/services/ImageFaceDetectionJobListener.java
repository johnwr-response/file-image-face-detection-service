package no.responseweb.imagearchive.imagefacedetection.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import no.responseweb.imagearchive.imagefacedetection.config.JmsConfig;
import no.responseweb.imagearchive.model.ImageFaceDetectionJobDto;
import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.objdetect.Objdetect;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class ImageFaceDetectionJobListener {

    private final FileStoreFetcherService fileStoreFetcherService;

    @JmsListener(destination = JmsConfig.IMAGE_FACE_DETECTION_JOB_QUEUE)
    public void listen(ImageFaceDetectionJobDto imageFaceDetectionJobDto) throws IOException {
        log.info("Called with: {}", imageFaceDetectionJobDto);
        if (imageFaceDetectionJobDto.getFileItemId()!=null) {
            byte[] downloadedBytes = fileStoreFetcherService.fetchFile(imageFaceDetectionJobDto.getFileItemId());
            BufferedImage image = ImageIO.read(new ByteArrayInputStream(downloadedBytes));
            if (image!=null) {
                Imgcodecs imageCodecs = new Imgcodecs();
                Mat loadedImage = imageCodecs.imread("");
                MatOfRect facesDetected = new MatOfRect();
                CascadeClassifier cascadeClassifier = new CascadeClassifier();
                int minFaceSize = Math.round(loadedImage.rows() * 0.1f);
                cascadeClassifier.load("./src/main/resources/haarcascades/haarcascade_frontalface_alt.xml");
                cascadeClassifier.detectMultiScale(loadedImage,
                        facesDetected,
                        1.1,
                        3,
                        Objdetect.CASCADE_SCALE_IMAGE,
                        new Size(minFaceSize, minFaceSize),
                        new Size()
                );
                Rect[] facesArray = facesDetected.toArray();
                for(Rect face : facesArray) {
                    Imgproc.rectangle(loadedImage, face.tl(), face.br(), new Scalar(0, 0, 255), 3);
                }
                // saveImage(loadedImage, "targetImagePath");
            }

        }
    }
}

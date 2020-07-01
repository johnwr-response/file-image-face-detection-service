package no.responseweb.imagearchive.imagefacedetection.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import no.responseweb.imagearchive.imagefacedetection.config.JmsConfig;
import no.responseweb.imagearchive.model.ImageFaceDetectionJobDto;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class ImageFaceDetectionJobListener {
    @JmsListener(destination = JmsConfig.IMAGE_FACE_DETECTION_JOB_QUEUE)
    public void listen(ImageFaceDetectionJobDto imageFaceDetectionJobDto) {
        log.info("Called with: {}", imageFaceDetectionJobDto);
    }
}

package no.responseweb.imagearchive.imagefacedetection.services;

import java.util.UUID;

public interface FileStoreFetcherService {
    byte[] fetchFile(UUID fileItemId);
}

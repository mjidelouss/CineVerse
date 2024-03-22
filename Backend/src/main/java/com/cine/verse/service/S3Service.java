package com.cine.verse.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface S3Service {
    String uploadFile(String folderName, MultipartFile file);
}
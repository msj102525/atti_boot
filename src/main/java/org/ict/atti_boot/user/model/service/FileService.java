package org.ict.atti_boot.user.model.service;

import org.ict.atti_boot.user.jpa.entity.File;
import org.ict.atti_boot.user.jpa.entity.User;
import org.ict.atti_boot.user.jpa.repository.FileRepository;
import org.ict.atti_boot.user.jpa.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
public class FileService {
    private final String UPLOAD_DIR = "uploads/";

    private final UserRepository userRepository;
    private final FileRepository fileRepository;

    @Autowired
    public FileService(UserRepository userRepository, FileRepository fileRepository) {
        this.userRepository = userRepository;
        this.fileRepository = fileRepository;
    }

    public File saveFile(MultipartFile file, String userId) throws IOException {
        // 파일 저장 경로 설정
        byte[] bytes = file.getBytes();
        Path path = Paths.get(UPLOAD_DIR + "/" + file.getOriginalFilename());
        Files.write(path, bytes);

        // 파일 정보 저장
        File fileEntity = new File();
        fileEntity.setFileName(file.getOriginalFilename());
        fileEntity.setFilePath(path.toString());
        fileEntity.setFileType(file.getContentType());
        fileEntity.setFileSize(file.getSize());

        File savedFile = fileRepository.save(fileEntity);

        // 사용자 프로필 URL 업데이트
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setProfileUrl("/" + path.toString()); // 상대 경로로 설정
            userRepository.save(user);
        }

        return savedFile;
    }
}

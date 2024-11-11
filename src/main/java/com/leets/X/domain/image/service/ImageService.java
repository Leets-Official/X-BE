package com.leets.X.domain.image.service;

import com.leets.X.domain.image.domain.Image;
import com.leets.X.domain.image.dto.request.ImageDto;
import com.leets.X.domain.image.repository.ImageRepository;
import com.leets.X.domain.post.domain.Post;
import com.leets.X.domain.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageUploadService imageUploadService;
    private final ImageRepository imageRepository;

    @Transactional
    public List<Image> save(List<MultipartFile> file, Post post) throws IOException {
        List<ImageDto> dtoList = imageUploadService.uploadImages(file);

        List<Image> imageList = dtoList.stream()
                .map((ImageDto dto) -> Image.from(dto, post))
                .toList();

        return imageRepository.saveAll(imageList);
    }

    @Transactional
    public Image save(MultipartFile image, User user) throws IOException {
        ImageDto imageDto = imageUploadService.uploadImage(image);
        return imageRepository.save(Image.from(imageDto, user));
    }

    public ImageDto getImage(MultipartFile image) throws IOException {
        return imageUploadService.uploadImage(image);
    }

    @Transactional
    public void delete(Image image) {
        imageRepository.delete(image);
    }

}

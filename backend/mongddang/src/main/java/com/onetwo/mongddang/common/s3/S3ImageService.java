package com.onetwo.mongddang.common.s3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;
import com.onetwo.mongddang.common.s3.errors.CustomS3ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class S3ImageService {
    private final AmazonS3 amazonS3;
    private final Logger logger = LoggerFactory.getLogger(S3ImageService.class);
    @Value("${cloud.aws.region.static}")
    private String region;
    @Value("${cloud.aws.s3.bucketName}")
    private String bucketName;

    public String upload(MultipartFile image) {
        log.info("복약 이미지 파일을 S3에 업로드 시도 (in english : Try to upload meal image file to S3)");

        if (image.isEmpty() || Objects.isNull(image.getOriginalFilename())) {
            throw new RuntimeException(CustomS3ErrorCode.EMPTY_FILE_EXCEPTION.getMessage());
        }

        log.info("복약 이미지 파일을 S3에 업로드 완료 (in english : Meal image file uploaded to S3)");

        return this.uploadImage(image);
    }

    private String uploadImage(MultipartFile image) {
        log.info("uploadImage 메서드 실행");

        this.validateImageFileExtention(image.getOriginalFilename());
        try {
            return this.uploadImageToS3(image);
        } catch (IOException e) {
            throw new RuntimeException(CustomS3ErrorCode.IO_EXCEPTION_ON_IMAGE_UPLOAD.getMessage());
        }
    }

    private void validateImageFileExtention(String filename) {
        int lastDotIndex = filename.lastIndexOf(".");
        if (lastDotIndex == -1) {
            throw new RuntimeException(CustomS3ErrorCode.NO_FILE_EXTENTION.getMessage());
        }
        String extention = filename.substring(lastDotIndex + 1).toLowerCase();
        List<String> allowedExtentionList = Arrays.asList("jpg", "jpeg", "png", "gif");

        if (!allowedExtentionList.contains(extention)) {
            throw new RuntimeException(CustomS3ErrorCode.INVALID_FILE_EXTENTION.getMessage());
        }
    }

    // 유효한 이미지 확장자 검사 함수
    private boolean isValidImageExtension(String extension) {
        // 허용된 이미지 확장자 목록
        String[] validExtensions = {"jpg", "jpeg", "png", "gif", "bmp"};
        return Arrays.asList(validExtensions).contains(extension.toLowerCase());
    }

    private String uploadImageToS3(MultipartFile image) throws IOException {
        log.info("uploadImageToS3 메서드 실행 (in English : upload image to S3)");

        String originalFilename = image.getOriginalFilename(); //원본 파일 명
        String extention = originalFilename.substring(originalFilename.lastIndexOf(".")); //확장자 명

        String s3FileName = UUID.randomUUID().toString().substring(0, 10) + originalFilename; //변경된 파일 명

        InputStream is = image.getInputStream();
        byte[] bytes = IOUtils.toByteArray(is); //image를 byte[]로 변환

        ObjectMetadata metadata = new ObjectMetadata(); //metadata 생성
        metadata.setContentType("image/" + extention);
        metadata.setContentLength(bytes.length);
        log.info("metadata 생성 완료 (in English : metadata created)");

        //S3에 요청할 때 사용할 byteInputStream 생성
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        log.info("byteArrayInputStream 생성 완료 (in English : byteArrayInputStream created)");

        try {
            //S3로 putObject 할 때 사용할 요청 객체
            //생성자 : bucket 이름, 파일 명, byteInputStream, metadata
            PutObjectRequest putObjectRequest =
                    new PutObjectRequest(bucketName, s3FileName, byteArrayInputStream, metadata)
                            .withCannedAcl(CannedAccessControlList.PublicRead);

            log.info("putObject 실행 (in English : putObject executed)");
            //실제로 S3에 이미지 데이터를 넣는 부분이다.
            amazonS3.putObject(putObjectRequest); // put image to S3
        } catch (Exception e) {
            log.error(String.valueOf(e));
            throw new RuntimeException(CustomS3ErrorCode.PUT_OBJECT_EXCEPTION.getMessage());
        } finally {
            byteArrayInputStream.close();
            is.close();
        }

        return amazonS3.getUrl(bucketName, s3FileName).toString();
    }

    public void deleteImageFromS3(String imageAddress) {
        String key = getKeyFromImageAddress(imageAddress);
        try {
            amazonS3.deleteObject(new DeleteObjectRequest(bucketName, key));
        } catch (Exception e) {
            throw new RuntimeException(CustomS3ErrorCode.IO_EXCEPTION_ON_IMAGE_DELETE.getMessage());
        }
    }

    private String getKeyFromImageAddress(String imageAddress) {
        try {
            URL url = new URL(imageAddress);
            String decodingKey = URLDecoder.decode(url.getPath(), "UTF-8");
            return decodingKey.substring(1); // 맨 앞의 '/' 제거
        } catch (MalformedURLException | UnsupportedEncodingException e) {
            throw new RuntimeException(CustomS3ErrorCode.IO_EXCEPTION_ON_IMAGE_DELETE.getMessage());
        }
    }

}

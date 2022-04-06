package com.mfp.resource.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamSource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Component
public class ResourceRepository {

    @Value("${resource.s3.bucket-name}")
    private String bucketName;

    @Autowired()
    private AmazonS3 s3Client;

    public void uploadResource(Long id, InputStreamSource source) {
        try (InputStream inputStream = source.getInputStream()) {
            s3Client.putObject(bucketName, id.toString(), inputStream, new ObjectMetadata());
        } catch (IOException e) {
            throw new ResourceRepositoryException("Error while uploading resource", e);
        }
    }

    public byte[] getResourceData(Long resourceId) {
        S3Object s3Object = s3Client.getObject(bucketName, resourceId.toString());
        try (S3ObjectInputStream inputStream = s3Object.getObjectContent()) {
            return inputStream.readAllBytes();
        } catch (IOException e) {
            throw new ResourceRepositoryException("Error while getting resource data", e);
        }
    }

    public void deleteResources(List<Long> resourceIds) {
        String[] resourceKeys = resourceIds.stream().map(String::valueOf).toArray(String[]::new);
        DeleteObjectsRequest request = new DeleteObjectsRequest(bucketName).withKeys(resourceKeys);
        s3Client.deleteObjects(request);
    }

}

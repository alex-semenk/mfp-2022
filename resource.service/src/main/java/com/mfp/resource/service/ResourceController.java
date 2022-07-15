package com.mfp.resource.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @PostMapping(value = "/resources")
    public ResourceId uploadResource(@RequestPart("file") MultipartFile file) {
        String contentType = file.getContentType();
        if (!"audio/mpeg".equals(contentType)) {
            throw new RuntimeException("Invalid file content type: " + contentType);
        }
        Resource resource = resourceService.uploadResource(file);
        return new ResourceId(resource.getId());
    }

    @GetMapping(value = "/resources/{id}", produces = "audio/mpeg")
    public byte[] getResourceById(@PathVariable("id") Long resourceId) {
        return resourceService.getResourceDataById(resourceId);
    }

    @DeleteMapping("/resources")
    public ResourceIds deleteResourcesByIds(@RequestParam("id") List<Long> resourceIdsToDelete) {
        resourceService.deleteResourcesByIds(resourceIdsToDelete);
        return new ResourceIds(resourceIdsToDelete);
    }

}

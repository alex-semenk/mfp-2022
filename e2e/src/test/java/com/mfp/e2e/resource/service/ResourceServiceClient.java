package com.mfp.e2e.resource.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "resource.service", url = "${resource-service.url}", path = "/resources")
public interface ResourceServiceClient {

    @GetMapping(value = "/{id}", produces = "audio/mpeg")
    byte[] getResourceById(@PathVariable("id") Long resourceId);

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResourceId uploadResource(@RequestPart("file") MultipartFile file);

}

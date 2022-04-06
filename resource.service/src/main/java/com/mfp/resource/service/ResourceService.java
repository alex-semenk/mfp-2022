package com.mfp.resource.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamSource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceService {

    private static class ResourceIdSequence {
        private static long nextId = 1;

        public static long nextId() {
            return nextId++;
        }
    }

    @Autowired
    private ResourceRepository resourceRepository;

    public Long uploadResource(InputStreamSource source) {
        Long resourceId = ResourceIdSequence.nextId();
        resourceRepository.uploadResource(resourceId, source);
        return resourceId;
    }

    public byte[] getResourceById(Long resourceId) {
        return resourceRepository.getResourceData(resourceId);
    }

    public void deleteResourcesByIds(List<Long> resourceIds) {
        resourceRepository.deleteResources(resourceIds);
    }

}

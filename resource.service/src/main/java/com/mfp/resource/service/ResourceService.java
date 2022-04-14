package com.mfp.resource.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamSource;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ResourceService {

    @Autowired
    private ResourceRepository resourceRepository;
    
    @Autowired
    private ResourceStorage resourceStorage;

    @Autowired
    private ResourceTopic resourceTopic;

    public Resource uploadResource(InputStreamSource source) {
        Resource resource = new Resource();
        resource.setLocation(UUID.randomUUID());
        resourceRepository.save(resource);
        resourceStorage.uploadResource(resource.getLocation().toString(), source);
        resourceTopic.sendMessage(resource.getId());
        return resource;
    }

    public byte[] getResourceDataById(Long resourceId) {
        Resource resource = resourceRepository.findById(resourceId).orElse(null);
        return resourceStorage.getResourceData(resource.getLocation().toString());
    }

    public void deleteResourcesByIds(List<Long> resourceIds) {
        List<String> keys = resourceRepository.findAllById(resourceIds).stream().map(Resource::getLocation).map(UUID::toString).toList();
        resourceStorage.deleteResources(keys);
        resourceRepository.deleteAllById(resourceIds);
    }

}
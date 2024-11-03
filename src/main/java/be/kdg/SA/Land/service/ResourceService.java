package be.kdg.SA.Land.service;

import be.kdg.SA.Land.domain.Resource;
import be.kdg.SA.Land.repository.ResourceRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ResourceService {
    private ResourceRepository resourceRepository;
    public ResourceService (ResourceRepository resourceRepository) {
        this.resourceRepository = resourceRepository;
    }
    public Resource findResource(Resource resource) {
       Optional<Resource> resourceType = resourceRepository.findByName(resource.getName());
        if (resourceType.isPresent()) {
            return resource;
        } else {
            throw new IllegalArgumentException("Resource type does not exist in the ResourceType enum");
        }
    }

}

package com.greenfox.tribes1.Resources;

import com.greenfox.tribes1.Exception.DateNotGivenException;
import com.greenfox.tribes1.Exception.NotValidResourceException;
import com.greenfox.tribes1.KingdomElementService;
import com.greenfox.tribes1.TimeService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;

@Service
public class ResourceService implements KingdomElementService<Resource> {

  private ResourceRepository resourceRepository;
  private TimeService timeService;

  @Autowired
  public ResourceService(ResourceRepository resourceRepository, TimeService timeService) {
    this.timeService = timeService;
    this.resourceRepository = resourceRepository;
  }

  @Override
  @SneakyThrows
  public Resource findById(Long id) {
    return resourceRepository.findById(id).orElseThrow(()
            -> new NotValidResourceException("There is no Building with such Id"));
  }

  @Override
  public Resource save(Optional<Resource> resource) throws NotValidResourceException {
    return resourceRepository.save(resource
            .orElseThrow(() -> new NotValidResourceException("Resource validation failed")));
  }

  @Override
  public void refresh(Resource resource) throws NotValidResourceException, DateNotGivenException {
    Long difference = timeService.calculateDifference(resource.getUpdated_at(), new Timestamp(System.currentTimeMillis()));
    if (difference > 0) {
      resource.update(difference);
      save(Optional.of(resource));
    }
  }

  public void lvlUp(Resource resource) {
    resource.setAmountPerMinute(resource.getAmountPerMinute() + 5L);
  }
}

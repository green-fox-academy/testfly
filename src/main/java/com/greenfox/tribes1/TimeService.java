package com.greenfox.tribes1;

import com.greenfox.tribes1.Building.Barracks;
import com.greenfox.tribes1.Building.Building;
import com.greenfox.tribes1.Exception.BuildingIdNotFoundException;
import com.greenfox.tribes1.Exception.DateNotGivenException;
import com.greenfox.tribes1.Progression.Progression;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static org.aspectj.runtime.internal.Conversions.longValue;
import java.util.function.Predicate;

@Service
public class TimeService {

  Predicate<Timestamp> isValid = Objects::nonNull;

  public Long calculateDifference(Timestamp started_at, Timestamp finished_at) throws DateNotGivenException {
    //TODO: try to rewrite with optional (pay attention that there are two possible NULL values
    if (isTimestampValid(started_at) && (isTimestampValid(finished_at))) {
      long difference = finished_at.getTime() - started_at.getTime();
      return TimeUnit.MILLISECONDS.toMinutes(difference);
    }
    return null;
  }

  public Boolean isTimestampExpired(Timestamp timestamp) {
    Timestamp currentTime = new Timestamp(System.currentTimeMillis());
    return currentTime.after(timestamp);
  }

  public Timestamp calculateBuildingTimeForNewBuildingOrTroop(Progression progression) throws Exception {
    Timestamp currentTime = new Timestamp(System.currentTimeMillis());
    Long buildingTime = buildingTime(progression);
    return new Timestamp(currentTime.getTime() + TimeUnit.MILLISECONDS.toMillis(buildingTime));
  }

  public Long buildingTime(Progression progression) throws Exception {
    Long oneMinute = 60L * 1000L;
    if (progression.getLevel() == 0) {
      return oneMinute;
    } else if (progression.getLevel() != 0 && progression.getType().equals("troop")) {

     Long maxLevelBarrack =  progression.getKingdom()
              .getBuildings().stream()
              .filter(r -> r instanceof Barracks)
              .map(Building::getLevel)
              .max(Comparator.naturalOrder())
              .orElseThrow(Exception::new);

     Float troopCreationTimeMultiplier = (1 - (maxLevelBarrack * 0.05F));
     return longValue(progression.getLevel() * oneMinute *  troopCreationTimeMultiplier);
    } else {
      return progression.getLevel() * 5 * oneMinute;
    }
  }

  public Boolean isTimestampValid(Timestamp timestamp) throws DateNotGivenException {
    //TODO: if everything is done with optional, it can be wiped out
    if (timestamp == null) {
      throw new DateNotGivenException("Date not given!");
    }
    return true;
  }
}


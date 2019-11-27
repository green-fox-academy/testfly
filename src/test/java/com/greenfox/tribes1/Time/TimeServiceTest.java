//package com.greenfox.tribes1.Time;
//
//import com.greenfox.tribes1.Exception.DateNotGivenException;
//import com.greenfox.tribes1.TimeService;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.sql.Timestamp;
//import java.util.concurrent.TimeUnit;
//
//import static org.junit.Assert.assertEquals;
//
//@RunWith(SpringRunner.class)
//public class TimeServiceTest {
//
//  @Mock
//  private TimeService timeService;
//  private Long timeDifference = 10L;
//  private Timestamp currentTime = new Timestamp(System.currentTimeMillis());
//  private Timestamp finishedDate = new Timestamp(currentTime.getTime() + TimeUnit.MINUTES.toMillis(timeDifference));
//
//  @Before
//  public void init() {
//    MockitoAnnotations.initMocks(this);
//    timeService = new TimeService();
//  }
//
//  @Test
//  public void TimeServiceGiveBackLongTest() throws DateNotGivenException {
//    Long result = 10L;
//    assertEquals(result, timeService.calculateDifference(currentTime, finishedDate));
//  }
//
//  @Test(expected = DateNotGivenException.class)
//  public void TimeServiceGiveBack_DateNotGivenException() throws DateNotGivenException {
//    timeService.calculateDifference(null, currentTime);
//  }
//}
//

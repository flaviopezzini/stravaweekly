package stravaweekly.activity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.*;

@Service
public class ActivityService {

    private static final Logger logger = LoggerFactory.getLogger(ActivityService.class);

    public ZonedDateTime getEndDate(ZoneId userZoneId) {
        LocalDateTime now = LocalDateTime.now();

        logger.info("now" +  now);
        ZonedDateTime zonedNow = ZonedDateTime.of(now, userZoneId);
        logger.info("zonedNow" +  zonedNow);

        // 1-Mon, 2-Tue, 3-Wed, 4-Thu, 5-Fri, 6-Sat, 7-Sun
        int dayOfWeek = zonedNow.getDayOfWeek().getValue();
        logger.info("dayOfWeek" +  dayOfWeek);
        ZonedDateTime zonedEnd;
        // if it's Sunday get this week, else get last week
        if (zonedNow.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
            logger.info("is sunday");
            zonedEnd = zonedNow;
        } else {
            logger.info("NOT sunday");
            zonedEnd = zonedNow.minusDays(dayOfWeek);
        }
        return zonedEnd;
    }

    public ZonedDateTime getStartDate(ZonedDateTime zonedEndDate) {
        return zonedEndDate.minusDays(7).withHour(23).withMinute(59).withSecond(59);
    }

}

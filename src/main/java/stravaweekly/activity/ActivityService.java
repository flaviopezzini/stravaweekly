package stravaweekly.activity;

import org.springframework.stereotype.Service;

import java.time.*;

@Service
public class ActivityService {

    private final Clock clock;

    public ActivityService(Clock clock) {
        this.clock = clock;
    }

    public ZonedDateTime getEndDate(ZoneId userZoneId) {
        LocalDateTime now = LocalDateTime.now(clock);
        ZonedDateTime zonedNow = ZonedDateTime.of(now, userZoneId);

        // 1-Mon, 2-Tue, 3-Wed, 4-Thu, 5-Fri, 6-Sat, 7-Sun
        int dayOfWeek = zonedNow.getDayOfWeek().getValue();
        ZonedDateTime zonedEnd;
        // if it's Sunday get this week, else get last week
        if (zonedNow.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
            zonedEnd = zonedNow;
        } else {
            zonedEnd = zonedNow.minusDays(dayOfWeek);
        }
        return zonedEnd;
    }

    public ZonedDateTime getStartDate(ZonedDateTime zonedEndDate) {
        return zonedEndDate.minusDays(7).withHour(23).withMinute(59).withSecond(59);
    }

}

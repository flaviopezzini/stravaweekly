package stravaweekly.activity;

import org.junit.Test;

import java.time.*;

import static org.junit.Assert.assertEquals;

public class ActivityServiceTest {

    private ActivityService buildServiceFromDate(LocalDate fixedDate, ZoneId zoneId) {
        Clock fixedClock  = Clock.fixed(fixedDate.atStartOfDay(zoneId).toInstant(), zoneId);
        return new ActivityService(fixedClock);
    }

    @Test
    public void shouldReturnPreviousMondayAsStartDateWhenEndDateIsSunday() {
        LocalDate fixedDate = LocalDate.of(2020, 3, 15);
        ActivityService activityService = buildServiceFromDate(fixedDate, ZoneId.systemDefault());

        ZonedDateTime zonedEndDate = ZonedDateTime.of(
                2020, 3, 15, 0, 0, 0, 0,
                ZoneId.systemDefault());

        ZonedDateTime expectedZonedStartDate = ZonedDateTime.of(
                2020, 3, 8, 23, 59, 59, 0,
                ZoneId.systemDefault());

        ZonedDateTime actualZonedStartDate = activityService.getStartDate(zonedEndDate);

        assertEquals(expectedZonedStartDate, actualZonedStartDate);
    }

    @Test
    public void shouldReturnPreviousMondayAsStartDateWhenEndDateIsSundayForNZTimeZone() {
        LocalDate fixedDate = LocalDate.of(2020, 3, 15);
        ActivityService activityService = buildServiceFromDate(fixedDate, ZoneId.ofOffset("", ZoneOffset.ofHours(+13)));

        ZonedDateTime zonedEndDate = ZonedDateTime.of(
                2020, 3, 15, 0, 0, 0, 0,
                ZoneId.systemDefault());

        ZonedDateTime expectedZonedStartDate = ZonedDateTime.of(
                2020, 3, 8, 23, 59, 59, 0,
                ZoneId.systemDefault());

        ZonedDateTime actualZonedStartDate = activityService.getStartDate(zonedEndDate);

        assertEquals(expectedZonedStartDate, actualZonedStartDate);
    }

    @Test
    public void shouldReturnSundayAsEndDateWhenItsSunday() {
        LocalDate fixedDate = LocalDate.of(2020, 3, 15);
        ActivityService activityService = buildServiceFromDate(fixedDate, ZoneId.systemDefault());

        ZonedDateTime expectedZonedEndDate = ZonedDateTime.of(
                2020, 3, 15, 0, 0, 0, 0,
                ZoneId.systemDefault());

        ZonedDateTime actualZonedEndDate = activityService.getEndDate(ZoneId.systemDefault());

        assertEquals(expectedZonedEndDate, actualZonedEndDate);
    }

    @Test
    public void shouldReturnPreviousSundayAsEndDateWhenNotSunday() {
        LocalDate fixedDate = LocalDate.of(2020, 3, 17);
        ActivityService activityService = buildServiceFromDate(fixedDate, ZoneId.systemDefault());

        ZonedDateTime expectedZonedEndDate = ZonedDateTime.of(
                2020, 3, 15, 0, 0, 0, 0,
                ZoneId.systemDefault());

        ZonedDateTime actualZonedEndDate = activityService.getEndDate(ZoneId.systemDefault());

        assertEquals(expectedZonedEndDate, actualZonedEndDate);
    }

}

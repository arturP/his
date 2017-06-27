package sample.his.system.client;

import org.junit.Test;
import sample.his.system.HolidayInformation;

import java.time.LocalDate;
import java.time.Month;
import java.util.Collections;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


public class HolidayUtilsTest {

    private static final Integer OK_STATUS = 200;
    private static final Integer INVALID_STATUS = 400;

    @Test(expected = NullPointerException.class)
    public void holidayInThisSameDayWithNulls() {
        // GIVEN
        HolidayInformationResponse firstHoliday = null;
        HolidayInformationResponse secondHoliday = null;

        // WHEN
        HolidayUtils.holidayInThisSameDay(firstHoliday, secondHoliday);
    }

    @Test(expected = NullPointerException.class)
    public void holidayInThisSameDayWithNullHolidayList() {
        // GIVEN
        HolidayInformationResponse firstHoliday = new HolidayInformationResponse()
                .setStatus(OK_STATUS);
        HolidayInformationResponse secondHoliday = new HolidayInformationResponse()
                .setStatus(OK_STATUS);

        // WHEN
        HolidayUtils.holidayInThisSameDay(firstHoliday, secondHoliday);
    }

    @Test
    public void holidayInThisSameDayWithNotOKStatusReturnsEmptyValue() {
        // GIVEN
        HolidayInformationResponse firstHoliday = new HolidayInformationResponse()
                .setStatus(INVALID_STATUS).setHolidays(Collections.emptyList());
        HolidayInformationResponse secondHoliday = new HolidayInformationResponse()
                .setStatus(INVALID_STATUS).setHolidays(Collections.emptyList());

        // WHEN
        HolidayInformation result = HolidayUtils.holidayInThisSameDay(firstHoliday, secondHoliday);

        // THEN
        assertNotNull("Expected not null result!", result);
        assertTrue("Expected true value returned from method isEmpty()", result.isEmpty());
    }

    @Test
    public void holidayInThisSameDayNotSameDayReturnsEmptyValue() {
        // GIVEN
        final Holiday FIRST_HOLIDAY = new Holiday().setDate("2014-01-01").setName("first Holiday")
                .setObserved("2014-01-01").setPublic(true);
        final Holiday SECOND_HOLIDAY = new Holiday().setDate("2015-01-01").setName("second Holiday")
                .setObserved("2015-01-01").setPublic(false);
        HolidayInformationResponse firstHoliday = new HolidayInformationResponse()
                .setStatus(OK_STATUS).setHolidays(Collections.singletonList(FIRST_HOLIDAY));
        HolidayInformationResponse secondHoliday = new HolidayInformationResponse()
                .setStatus(OK_STATUS).setHolidays(Collections.singletonList(SECOND_HOLIDAY));

        // WHEN
        HolidayInformation result = HolidayUtils.holidayInThisSameDay(firstHoliday, secondHoliday);

        // THEN
        assertNotNull("Expected not null result!", result);
        assertTrue("Expected true value returned from method isEmpty()", result.isEmpty());
    }

    @Test
    public void holidayInThisSameDaySameDayReturnHolidayInformation() {
        // GIVEN
        final String DATE = "2015-01-01";
        final String FIRST_HOLIDAY_NAME = "first Holiday";
        final String SECOND_HOLIDAY_NAME = "second Holiday";
        final Holiday FIRST_HOLIDAY = new Holiday().setDate(DATE).setName(FIRST_HOLIDAY_NAME)
                .setObserved(DATE).setPublic(true);
        final Holiday SECOND_HOLIDAY = new Holiday().setDate(DATE).setName(SECOND_HOLIDAY_NAME)
                .setObserved(DATE).setPublic(false);
        HolidayInformationResponse firstHoliday = new HolidayInformationResponse()
                .setStatus(OK_STATUS).setHolidays(Collections.singletonList(FIRST_HOLIDAY));
        HolidayInformationResponse secondHoliday = new HolidayInformationResponse()
                .setStatus(OK_STATUS).setHolidays(Collections.singletonList(SECOND_HOLIDAY));

        // WHEN
        HolidayInformation result = HolidayUtils.holidayInThisSameDay(firstHoliday, secondHoliday);

        // THEN
        assertNotNull("Expected not null result!", result);
        assertEquals("Returned date differs from expected",
                LocalDate.of(2015, Month.JANUARY, 1), result.getDate());
        assertEquals("Returned first language name differs from expected",
                FIRST_HOLIDAY_NAME, result.getName1());
        assertEquals("Returned second language name differs from expected",
                SECOND_HOLIDAY_NAME, result.getName2());
    }

    @Test(expected = NullPointerException.class)
    public void findEarlierDateWithNullParameters() throws IllegalArgumentException {
        // GIVEN
        HolidayInformationResponse firstHoliday = null;
        HolidayInformationResponse secondHoliday = null;

        // WHEN
        HolidayUtils.findEarlierDate(firstHoliday, secondHoliday);
    }

    @Test(expected = NullPointerException.class)
    public void findEarlierDateWithNullHolidayList() throws IllegalArgumentException {
        // GIVEN
        HolidayInformationResponse firstHoliday = new HolidayInformationResponse()
                .setStatus(OK_STATUS);
        HolidayInformationResponse secondHoliday = new HolidayInformationResponse()
                .setStatus(OK_STATUS);

        // WHEN
        HolidayUtils.findEarlierDate(firstHoliday, secondHoliday);
    }

    @Test(expected = IllegalArgumentException.class)
    public void findEarlierDateWithNotOKStatusThrowsInvalidArgumentException() throws IllegalArgumentException {
        // GIVEN
        HolidayInformationResponse firstHoliday = new HolidayInformationResponse()
                .setStatus(INVALID_STATUS).setHolidays(Collections.emptyList());
        HolidayInformationResponse secondHoliday = new HolidayInformationResponse()
                .setStatus(INVALID_STATUS).setHolidays(Collections.emptyList());

        // WHEN
        HolidayUtils.findEarlierDate(firstHoliday, secondHoliday);
    }

    @Test
    public void findEarlierDateWithSameDates() throws IllegalArgumentException {
        // GIVEN
        final String DATE = "2015-01-01";
        final String FIRST_HOLIDAY_NAME = "first Holiday";
        final String SECOND_HOLIDAY_NAME = "second Holiday";
        final Holiday FIRST_HOLIDAY = new Holiday().setDate(DATE).setName(FIRST_HOLIDAY_NAME)
                .setObserved(DATE).setPublic(true);
        final Holiday SECOND_HOLIDAY = new Holiday().setDate(DATE).setName(SECOND_HOLIDAY_NAME)
                .setObserved(DATE).setPublic(false);
        HolidayInformationResponse firstHoliday = new HolidayInformationResponse()
                .setStatus(OK_STATUS).setHolidays(Collections.singletonList(FIRST_HOLIDAY));
        HolidayInformationResponse secondHoliday = new HolidayInformationResponse()
                .setStatus(OK_STATUS).setHolidays(Collections.singletonList(SECOND_HOLIDAY));

        // WHEN
        LocalDate result = HolidayUtils.findEarlierDate(firstHoliday, secondHoliday);

        // THEN
        assertNotNull("Expected not null result!", result);
        assertEquals("Returned date differs from expected",
                LocalDate.of(2015, Month.JANUARY, 1), result);
    }

    @Test
    public void findEarlierDateWithFirstEarlier() throws IllegalArgumentException {
        // GIVEN
        final String FIRST_DATE = "2015-01-01";
        final String SECOND_DATE = "2015-01-02";
        final String FIRST_HOLIDAY_NAME = "first Holiday";
        final String SECOND_HOLIDAY_NAME = "second Holiday";
        final Holiday FIRST_HOLIDAY = new Holiday().setDate(FIRST_DATE).setName(FIRST_HOLIDAY_NAME)
                .setObserved(FIRST_DATE).setPublic(true);
        final Holiday SECOND_HOLIDAY = new Holiday().setDate(SECOND_DATE).setName(SECOND_HOLIDAY_NAME)
                .setObserved(SECOND_DATE).setPublic(false);
        HolidayInformationResponse firstHoliday = new HolidayInformationResponse()
                .setStatus(OK_STATUS).setHolidays(Collections.singletonList(FIRST_HOLIDAY));
        HolidayInformationResponse secondHoliday = new HolidayInformationResponse()
                .setStatus(OK_STATUS).setHolidays(Collections.singletonList(SECOND_HOLIDAY));

        // WHEN
        LocalDate result = HolidayUtils.findEarlierDate(firstHoliday, secondHoliday);

        // THEN
        assertNotNull("Expected not null result!", result);
        assertEquals("Returned date differs from expected",
                LocalDate.of(2015, Month.JANUARY, 1), result);
    }

    @Test
    public void findEarlierDateWithSecondEarlier() throws IllegalArgumentException {
        // GIVEN
        final String FIRST_DATE = "2016-12-01";
        final String SECOND_DATE = "2015-01-01";
        final String FIRST_HOLIDAY_NAME = "first Holiday";
        final String SECOND_HOLIDAY_NAME = "second Holiday";
        final Holiday FIRST_HOLIDAY = new Holiday().setDate(FIRST_DATE).setName(FIRST_HOLIDAY_NAME)
                .setObserved(FIRST_DATE).setPublic(true);
        final Holiday SECOND_HOLIDAY = new Holiday().setDate(SECOND_DATE).setName(SECOND_HOLIDAY_NAME)
                .setObserved(SECOND_DATE).setPublic(false);
        HolidayInformationResponse firstHoliday = new HolidayInformationResponse()
                .setStatus(OK_STATUS).setHolidays(Collections.singletonList(FIRST_HOLIDAY));
        HolidayInformationResponse secondHoliday = new HolidayInformationResponse()
                .setStatus(OK_STATUS).setHolidays(Collections.singletonList(SECOND_HOLIDAY));

        // WHEN
        LocalDate result = HolidayUtils.findEarlierDate(firstHoliday, secondHoliday);

        // THEN
        assertNotNull("Expected not null result!", result);
        assertEquals("Returned date differs from expected",
                LocalDate.of(2015, Month.JANUARY, 1), result);
    }

    @Test(expected = IllegalArgumentException.class)
    public void findEarlierDateWithNullHolidayInCollection() throws IllegalArgumentException {
        // GIVEN
        HolidayInformationResponse firstHoliday = new HolidayInformationResponse()
                .setStatus(OK_STATUS).setHolidays(Collections.emptyList());
        HolidayInformationResponse secondHoliday = new HolidayInformationResponse()
                .setStatus(OK_STATUS).setHolidays(Collections.emptyList());

        // WHEN
        HolidayUtils.findEarlierDate(firstHoliday, secondHoliday);
    }
}

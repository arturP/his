package sample.his.system.client;

import sample.his.system.HolidayInformation;

import java.time.LocalDate;

/**
 *
 */
public class HolidayUtils {
    private static final String NULL_PARAMETER_ERROR_MESSAGE = "Provided parameter is null!";
    private static final String NULL_LIST_ERROR_MESSAGE  = "List field is null!";
    private static final String NULL_STATUS_ERROR_MESSAGE = "Status field is null!";
    private static final Integer OK_VALUE = 200;

    public static HolidayInformation holidayInThisSameDay(HolidayInformationResponse first,
                                                    HolidayInformationResponse second) {

        checkInputValues(first, second);

        if (OK_VALUE.equals(first.getStatus()) && OK_VALUE.equals(second.getStatus())) {
            for (Holiday firstInfo : first.getHolidays()) {
                for (Holiday secondInfo : second.getHolidays()) {
                    if (firstInfo.getDate().equals(secondInfo.getDate())) {
                        return new HolidayInformation().setDate(LocalDate.parse(firstInfo.getDate()))
                                .setName1(firstInfo.getName())
                                .setName2(secondInfo.getName());
                    }
                }
            }
        }
        return new HolidayInformation();
    }

    public static LocalDate findEarlierDate(HolidayInformationResponse first, HolidayInformationResponse second)
            throws IllegalArgumentException {

        checkInputValues(first, second);

        if ((!(OK_VALUE.equals(first.getStatus()))) || (!(OK_VALUE.equals(second.getStatus())))) {
            throw new IllegalArgumentException("Status is different then expected!");
        }

        LocalDate result = getEarlierDate(getEarlierDate(LocalDate.MAX, first), second);

        if (LocalDate.MAX.equals(result)) {
            throw new IllegalArgumentException("Date is not provided!");
        }
        return result;
    }

    private static void checkInputValues(HolidayInformationResponse first, HolidayInformationResponse second) {

        if (first == null || second == null) {
            throw new NullPointerException(NULL_PARAMETER_ERROR_MESSAGE);
        }

        if (first.getHolidays() == null || second.getHolidays() == null) {
            throw new NullPointerException(NULL_LIST_ERROR_MESSAGE);
        }

        if (first.getStatus() == null || second.getStatus() == null) {
            throw new NullPointerException(NULL_STATUS_ERROR_MESSAGE);
        }
    }

    private static LocalDate getEarlierDate(LocalDate initDate, HolidayInformationResponse holidayInformationResponse) {

        LocalDate earliestDate = initDate;

        for (Holiday holiday : holidayInformationResponse.getHolidays()) {
            if (holiday != null) {
                LocalDate holidayDate = LocalDate.parse(holiday.getDate());
                if (holidayDate.isBefore(earliestDate)) {
                    earliestDate = holidayDate;
                }
            }
        }
        return earliestDate;
    }
}

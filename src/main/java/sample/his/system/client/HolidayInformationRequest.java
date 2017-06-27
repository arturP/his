package sample.his.system.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class HolidayInformationRequest {

    private String apiKey;
    private String countryCode;
    private String year;
    private String month;
    private String day;
    private Boolean upcoming;

    public String getApiKey() {
        return apiKey;
    }

    public HolidayInformationRequest setApiKey(String apiKey) {
        this.apiKey = apiKey;
        return this;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public HolidayInformationRequest setCountryCode(String countryCode) {
        this.countryCode = countryCode;
        return this;
    }

    public String getYear() {
        return year;
    }

    public HolidayInformationRequest setYear(String year) {
        this.year = year;
        return this;
    }

    public String getMonth() {
        return month;
    }

    public HolidayInformationRequest setMonth(String month) {
        this.month = month;
        return this;
    }

    public String getDay() {
        return day;
    }

    public HolidayInformationRequest setDay(String day) {
        this.day = day;
        return this;
    }

    public Boolean getUpcoming() {
        return upcoming;
    }

    public HolidayInformationRequest setUpcoming(Boolean upcoming) {
        this.upcoming = upcoming;
        return this;
    }
}

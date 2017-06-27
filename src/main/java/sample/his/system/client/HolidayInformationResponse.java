package sample.his.system.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

/**
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class HolidayInformationResponse {
    private Integer status;
    private List<Holiday> holidays;

    public Integer getStatus() {
        return status;
    }

    public HolidayInformationResponse setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public List<Holiday> getHolidays() {
        return holidays;
    }

    public HolidayInformationResponse setHolidays(List<Holiday> holidays) {
        this.holidays = holidays;
        return this;
    }
}

package sample.his.system.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestOperations;
import sample.his.system.HolidayInformation;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 *
 */
public class HolidayClient implements HolidayClientInterface {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private String address = "https://holidayapi.com/v1/holidays";
    private String apiKey = "8e614178-0d6f-452d-96f2-bc9c2e25fdd2";
    private RestOperations restOperations;

    public HolidayClient(){}

    public HolidayClient(String address, String apiKey, RestOperations restOperations){
        this.address = address;
        this.apiKey = apiKey;
        this.restOperations = restOperations;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public void setRestOperations(RestOperations restOperations){
        this.restOperations = restOperations;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public HolidayInformationResponse getHolidayInformation(HolidayInformationRequest request){
        if(request == null){
            throw new IllegalArgumentException("Argument for a method cannot be null");
        }
        StringBuilder uriString = new StringBuilder();
        uriString.append(address)
                .append("?key={apiKey}")
                .append("&country={countryCode}")
                .append("&year={year}")
                .append("&month={month}")
                .append("&day={day}")
                .append("&upcoming=true");

        logger.debug(uriString.toString());

        return restOperations.getForObject(uriString.toString(), HolidayInformationResponse.class,
                request.apiKey(), request.countryCode(), request.year(), request.month(), request.day());

    }

    public HolidayInformation findHolidayForCountries(@NotNull String firstCountryCode,
                                                      @NotNull String secondCountryCode,
                                                      @NotNull LocalDate date) {
        LocalDate originalDate = date;
        do {
            HolidayInformationResponse firstResponse = getNextHoliday(firstCountryCode, date);

            HolidayInformationResponse secondResponse = getNextHoliday(secondCountryCode, date);

            HolidayInformation information = HolidayUtils.holidayInThisSameDay(firstResponse, secondResponse);

            if (!information.isEmpty()) {
                return information;
            }

            try {
                date = HolidayUtils.findEarlierDate(firstResponse, secondResponse);
            } catch (IllegalArgumentException e) {
                logger.error(e.getLocalizedMessage());
                break;
            }

        } while (originalDate.plus(1, ChronoUnit.YEARS).isAfter(date));

        return new HolidayInformation();
    }

    private HolidayInformationResponse getNextHoliday(String countryCode, LocalDate date) {

        return this.getHolidayInformation(HolidayInformationRequest.builder()
                .setApiKey(apiKey)
                .setCountryCode(countryCode)
                .setYear(Integer.toString(date.getYear()))
                .setMonth(Integer.toString(date.getMonthValue()))
                .setDay(Integer.toString(date.getDayOfMonth()))
                .setUpcoming(true).build());
    }
}

package sample.his.system.client;

import org.springframework.stereotype.Service;
import sample.his.system.HolidayInformation;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 *
 */
@Service
public interface HolidayClientInterface {
    /**
     * Finds next holiday that is in this same day for both countries.
     *
     * @param firstCountryCode - country code for first country
     * @param secondCountryCode - country code for second country
     * @param date - date after which holiday must be find
     * @return
     */
    HolidayInformation findHolidayForCountries(@NotNull String firstCountryCode,
                                               @NotNull String secondCountryCode,
                                               @NotNull LocalDate date);
}

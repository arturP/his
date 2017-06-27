package sample.his.system;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import sample.his.system.client.HolidayClientInterface;
import sample.his.system.validation.CheckCountryCode;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 *
 */
@RestController
@Validated
public class HolidayInformationController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private HolidayClientInterface client;

    public HolidayInformationController(HolidayClientInterface holidayClient){
        this.client = holidayClient;
    }


    @ApiOperation(
            value = "Retrieves next holiday for both countries",
            notes = "Retrieves next holiday for both countries",
            response = HolidayInformation.class,
            responseContainer = "ResponseEntity")
    @RequestMapping(value = "/nextholiday", method = RequestMethod.GET)
    public ResponseEntity<HolidayInformation> getHolidayInformation(
            @ApiParam(value="First country code", required = true)
            @RequestParam(value="firstCountry") @CheckCountryCode String firstCountry,
            @ApiParam(value="Second country code", required = true)
            @RequestParam(value="secondCountry") @CheckCountryCode String secondCountry,
            @ApiParam(value="Search for holiday after this date", required = true)
            @RequestParam(value="holidayDate") @NotNull @DateTimeFormat(pattern="yyyy-MM-dd") LocalDate holidayDate){

        logger.debug("First country code: " + firstCountry + " Second country code: " + secondCountry);

        try {
            return new ResponseEntity<>(this.client.findHolidayForCountries(firstCountry, secondCountry, holidayDate),
                    HttpStatus.OK);
        } catch (HttpClientErrorException error) {
            logger.error("Error during call to holidayapi web service.");
            logger.error("Error message: " + error.getMessage());
            return new ResponseEntity<>(new HolidayInformation(), error.getStatusCode());
        }
    }

    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }

}

package sample.his.system;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import sample.his.system.client.HolidayClientInterface;

import javax.validation.ConstraintViolationException;
import java.time.LocalDate;
import java.time.Month;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class HolidayInformationControllerIT {

    @Autowired
    HolidayInformationController controller;

    @MockBean
    HolidayClientInterface clientInterface;

    @Test
    public void getHolidayInformationReturnsInfo() {

        // GIVEN
        final String FIRST_COUNTRY = "GB";
        final String SECOND_COUNTRY = "FR";
        final LocalDate date = LocalDate.of(2017, Month.FEBRUARY, 1);
        Mockito.when(clientInterface.findHolidayForCountries(anyString(), anyString(), any()))
                .thenReturn(new HolidayInformation().setName1("Expected1")
                        .setName2("Expected2").setDate(LocalDate.now()));

        // WHEN
        ResponseEntity<HolidayInformation> information =
                controller.getHolidayInformation(FIRST_COUNTRY, SECOND_COUNTRY, date);

        // THEN
        assertNotNull("Expected not null object!", information);
        assertEquals("Expected OK status", HttpStatus.OK, information.getStatusCode());
        assertNotNull("Expected not null body in response", information.getBody());
    }

    @Test(expected=ConstraintViolationException.class)
    public void getHolidayInformationInvalidCountryThrowsException() {

        // GIVEN
        final String FIRST_COUNTRY = "Abcd";
        final String SECOND_COUNTRY = "FR";
        final LocalDate date = LocalDate.of(2017, Month.FEBRUARY, 1);

        // WHEN
        controller.getHolidayInformation(FIRST_COUNTRY, SECOND_COUNTRY, date);
    }

    @Test(expected=ConstraintViolationException.class)
    public void getHolidayInformationInvalidSecondCountryThrowsException() {
        // GIVEN
        final String FIRST_COUNTRY = "PL";
        final String SECOND_COUNTRY = "Thi is invalid country code";
        final LocalDate date = LocalDate.of(2017, Month.FEBRUARY, 1);

        // WHEN
        controller.getHolidayInformation(FIRST_COUNTRY, SECOND_COUNTRY, date);
    }

    @Test(expected=ConstraintViolationException.class)
    public void getHolidayInformationNullParametersThrowsException() {

        // WHEN
        controller.getHolidayInformation(null, null, null);
    }

    @Test(expected=ConstraintViolationException.class)
    public void getHolidayInformationInvalidDateThrowsException() {
        // GIVEN
        final String FIRST_COUNTRY = "PL";
        final String SECOND_COUNTRY = "DE";
        final LocalDate date = null;

        // WHEN
        controller.getHolidayInformation(FIRST_COUNTRY, SECOND_COUNTRY, date);
    }
}

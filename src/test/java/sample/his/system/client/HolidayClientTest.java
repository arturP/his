package sample.his.system.client;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;
import sample.his.system.HolidayInformation;

import java.time.LocalDate;
import java.time.Month;
import java.util.Collections;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.eq;

/**
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class HolidayClientTest {
    @InjectMocks
    private HolidayClient client;

    @Mock
    private RestTemplate restTemplate;

    private final static Integer OK_STATUS = 200;

    @Test
    public void getHolidayInformationReturnsValidResponse(){
        // GIVEN
        HolidayInformationRequest request = HolidayInformationRequest.builder()
                .setApiKey("abc-def-xyz")
                .setCountryCode("PL").setYear("2000").setDay("01").setMonth("12").setUpcoming(true).build();

        client.setAddress("http://abc.def.com");
        client.setApiKey("abc-def-xyz");
        client.setRestOperations(restTemplate);

        Mockito.when(restTemplate.getForObject(Mockito.anyString(), Mockito.any(), Mockito.anyString(),
                Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
                .thenReturn(new HolidayInformationResponse());

        // WHEN
        HolidayInformationResponse response = client.getHolidayInformation(request);

        // THEN
        assertNotNull(response);

        Mockito.verify(restTemplate, Mockito.times(1))
                .getForObject(Mockito.anyString(), Mockito.any(), Mockito.anyString(), Mockito.anyString(),
                        Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
    }

    @Test
    public void findHolidayForCountriesReturnsHoliday() {
        // GIVEN
        final String FIRST_COUNTRY_CODE = "EN";
        final String SECOND_COUNTRY_CODE = "PL";
        final LocalDate date = LocalDate.of(2016, Month.JANUARY, 1);
        client.setAddress("http://abc.def.com");
        client.setApiKey("abc-def-xyz");
        client.setRestOperations(restTemplate);
        final String FIRST_HOLIDAY_NAME = "First holiday";
        final String SECOND_HOLIDAY_NAME = "Second holiday";
        final Holiday FIRST_HOLIDAY = new Holiday().setName(FIRST_HOLIDAY_NAME).setPublic(true).setDate("2016-01-05")
                .setObserved("2016-01-06");
        final Holiday SECOND_HOLIDAY = new Holiday().setName(SECOND_HOLIDAY_NAME).setPublic(true).setDate("2016-01-05")
                .setObserved("2016-01-06");

        Mockito.when(restTemplate.getForObject(Mockito.anyString(), Mockito.any(), Mockito.anyString(),
                eq(FIRST_COUNTRY_CODE), Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
                .thenReturn(new HolidayInformationResponse().setStatus(OK_STATUS)
                .setHolidays(Collections.singletonList(FIRST_HOLIDAY)));
        Mockito.when(restTemplate.getForObject(Mockito.anyString(), Mockito.any(), Mockito.anyString(),
                eq(SECOND_COUNTRY_CODE), Mockito.anyString(), Mockito.anyString(), Mockito.anyString()))
                .thenReturn(new HolidayInformationResponse().setStatus(OK_STATUS)
                        .setHolidays(Collections.singletonList(SECOND_HOLIDAY)));

        // WHEN
        HolidayInformation result = client.findHolidayForCountries(FIRST_COUNTRY_CODE, SECOND_COUNTRY_CODE, date);

        // THEN
        assertNotNull(result);
        assertEquals("Date different then expected",
                LocalDate.of(2016, Month.JANUARY, 5), result.getDate());
        assertEquals("First holiday name different then expected",
                FIRST_HOLIDAY_NAME, result.getName1());
        assertEquals("Second holiday name different then expected",
                SECOND_HOLIDAY_NAME, result.getName2());
        Mockito.verify(restTemplate, Mockito.times(2))
                .getForObject(Mockito.anyString(), Mockito.any(), Mockito.anyString(), Mockito.anyString(),
                        Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
    }

    @Test
    public void findHolidayForCountriesReturnsEmptyResponse() {
        // GIVEN
        final String FIRST_COUNTRY_CODE = "AR";
        final String SECOND_COUNTRY_CODE = "ZW";
        final LocalDate date = LocalDate.of(2016, Month.JANUARY, 1);
        client.setAddress("http://abc.def.com");
        client.setApiKey("abc-def-xyz");
        client.setRestOperations(restTemplate);
        final String FIRST_HOLIDAY_NAME = "First holiday";
        final String SECOND_HOLIDAY_NAME = "Second holiday";
        final Holiday FIRST_HOLIDAY = new Holiday().setName(FIRST_HOLIDAY_NAME).setPublic(true).setDate("2016-01-05")
                .setObserved("2016-01-06");
        final Holiday SECOND_HOLIDAY = new Holiday().setName(SECOND_HOLIDAY_NAME).setPublic(true).setDate("2016-05-12")
                .setObserved("2016-05-12");
        final Holiday THIRD_HOLIDAY = new Holiday().setName("Next").setPublic(true).setDate("2017-02-01")
                .setObserved("2017-02-02");
        final Holiday FOURTH_HOLIDAY = new Holiday().setName("Another").setPublic(true).setDate("2017-03-11")
                .setObserved("2017-03-11");


        Mockito.when(restTemplate.getForObject(Mockito.anyString(), Mockito.any(), Mockito.anyString(),
                eq(FIRST_COUNTRY_CODE), eq("2016"), eq("1"), eq("1")))
                .thenReturn(new HolidayInformationResponse().setStatus(OK_STATUS)
                        .setHolidays(Collections.singletonList(FIRST_HOLIDAY)));
        Mockito.when(restTemplate.getForObject(Mockito.anyString(), Mockito.any(), Mockito.anyString(),
                eq(SECOND_COUNTRY_CODE), eq("2016"), eq("1"), eq("1")))
                .thenReturn(new HolidayInformationResponse().setStatus(OK_STATUS)
                        .setHolidays(Collections.singletonList(SECOND_HOLIDAY)));
        Mockito.when(restTemplate.getForObject(Mockito.anyString(), Mockito.any(), Mockito.anyString(),
                eq(FIRST_COUNTRY_CODE), eq("2016"), eq("1"), eq("5")))
                .thenReturn(new HolidayInformationResponse().setStatus(OK_STATUS)
                        .setHolidays(Collections.singletonList(THIRD_HOLIDAY)));
        Mockito.when(restTemplate.getForObject(Mockito.anyString(), Mockito.any(), Mockito.anyString(),
                eq(SECOND_COUNTRY_CODE), eq("2016"), eq("1"), eq("5")))
                .thenReturn(new HolidayInformationResponse().setStatus(OK_STATUS)
                        .setHolidays(Collections.singletonList(FOURTH_HOLIDAY)));

        // WHEN
        HolidayInformation result = client.findHolidayForCountries(FIRST_COUNTRY_CODE, SECOND_COUNTRY_CODE, date);

        // THEN
        assertNotNull("Expected not null result!", result);
        assertTrue("Expected returned true value!", result.isEmpty());
        Mockito.verify(restTemplate, Mockito.times(4))
                .getForObject(Mockito.anyString(), Mockito.any(), Mockito.anyString(), Mockito.anyString(),
                        Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
    }
}

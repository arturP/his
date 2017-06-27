package sample.his.system;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDate;

@ApiModel(value = "HolidayInformation", description = "Information about holiday in both countries")
public class HolidayInformation {

    @ApiModelProperty( value = "Date of holiday", required = true )
    private LocalDate date;
    @ApiModelProperty( value = "Name of holiday in first country", required = true )
    private String name1;
    @ApiModelProperty( value = "Name of holiday in second country", required = true )
    private String name2;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    public LocalDate getDate() {
        return date;
    }

    public HolidayInformation setDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public String getName1() {
        return name1;
    }

    public HolidayInformation setName1(String name1) {
        this.name1 = name1;
        return this;
    }

    public String getName2() {
        return name2;
    }

    public HolidayInformation setName2(String name2) {
        this.name2 = name2;
        return this;
    }

    @JsonIgnore
    public boolean isEmpty() {
        if (date == null && name1 == null && name2 == null) {
            return true;
        }
        return false;
    }

}
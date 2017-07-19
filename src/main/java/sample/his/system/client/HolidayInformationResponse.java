package sample.his.system.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.auto.value.AutoValue;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 *
 */
@AutoValue
@JsonDeserialize(builder = AutoValue_HolidayInformationResponse.Builder.class)
public abstract class HolidayInformationResponse {

    @NotNull
    public static Builder builder() {
        return new AutoValue_HolidayInformationResponse.Builder();
    }

    @NotNull
    @JsonProperty("status")
    public abstract Integer status();

    @NotNull
    @JsonProperty("holidays")
    public abstract List<Holiday> holidays();

    @AutoValue.Builder
    public static abstract class Builder {

        @NotNull
        @JsonProperty("status")
        public abstract Builder setStatus(@NotNull Integer status);

        @NotNull
        @JsonProperty("holidays")
        public abstract Builder setHolidays(@NotNull List<Holiday> holidays);

        @NotNull
        public abstract HolidayInformationResponse build();
    }
}

package sample.his.system.client;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.auto.value.AutoValue;

import javax.validation.constraints.NotNull;

/**
 *
 */
@AutoValue
@JsonDeserialize(builder = AutoValue_HolidayInformationRequest.Builder.class)
public abstract class HolidayInformationRequest {

    @NotNull
    public static Builder builder() {
        return new AutoValue_HolidayInformationRequest.Builder();
    }


    @NotNull
    @JsonProperty("apiKey")
    public abstract String apiKey();

    @NotNull
    @JsonProperty("countryCode")
    public abstract String countryCode();

    @NotNull
    @JsonProperty("year")
    public abstract String year();

    @NotNull
    @JsonProperty("month")
    public abstract String month();

    @NotNull
    @JsonProperty("day")
    public abstract String day();

    @NotNull
    @JsonProperty("upcoming")
    public abstract Boolean upcoming();

    @AutoValue.Builder
    public static abstract class Builder {

        @NotNull
        @JsonProperty("apiKey")
        public abstract Builder setApiKey(@NotNull String apiKey);

        @NotNull
        @JsonProperty("countryCode")
        public abstract Builder setCountryCode(@NotNull String countryCode);

        @NotNull
        @JsonProperty("year")
        public abstract Builder setYear(@NotNull String year);

        @NotNull
        @JsonProperty("month")
        public abstract Builder setMonth(@NotNull String month);

        @NotNull
        @JsonProperty("day")
        public abstract Builder setDay(@NotNull String day);

        @NotNull
        @JsonProperty("upcoming")
        public abstract Builder setUpcoming(@NotNull Boolean upcoming);

        @NotNull
        public abstract HolidayInformationRequest build();
    }

}

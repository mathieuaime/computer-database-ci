package com.excilys.computerdatabase.web.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.auto.value.AutoValue;

@AutoValue
@JsonDeserialize(builder = CompanyDto.Builder.class)
public abstract class CompanyDto {
    public abstract long getId();

    public abstract String getName();

    public static Builder builder() {
        return Builder.create();
    }

    @AutoValue.Builder
    @JsonPOJOBuilder(withPrefix = "")
    public static abstract class Builder {
        @JsonCreator
        private static Builder create() {
            return new AutoValue_CompanyDto.Builder();
        }

        public abstract Builder id(long id);

        public abstract Builder name(String name);

        public abstract CompanyDto build();
    }
}

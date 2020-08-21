package com.excilys.computerdatabase.web.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.auto.value.AutoValue;
import java.time.Instant;
import javax.annotation.Nullable;

@AutoValue
@JsonDeserialize(builder = ComputerDto.Builder.class)
public abstract class ComputerDto {
    @Nullable
    public abstract Long getId();

    public abstract String getName();

    public abstract Instant getIntroduced();

    @Nullable
    public abstract Instant getDiscontinued();

    @Nullable
    public abstract CompanyDto getCompany();

    public static Builder builder() {
        return Builder.create();
    }

    @AutoValue.Builder
    @JsonPOJOBuilder(withPrefix = "")
    public static abstract class Builder {
        @JsonCreator
        private static Builder create() {
            return new AutoValue_ComputerDto.Builder();
        }

        public abstract Builder id(Long id);

        public abstract Builder name(String name);

        public abstract Builder introduced(Instant introduced);

        public abstract Builder discontinued(Instant discontinued);

        public abstract Builder company(CompanyDto company);

        public abstract ComputerDto build();
    }
}

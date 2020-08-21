package com.excilys.computerdatabase.model;

import com.google.auto.value.AutoValue;
import java.time.Instant;
import javax.annotation.Nullable;

@AutoValue
public abstract class Computer {
    @Nullable
    public abstract Long getId();

    public abstract String getName();

    public abstract Instant getIntroduced();

    @Nullable
    public abstract Instant getDiscontinued();

    public abstract Company getCompany();

    public static Builder builder() {
        return new AutoValue_Computer.Builder();
    }

    @AutoValue.Builder
    public static abstract class Builder {
        public abstract Builder id(Long id);

        public abstract Builder name(String name);

        public abstract Builder introduced(Instant introduced);

        public abstract Builder discontinued(Instant discontinued);

        public abstract Builder company(Company company);

        public abstract Computer build();
    }
}

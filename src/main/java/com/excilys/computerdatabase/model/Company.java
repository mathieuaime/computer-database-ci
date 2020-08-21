package com.excilys.computerdatabase.model;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Company {
    public abstract Long getId();

    public abstract String getName();

    public static Builder builder() {
        return new AutoValue_Company.Builder();
    }

    @AutoValue.Builder
    public static abstract class Builder {
        public abstract Builder id(Long id);

        public abstract Builder name(String name);

        public abstract Company build();
    }
}

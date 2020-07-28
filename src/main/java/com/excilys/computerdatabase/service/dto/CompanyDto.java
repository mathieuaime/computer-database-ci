package com.excilys.computerdatabase.service.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.Objects;
import javax.validation.constraints.NotNull;

public class CompanyDto {
    private final Long id;

    @NotNull
    private final String name;

    public static Builder builder() {
        return new Builder();
    }

    @JsonCreator
    public CompanyDto(Long id, @NotNull String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CompanyDto that = (CompanyDto) o;
        return Objects.equals(id, that.id) &&
            Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String toString() {
        return "CompanyDto{" +
            "uuid='" + id + '\'' +
            ", name='" + name + '\'' +
            '}';
    }

    public static class Builder {
        private Long id;
        private String name;

        private Builder() {
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public CompanyDto build() {
            return new CompanyDto(this.id, this.name);
        }
    }
}

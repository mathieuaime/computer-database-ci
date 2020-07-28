package com.excilys.computerdatabase.service.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.time.Instant;
import java.util.Objects;
import javax.validation.constraints.NotNull;

public class ComputerDto {
    private final Long id;

    @NotNull
    private final String name;

    @NotNull
    private final Instant introduced;

    private final Instant discontinued;

    private final CompanyDto company;

    public static Builder builder() {
        return new Builder();
    }

    @JsonCreator
    public ComputerDto(Long id, @NotNull String name, @NotNull Instant introduced,
        Instant discontinued, CompanyDto company) {
        this.id = id;
        this.name = name;
        this.introduced = introduced;
        this.discontinued = discontinued;
        this.company = company;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Instant getIntroduced() {
        return introduced;
    }

    public Instant getDiscontinued() {
        return discontinued;
    }

    public CompanyDto getCompany() {
        return company;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ComputerDto that = (ComputerDto) o;
        return Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(introduced, that.introduced) &&
            Objects.equals(discontinued, that.discontinued) &&
            Objects.equals(company, that.company);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, introduced, discontinued, company);
    }

    @Override
    public String toString() {
        return "ComputerDto{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", introduced=" + introduced +
            ", discontinued=" + discontinued +
            ", company=" + company +
            '}';
    }

    public static class Builder {
        private Long id;
        private String name;
        private Instant introduced;
        private Instant discontinued;
        private CompanyDto company;

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

        public Builder introduced(Instant introduced) {
            this.introduced = introduced;
            return this;
        }

        public Builder discontinued(Instant discontinued) {
            this.discontinued = discontinued;
            return this;
        }

        public Builder company(CompanyDto company) {
            this.company = company;
            return this;
        }

        public ComputerDto build() {
            return new ComputerDto(this.id, this.name, this.introduced, this.discontinued,
                this.company);
        }
    }
}

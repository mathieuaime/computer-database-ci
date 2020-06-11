package com.excilys.computerdatabase.service.dto;

import java.util.Objects;
import javax.validation.constraints.NotNull;

public class ComputerDto {

    private String uuid;

    @NotNull
    private String name;

    private CompanyDto company;

    public String getUuid() {
        return uuid;
    }

    public ComputerDto setUuid(String uuid) {
        this.uuid = uuid;
        return this;
    }

    public String getName() {
        return name;
    }

    public ComputerDto setName(String name) {
        this.name = name;
        return this;
    }

    public CompanyDto getCompany() {
        return company;
    }

    public ComputerDto setCompany(CompanyDto company) {
        this.company = company;
        return this;
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
        return Objects.equals(uuid, that.uuid) &&
            Objects.equals(name, that.name) &&
            Objects.equals(company, that.company);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, name, company);
    }

    @Override
    public String toString() {
        return "ComputerDto{" +
            "uuid='" + uuid + '\'' +
            ", name='" + name + '\'' +
            ", company=" + company +
            '}';
    }
}

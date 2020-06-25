package com.excilys.computerdatabase.service.dto;

import java.util.Objects;
import javax.validation.constraints.NotNull;

public class ComputerDto {

    private Long id;

    @NotNull
    private String name;

    private CompanyDto company;

    public Long getId() {
        return id;
    }

    public ComputerDto setId(Long id) {
        this.id = id;
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
        return Objects.equals(id, that.id) &&
            Objects.equals(name, that.name) &&
            Objects.equals(company, that.company);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, company);
    }

    @Override
    public String toString() {
        return "ComputerDto{" +
            "uuid='" + id + '\'' +
            ", name='" + name + '\'' +
            ", company=" + company +
            '}';
    }
}

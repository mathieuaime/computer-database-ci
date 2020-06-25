package com.excilys.computerdatabase.service.dto;

import java.util.Objects;
import javax.validation.constraints.NotNull;

public class CompanyDto {

    private Long id;

    @NotNull
    private String name;

    public Long getId() {
        return id;
    }

    public CompanyDto setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public CompanyDto setName(String name) {
        this.name = name;
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
}

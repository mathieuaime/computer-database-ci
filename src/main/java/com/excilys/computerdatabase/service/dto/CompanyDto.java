package com.excilys.computerdatabase.service.dto;

import java.util.Objects;
import javax.validation.constraints.NotNull;

public class CompanyDto {

    private String uuid;

    @NotNull
    private String name;

    public String getUuid() {
        return uuid;
    }

    public CompanyDto setUuid(String uuid) {
        this.uuid = uuid;
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
        return Objects.equals(uuid, that.uuid) &&
            Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, name);
    }

    @Override
    public String toString() {
        return "CompanyDto{" +
            "uuid='" + uuid + '\'' +
            ", name='" + name + '\'' +
            '}';
    }
}

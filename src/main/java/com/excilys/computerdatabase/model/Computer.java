package com.excilys.computerdatabase.model;

import java.util.Objects;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Computer {
    @Id
    private String uuid = UUID.randomUUID().toString();

    private String name;

    @ManyToOne
    private Company company;

    public String getUuid() {
        return this.uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Company getCompany() {
        return this.company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Computer computer = (Computer) o;
        return Objects.equals(this.uuid, computer.uuid) && Objects.equals(this.name, computer.name) && Objects.equals(
                this.company,
                computer.company);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.uuid, this.name, this.company);
    }

    @Override
    public String toString() {
        return "Computer{" + "uuid='" + uuid + '\'' + ", name='" + name + '\'' + ", company=" + company + '}';
    }
}

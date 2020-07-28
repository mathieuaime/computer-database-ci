package com.excilys.computerdatabase.model;

import java.time.Instant;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Computer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Instant introduced;

    private Instant discontinued;

    @ManyToOne
    private Company company;

    public Long getId() {
        return id;
    }

    public Computer setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Computer setName(String name) {
        this.name = name;
        return this;
    }

    public Instant getIntroduced() {
        return introduced;
    }

    public Computer setIntroduced(Instant introduced) {
        this.introduced = introduced;
        return this;
    }

    public Instant getDiscontinued() {
        return discontinued;
    }

    public Computer setDiscontinued(Instant discontinued) {
        this.discontinued = discontinued;
        return this;
    }

    public Company getCompany() {
        return company;
    }

    public Computer setCompany(Company company) {
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
        Computer computer = (Computer) o;
        return Objects.equals(id, computer.id) &&
            Objects.equals(name, computer.name) &&
            Objects.equals(introduced, computer.introduced) &&
            Objects.equals(discontinued, computer.discontinued) &&
            Objects.equals(company, computer.company);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, introduced, discontinued, company);
    }

    @Override
    public String toString() {
        return "Computer{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", introduced=" + introduced +
            ", discontinued=" + discontinued +
            ", company=" + company +
            '}';
    }
}

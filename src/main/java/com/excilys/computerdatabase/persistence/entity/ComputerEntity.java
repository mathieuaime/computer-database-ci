package com.excilys.computerdatabase.persistence.entity;

import java.time.Instant;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "computer")
public class ComputerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Instant introduced;

    private Instant discontinued;

    @ManyToOne
    private CompanyEntity company;

    public Long getId() {
        return id;
    }

    public ComputerEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ComputerEntity setName(String name) {
        this.name = name;
        return this;
    }

    public Instant getIntroduced() {
        return introduced;
    }

    public ComputerEntity setIntroduced(Instant introduced) {
        this.introduced = introduced;
        return this;
    }

    public Instant getDiscontinued() {
        return discontinued;
    }

    public ComputerEntity setDiscontinued(Instant discontinued) {
        this.discontinued = discontinued;
        return this;
    }

    public CompanyEntity getCompany() {
        return company;
    }

    public ComputerEntity setCompany(CompanyEntity company) {
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
        ComputerEntity computer = (ComputerEntity) o;
        return Objects.equals(id, computer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
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

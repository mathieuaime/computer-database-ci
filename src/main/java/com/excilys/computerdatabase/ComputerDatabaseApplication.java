package com.excilys.computerdatabase;

import com.excilys.computerdatabase.model.Company;
import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.repository.CompanyRepository;
import com.excilys.computerdatabase.repository.ComputerRepository;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.IntStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ComputerDatabaseApplication {
    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private ComputerRepository computerRepository;

    public static void main(String[] args) {
        SpringApplication.run(ComputerDatabaseApplication.class, args);
    }

    @Bean
    public CommandLineRunner loadData() {
        return (args) -> IntStream.rangeClosed(1, 20).forEach(i -> {
            Company company = new Company().setName("Company " + i);
            companyRepository.save(company);

            IntStream.rangeClosed(1, 20).forEach(j -> {
                Computer computer =
                    new Computer().setName("Computer " + i + j)
                        .setCompany(company)
                        .setIntroduced(Instant.now())
                        .setDiscontinued(Instant.now().plus(1, ChronoUnit.DAYS));
                computerRepository.save(computer);
            });
        });
    }
}

package com.excilys.computerdatabase.service;

import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.persistence.ComputerDao;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ComputerService {
    private final ComputerDao computerDao;

    public ComputerService(ComputerDao computerDao) {
        this.computerDao = computerDao;
    }

    public Page<Computer> find(Pageable pageable) {
        return this.computerDao.findAll(pageable);
    }

    public Optional<Computer> findByUuid(String uuid) {
        return this.computerDao.findById(uuid);
    }

    public Page<Computer> findByCompany(String uuid, Pageable pageable) {
        return this.computerDao.findByCompany(uuid, pageable);
    }

    public Computer save(Computer computer) {
        return this.computerDao.save(computer);
    }

    public void delete(String uuid) {
        this.computerDao.deleteById(uuid);
    }
}

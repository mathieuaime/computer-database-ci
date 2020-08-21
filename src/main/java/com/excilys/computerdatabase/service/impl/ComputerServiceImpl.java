package com.excilys.computerdatabase.service.impl;

import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.service.dao.ComputerDao;
import com.excilys.computerdatabase.service.ComputerService;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("computerService")
@Transactional(readOnly = true)
public class ComputerServiceImpl implements ComputerService {
    private final ComputerDao computerDao;

    public ComputerServiceImpl(ComputerDao computerDao) {
        this.computerDao = computerDao;
    }

    @Override
    public Page<Computer> find(Pageable pageable) {
        return this.computerDao.find(pageable);
    }

    @Override
    public Optional<Computer> findById(long id) {
        return this.computerDao.findById(id);
    }

    @Override
    public Page<Computer> findByCompany(long id, Pageable pageable) {
        return this.computerDao.findByCompanyId(id, pageable);
    }

    @Override
    public long count() {
        return this.computerDao.count();
    }

    @Override
    public boolean exist(long id) {
        return this.computerDao.existsById(id);
    }

    @Override
    public Page<Computer> search(String filterText, Pageable pageable) {
        return this.computerDao.search(filterText, pageable);
    }

    @Transactional
    @Override
    public Computer save(Computer computer) {
        return this.computerDao.save(computer);
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        this.computerDao.deleteById(id);
    }
}

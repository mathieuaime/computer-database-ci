package com.excilys.computerdatabase.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.persistence.ComputerDao;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class ComputerServiceTest {

    @Mock
    private ComputerDao computerDao;

    @InjectMocks
    private ComputerService computerService;

    @Test
    void find() {
        Computer computer = new Computer();
        when(this.computerDao.findAll(Pageable.unpaged())).thenReturn(
                new PageImpl<>(Collections.singletonList(computer)));

        Page<Computer> computers = this.computerService.find(Pageable.unpaged());

        assertThat(computers).containsExactly(computer);
        verify(this.computerDao).findAll(Pageable.unpaged());
    }

    @Test
    void findByUuid() {
        Computer computer = new Computer();
        String uuid = UUID.randomUUID().toString();
        when(this.computerDao.findById(uuid)).thenReturn(Optional.of(computer));

        Optional<Computer> optComputer = this.computerService.findByUuid(uuid);

        assertThat(optComputer).contains(computer);
        verify(this.computerDao).findById(uuid);
    }

    @Test
    void findByCompany() {
        Computer computer = new Computer();
        when(this.computerDao.findByCompany("company", Pageable.unpaged())).thenReturn(
                new PageImpl<>(Collections.singletonList(computer)));

        Page<Computer> computers = this.computerService.findByCompany("company", Pageable.unpaged());

        assertThat(computers).containsExactly(computer);
        verify(this.computerDao).findByCompany("company", Pageable.unpaged());
    }

    @Test
    void save() {
        Computer computer = new Computer();
        when(this.computerDao.save(computer)).thenReturn(computer);

        Computer computerSaved = this.computerService.save(computer);

        assertThat(computerSaved).isEqualTo(computer);
        verify(this.computerDao).save(computer);
    }

    @Test
    void delete() {
        String uuid = UUID.randomUUID().toString();
        this.computerService.delete(uuid);

        verify(this.computerDao).deleteById(uuid);
    }
}
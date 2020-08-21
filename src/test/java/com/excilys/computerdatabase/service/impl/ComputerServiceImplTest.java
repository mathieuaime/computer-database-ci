package com.excilys.computerdatabase.service.impl;

import static java.util.List.of;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.data.domain.Pageable.unpaged;

import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.service.dao.ComputerDao;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;

@ExtendWith(MockitoExtension.class)
class ComputerServiceImplTest {
  @Mock
  private ComputerDao computerDao;

  @InjectMocks
  private ComputerServiceImpl computerService;

  @Test
  void find() {
    var computer = mock(Computer.class);
    when(computerDao.find(unpaged())).thenReturn(new PageImpl<>(of(computer)));

    var computers = computerService.find(unpaged());

    assertThat(computers.getContent()).containsExactly(computer);
  }

  @Test
  void findById() {
    var computer = mock(Computer.class);
    long id = 1;
    when(computerDao.findById(id)).thenReturn(Optional.of(computer));

    var optComputer = computerService.findById(id);

    assertThat(optComputer).contains(computer);
  }

  @Test
  void findByCompany() {
    int companyId = 1;
    var computer = mock(Computer.class);
    when(computerDao.findByCompanyId(companyId, unpaged()))
        .thenReturn(new PageImpl<>(of(computer)));

    var computers = computerService.findByCompany(companyId, unpaged());

    assertThat(computers.getContent()).containsExactly(computer);
  }

  @Test
  void count() {
    when(computerDao.count()).thenReturn(1L);

    long count = computerService.count();

    assertThat(count).isEqualTo(1L);
  }

  @Test
  void exist() {
    long id = 1L;
    when(computerDao.existsById(id)).thenReturn(true);

    boolean exist = computerService.exist(id);

    assertThat(exist).isTrue();
  }

  @Test
  void search() {
    String filter = "filter";
    var computer = mock(Computer.class);
    when(computerDao.search(filter, unpaged())).thenReturn(new PageImpl<>(of(computer)));

    var computers = computerService.search(filter, unpaged());

    assertThat(computers.getContent()).containsExactly(computer);
  }

  @Test
  void save() {
    var computer = mock(Computer.class);

    when(computerDao.save(computer)).thenReturn(computer);

    var saved = computerService.save(computer);

    assertThat(saved).isEqualTo(computer);
  }

  @Test
  void delete() {
    long id = 1;

    computerService.deleteById(id);

    verify(computerDao).deleteById(id);
  }
}
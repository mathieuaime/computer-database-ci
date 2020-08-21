package com.excilys.computerdatabase.cache;

import com.excilys.computerdatabase.model.Computer;
import com.excilys.computerdatabase.service.ComputerService;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class CachedComputerService implements ComputerService {
  private final ComputerService delegate;
  private final AtomicLong count = new AtomicLong();

  public CachedComputerService(ComputerService delegate) {
    this.delegate = delegate;
    count.set(delegate.count());
  }

  @Override
  public long count() {
    return count.get();
  }

  @Override
  public boolean exist(long id) {
    return delegate.exist(id);
  }

  @Override
  public Computer save(Computer computerDto) {
    Computer saved = delegate.save(computerDto);

    if (computerDto.getId() == null) {
      count.incrementAndGet();
    }

    return saved;
  }

  @Override
  public void deleteById(long id) {
    if (delegate.exist(id)) {
      delegate.deleteById(id);
      count.decrementAndGet();
    }
  }

  @Override
  public Page<Computer> find(Pageable pageable) {
    return delegate.find(pageable);
  }

  @Override
  public Optional<Computer> findById(long id) {
    return delegate.findById(id);
  }

  @Override
  public Page<Computer> findByCompany(long id, Pageable pageable) {
    return delegate.findByCompany(id, pageable);
  }

  @Override
  public Page<Computer> search(String filterText, Pageable pageable) {
    return delegate.search(filterText, pageable);
  }
}

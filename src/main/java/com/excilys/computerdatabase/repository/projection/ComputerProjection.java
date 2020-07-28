package com.excilys.computerdatabase.repository.projection;

import com.excilys.computerdatabase.model.Company;
import java.time.Instant;

public interface ComputerProjection {
  Long getId();

  String getName();

  Instant getIntroduced();

  Instant getDiscontinued();

  Company getCompany();
}

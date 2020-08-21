package com.excilys.computerdatabase.config;

import com.excilys.computerdatabase.service.ComputerService;
import com.excilys.computerdatabase.cache.CachedComputerService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile("cache")
@Configuration
public class CacheConfiguration {
  @Primary
  @Bean
  public ComputerService cachedComputerService(
      @Qualifier("computerService") ComputerService computerService) {
    return new CachedComputerService(computerService);
  }
}

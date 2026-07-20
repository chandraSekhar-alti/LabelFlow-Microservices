package com.labelflow.changeorder.common.config;

import java.time.Clock;
import java.time.LocalDate;
import java.time.Year;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TimeConfig {

  @Bean
  public Clock clock() {
    return Clock.systemUTC();
  }

  public LocalDate currentDate() {
    return LocalDate.now(clock());
  }

  public Year currentYear() {
    return Year.now(clock());
  }
}

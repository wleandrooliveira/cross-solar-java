package com.crossover.techtrial.service;

import com.crossover.techtrial.model.HourlyElectricity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

/**
 * HourlyElectricityService interface for all services realted to HourlyElectricity.
 * @author Crossover
 *
 */
public interface HourlyElectricityService {
  HourlyElectricity save(HourlyElectricity hourlyElectricity);
  
  Page<HourlyElectricity> getAllHourlyElectricityByPanelId(Long panelId, Pageable pageable);

  List<HourlyElectricity> findAllByPanelIdAndReadingAtLessThan(Long panelId, LocalDateTime date);
}

package com.crossover.techtrial.repository;

import com.crossover.techtrial.model.HourlyElectricity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RestResource;



import java.time.LocalDateTime;
import java.util.List;

/**
 * HourlyElectricity Repository is for all operations for HourlyElectricity.
 * @author Crossover
 */
@RestResource(exported = false)

public interface HourlyElectricityRepository  extends PagingAndSortingRepository<HourlyElectricity,Long> {

  Page<HourlyElectricity> findAllByPanelIdOrderByReadingAtDesc(Long panelId, Pageable pageable);

  List<HourlyElectricity> findAllByPanelIdAndReadingAtLessThan(Long panelId, LocalDateTime date);

}
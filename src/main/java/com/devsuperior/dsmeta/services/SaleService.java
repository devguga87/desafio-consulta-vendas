package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.devsuperior.dsmeta.dto.SalesRepportMinDTO;
import com.devsuperior.dsmeta.dto.SummaryMinDTO;
import com.devsuperior.dsmeta.projections.SalesRepportMinProjection;
import com.devsuperior.dsmeta.projections.SummaryMinProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;
	
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	public Page<SalesRepportMinDTO> findRepport(String minDate, String maxDate, String name, Pageable pageable){
		LocalDate initialDate, finalDate;
		if(maxDate == null || maxDate.trim().isEmpty()){
			finalDate = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		} else {
			finalDate = LocalDate.parse(maxDate);
		}
		if(minDate == null || minDate.trim().isEmpty()){
			initialDate = finalDate.minusYears(1L);
		} else {
			initialDate =LocalDate.parse(minDate);
		}
		Page<SalesRepportMinProjection> result = repository.searchSalesRepport(initialDate,finalDate,name, pageable);
		return result.map(x -> new SalesRepportMinDTO(x));
	}

	public List<SummaryMinDTO> findSummary(String minDate, String maxDate, String name){
		LocalDate initialDate, finalDate;
		if(maxDate == null || maxDate.trim().isEmpty()){
			finalDate = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
		} else {
			finalDate = LocalDate.parse(maxDate);
		}
		if(minDate == null || minDate.trim().isEmpty()){
			initialDate = finalDate.minusYears(1L);
		} else {
			initialDate =LocalDate.parse(minDate);
		}

		List<SummaryMinProjection> result = repository.searchSummary(initialDate,finalDate,name);
		return result.stream().map(x -> new SummaryMinDTO(x)).collect(Collectors.toList());
	}
}

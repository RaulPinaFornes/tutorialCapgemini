package com.ccsw.tutorial.lending;

import java.util.List;

import org.springframework.data.domain.Page;

import com.ccsw.tutorial.lending.model.Lending;
import com.ccsw.tutorial.lending.model.LendingDto;
import com.ccsw.tutorial.lending.model.LendingResponseDto;
import com.ccsw.tutorial.lending.model.LendingSearchDto;

public interface LendingService {

    public Page<Lending> findPage(LendingSearchDto dto);

    public void delete(Long id);

    public List<Lending> findAll();

    public LendingResponseDto save(LendingDto dto);
}

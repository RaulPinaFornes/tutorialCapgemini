package com.ccsw.tutorial.lending;

import java.util.List;
import java.util.stream.Collectors;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ccsw.tutorial.lending.model.Lending;
import com.ccsw.tutorial.lending.model.LendingDto;
import com.ccsw.tutorial.lending.model.LendingResponseDto;
import com.ccsw.tutorial.lending.model.LendingSearchDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Lending", description = "Api of Lendings")
@RequestMapping(value = "/lending")
@RestController
@CrossOrigin(origins = "*")
public class LendingController {

    @Autowired
    LendingService lendingService;

    @Autowired
    DozerBeanMapper mapper;

    @Operation(summary = "Find", description = "Method that return a list of Lendings")
    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<LendingDto> findAll() {
        List<Lending> listLending = lendingService.findAll();
        List<LendingDto> listLendingDto = listLending.stream().map(e -> mapper.map(e, LendingDto.class))
                .collect(Collectors.toList());

        return listLendingDto;
    }

    @Operation(summary = "Find page", description = "Method that return a page of Lendings")
    @RequestMapping(path = "", method = RequestMethod.POST)
    public Page<LendingDto> findPage(@RequestBody LendingSearchDto dto) {
        System.out.println(dto);
        Page<Lending> page = lendingService.findPage(dto);

        return new PageImpl<>(
                page.getContent().stream().map(e -> mapper.map(e, LendingDto.class)).collect(Collectors.toList()),
                page.getPageable(), page.getTotalElements());
    }

    @Operation(summary = "Delete", description = "Method that deletes a Lending")
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable("id") Long id) {
        lendingService.delete(id);
    }

    @Operation(summary = "Create", description = "Create a new Lending")
    @RequestMapping(path = "", method = RequestMethod.PUT)
    public LendingResponseDto save(@RequestBody LendingDto dto) {
        return this.lendingService.save(dto);
    }

}

package com.ccsw.tutorial.lending;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import com.ccsw.tutorial.lending.model.Lending;

public interface LendingRepository extends CrudRepository<Lending, Long>, JpaSpecificationExecutor<Lending> {

    @Override
    @EntityGraph(attributePaths = { "game", "client" })
    Page<Lending> findAll(Specification<Lending> spce, Pageable pageable);

    @Override
    @EntityGraph(attributePaths = { "game", "client" })
    List<Lending> findAll(Specification<Lending> spce);
}

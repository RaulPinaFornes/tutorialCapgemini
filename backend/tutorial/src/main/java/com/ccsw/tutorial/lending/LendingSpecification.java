package com.ccsw.tutorial.lending;

import java.util.Date;

import org.springframework.data.jpa.domain.Specification;

import com.ccsw.tutorial.common.criteria.SearchCriteria;
import com.ccsw.tutorial.lending.model.Lending;
import com.ccsw.tutorial.lending.model.LendingDto;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class LendingSpecification implements Specification<Lending> {

    private static final long serialVersionUID = 1L;

    private final SearchCriteria criteria;

    public LendingSpecification(SearchCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public Predicate toPredicate(Root<Lending> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
        if (criteria.getValue() != null) {
            Path<String> path = getPath(root);

            if (criteria.getOperation().equalsIgnoreCase(">")) {

                return builder.greaterThan(path.as(Date.class), (Date) criteria.getValue());

            } else if (criteria.getOperation().equalsIgnoreCase(">=")) {

                return builder.greaterThanOrEqualTo(path.as(Date.class), (Date) criteria.getValue());
            } else if (criteria.getOperation().equalsIgnoreCase("<=")) {

                return builder.lessThanOrEqualTo(path.as(Date.class), (Date) criteria.getValue());
            } else if (criteria.getOperation().equalsIgnoreCase("<")) {

                return builder.lessThan(path.as(Date.class), (Date) criteria.getValue());
            } else if (criteria.getOperation().equalsIgnoreCase(":")) {
                return builder.equal(path, criteria.getValue());
            } else if (criteria.getOperation().equalsIgnoreCase("<>")) {

                return builder.between(path.as(Date.class), ((LendingDto) criteria.getValue()).getDateinit(),
                        ((LendingDto) criteria.getValue()).getDateend());
            }
        }
        return null;
    }

    private Path<String> getPath(Root<Lending> root) {
        String key = criteria.getKey();
        String[] split = key.split("[.]", 0);

        Path<String> expression = root.get(split[0]);
        for (int i = 1; i < split.length; i++) {
            expression = expression.get(split[i]);
        }

        return expression;
    }
}

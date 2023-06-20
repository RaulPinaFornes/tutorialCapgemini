package com.ccsw.tutorial.lending;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
            String pattern = "yyyy-MM-dd";
            SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);

            if (criteria.getOperation().equalsIgnoreCase(">")) {

                Date parsed = null;
                try {
                    parsed = dateFormat.parse((String) criteria.getValue());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                return builder.greaterThanOrEqualTo(path.as(Date.class), parsed);

            } else if (criteria.getOperation().equalsIgnoreCase("<")) {
                Date parsed = null;
                try {
                    parsed = dateFormat.parse((String) criteria.getValue());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return builder.lessThanOrEqualTo(path.as(Date.class), parsed);
            } else if (criteria.getOperation().equalsIgnoreCase(":")) {
                return builder.equal(path, criteria.getValue());
            } else if (criteria.getOperation().equalsIgnoreCase("<>")) {

                return builder.between(path, ((LendingDto) criteria.getValue()).getDateinit(),
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

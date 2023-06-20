package com.ccsw.tutorial.lending.model;

import com.ccsw.tutorial.common.pagination.PageableRequest;

public class LendingSearchDto {

    private LendingFilterDto lendingFilter;

    private PageableRequest pageable;

    public LendingFilterDto getLendingFilter() {
        return lendingFilter;
    }

    public void setLendingFilter(LendingFilterDto lendingFilter) {
        this.lendingFilter = lendingFilter;
    }

    public PageableRequest getPageable() {
        return pageable;
    }

    public void setPageable(PageableRequest pageable) {
        this.pageable = pageable;
    }

}

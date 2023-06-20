package com.ccsw.tutorial.lending.model;

import java.util.Date;

public class LendingFilterDto {

    private Long clientId;

    private Date date;

    private Long titleId;

    public LendingFilterDto(Long clientId, Date date, Long titleId) {
        this.clientId = clientId;
        this.date = date;
        this.titleId = titleId;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getTitleId() {
        return titleId;
    }

    public void setTitleId(Long titleId) {
        this.titleId = titleId;
    }

}

package com.ccsw.tutorial.lending.model;

public class LendingFilterDto {

    private Long clientId;

    private String date;

    private Long titleId;

    public LendingFilterDto(Long clientId, String date, Long titleId) {
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getTitleId() {
        return titleId;
    }

    public void setTitleId(Long titleId) {
        this.titleId = titleId;
    }

}

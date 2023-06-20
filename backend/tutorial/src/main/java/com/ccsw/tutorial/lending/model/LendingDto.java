package com.ccsw.tutorial.lending.model;

import java.util.Date;

import com.ccsw.tutorial.client.model.ClientDto;
import com.ccsw.tutorial.game.model.GameDto;

public class LendingDto {

    private Long id;
    private GameDto game;
    private ClientDto client;
    private Date dateinit;
    private Date dateend;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GameDto getGame() {
        return game;
    }

    public void setGame(GameDto game) {
        this.game = game;
    }

    public ClientDto getClient() {
        return client;
    }

    public void setClient(ClientDto client) {
        this.client = client;
    }

    public Date getDateinit() {
        return dateinit;
    }

    public void setDateinit(Date dateinit) {
        this.dateinit = dateinit;
    }

    public Date getDateend() {
        return dateend;
    }

    public void setDateend(Date dateend) {
        this.dateend = dateend;
    }

}

package com.ccsw.tutorial.lending.model;

import com.ccsw.tutorial.client.model.ClientDto;
import com.ccsw.tutorial.game.model.GameDto;

public class LendingDto {

    private Long id;
    private GameDto game;
    private ClientDto client;
    private String dateinit;
    private String dateend;

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

    public String getDateinit() {
        return dateinit;
    }

    public void setDateinit(String dateinit) {
        this.dateinit = dateinit;
    }

    public String getDateend() {
        return dateend;
    }

    public void setDateend(String dataend) {
        this.dateend = dataend;
    }

}

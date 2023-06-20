package com.ccsw.tutorial.lending.model;

import java.time.LocalDate;

import com.ccsw.tutorial.client.model.Client;
import com.ccsw.tutorial.game.model.Game;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "lending")
public class Lending {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @Column(name = "dateinit", nullable = false)
    private LocalDate dateinit;

    @Column(name = "dateend", nullable = false)
    private LocalDate dateend;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game gameid) {
        this.game = gameid;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public LocalDate getDateinit() {
        return dateinit;
    }

    public void setDateinit(LocalDate dateinit) {
        this.dateinit = dateinit;
    }

    public LocalDate getDateend() {
        return dateend;
    }

    public void setDateend(LocalDate dateend) {
        this.dateend = dateend;
    }

}

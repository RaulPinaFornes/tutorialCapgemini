package com.ccsw.tutorial.client;

import java.util.List;

import com.ccsw.tutorial.client.model.Client;
import com.ccsw.tutorial.client.model.ClientDto;

public interface ClientService {

    public List<Client> findAll();

    public boolean save(Long id, ClientDto dto);

    public void delete(Long id);

    public Client get(Long id);
}

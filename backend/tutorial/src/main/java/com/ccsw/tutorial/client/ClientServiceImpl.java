package com.ccsw.tutorial.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ccsw.tutorial.client.model.Client;
import com.ccsw.tutorial.client.model.ClientDto;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ClientServiceImpl implements ClientService {

    @Autowired
    ClientRepository clientRepository;

    @Override
    public List<Client> findAll() {

        return (List<Client>) this.clientRepository.findAll();
    }

    @Override
    public boolean save(Long id, ClientDto dto) {

        Client client = null;
        Client clientSearch = this.findAll().stream().filter(e -> e.getName().equals(dto.getName())).findFirst()
                .orElse(null);
        if (clientSearch != null)
            return false;
        if (id == null) {
            client = new Client();
        } else {
            client = this.clientRepository.findById(id).orElse(null);
        }

        client.setName(dto.getName());

        this.clientRepository.save(client);
        return true;
    }

    @Override
    public void delete(Long id) {
        this.clientRepository.deleteById(id);
    }

    @Override
    public Client get(Long id) {
        return this.clientRepository.findById(id).orElse(null);
    }

}

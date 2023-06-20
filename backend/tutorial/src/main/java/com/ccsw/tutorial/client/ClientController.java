package com.ccsw.tutorial.client;

import java.util.List;
import java.util.stream.Collectors;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ccsw.tutorial.client.model.Client;
import com.ccsw.tutorial.client.model.ClientDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Client", description = "API of Client")
@RequestMapping(value = "/client")
@RestController
@CrossOrigin(origins = "*")
public class ClientController {

    @Autowired
    ClientService clientService;

    @Autowired
    DozerBeanMapper mapper;

    @Operation(summary = "Find", description = "Method that returns a list of Clients")
    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<ClientDto> findAll() {
        List<Client> clients = this.clientService.findAll();

        return clients.stream().map(e -> mapper.map(e, ClientDto.class)).collect(Collectors.toList());
    }

    @Operation(summary = "Save or Update a Client", description = "Method that saves or updates a Client")
    @RequestMapping(path = { "", "/{id}" }, method = RequestMethod.PUT)
    public boolean save(@PathVariable(name = "id", required = false) Long id, @RequestBody ClientDto dto) {

        return this.clientService.save(id, dto);

    }

    @Operation(summary = "Delete", description = "Method that deletes a client by id")
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable(name = "id", required = true) Long id) {
        this.clientService.delete(id);
    }

}

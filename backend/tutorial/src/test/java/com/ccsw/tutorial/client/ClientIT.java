package com.ccsw.tutorial.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import com.ccsw.tutorial.client.model.ClientDto;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ClientIT {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    ParameterizedTypeReference<List<ClientDto>> responseType = new ParameterizedTypeReference<List<ClientDto>>() {
    };

    public static final String SERVICE_PATH = "/client";
    public static final String LOCAL_HOST = "http://localhost:";
    public static final int TOTAL_CLIENTS = 5;
    public static final int TOTAL_CLIENTS_AFTER_DELETE = 4;
    public static final String NEW_CLIENT_NAME = "Nuevo cliente";
    public static final Long NEW_CLIENT_ID = 6L;
    public static final Long MODIFY_CLIENT_ID = 1L;
    public static final Long DELETE_CLIENT_ID = 1L;

    public static final String NAME_CLIENT_EXIST = "Cliente 1";
    public static final Long ID_CLIENT_EXIST = 5L;

    @Test
    public void findAllShouldReturnAllClient() {

        ResponseEntity<List<ClientDto>> response = restTemplate.exchange(LOCAL_HOST + port + SERVICE_PATH,
                HttpMethod.GET, null, responseType);

        assertNotNull(response);
        assertEquals(TOTAL_CLIENTS, response.getBody().size());
    }

    @Test
    public void saveWithoutIdShouldCreateNewClient() {

        ClientDto dto = new ClientDto();
        dto.setName(NEW_CLIENT_NAME);

        restTemplate.exchange(LOCAL_HOST + port + SERVICE_PATH, HttpMethod.PUT, new HttpEntity<>(dto), Void.class);

        ResponseEntity<List<ClientDto>> response = restTemplate.exchange(LOCAL_HOST + port + SERVICE_PATH,
                HttpMethod.GET, null, responseType);

        ClientDto clientSearch = response.getBody().stream().filter(item -> item.getId().equals(NEW_CLIENT_ID))
                .findFirst().orElse(null);

        assertNotNull(clientSearch);
        assertEquals(NEW_CLIENT_NAME, clientSearch.getName());
    }

    @Test
    public void modifyWithExistsIdShouldModifyClient() {
        ClientDto dto = new ClientDto();
        dto.setName(NEW_CLIENT_NAME);

        restTemplate.exchange(LOCAL_HOST + port + SERVICE_PATH + "/" + MODIFY_CLIENT_ID, HttpMethod.PUT,
                new HttpEntity<>(dto), Void.class);

        ResponseEntity<List<ClientDto>> response = restTemplate.exchange(LOCAL_HOST + port + SERVICE_PATH,
                HttpMethod.GET, null, responseType);

        assertNotNull(response);
        assertEquals(TOTAL_CLIENTS, response.getBody().size());

        ClientDto clientSearch = response.getBody().stream().filter(item -> item.getName().equals(NEW_CLIENT_NAME))
                .findFirst().orElse(null);

        assertNotNull(clientSearch);
        assertEquals(MODIFY_CLIENT_ID, clientSearch.getId());
    }

    @Test
    public void deleteShouldEliminateClientById() {
        restTemplate.exchange(LOCAL_HOST + port + SERVICE_PATH + "/" + DELETE_CLIENT_ID, HttpMethod.DELETE, null,
                responseType);

        ResponseEntity<List<ClientDto>> response = restTemplate.exchange(LOCAL_HOST + port + SERVICE_PATH,
                HttpMethod.GET, null, responseType);

        assertNotNull(response);
        assertEquals(TOTAL_CLIENTS_AFTER_DELETE, response.getBody().size());
    }

    @Test
    public void saveWithExistsNameShouldReturnFalse() {
        ClientDto dto = new ClientDto();
        dto.setName(NAME_CLIENT_EXIST);

        ResponseEntity<Boolean> response = restTemplate.exchange(
                LOCAL_HOST + port + SERVICE_PATH + "/" + ID_CLIENT_EXIST, HttpMethod.PUT, new HttpEntity<>(dto),
                Boolean.class);

        assertNotNull(response);
        assertEquals(true, response.getBody());

    }
}

package com.ccsw.tutorial.lending;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.ccsw.tutorial.common.pagination.PageableRequest;
import com.ccsw.tutorial.config.ResponsePage;
import com.ccsw.tutorial.game.model.GameDto;
import com.ccsw.tutorial.lending.model.LendingDto;
import com.ccsw.tutorial.lending.model.LendingFilterDto;
import com.ccsw.tutorial.lending.model.LendingSearchDto;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class LendingIT {

    public static final String LOCALHOST = "http://localhost:";
    public static final String SERVICE_PATH = "/lending";
    public static final int PAGE_SIZE = 3;
    public static final int LENDING_MAX_SIZE = 3;
    public static final int LENDING_ID_DELETE = 1;

    public static final Long LENDING_FILTER_TITLE = 1L;
    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static final String LENDING_FILTER_DATE = "2023-06-16";

    public static final Long LENDING_FILTER_CLIENT = 1L;

    public static final String LENDING_FILTER_DATE_FIRST_DAY = "2023-06-15";

    public static final String LENDING_FILTER_DATE_LAST_DAY = "2023-06-18";
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    ParameterizedTypeReference<ResponsePage<LendingDto>> responseTypePage = new ParameterizedTypeReference<ResponsePage<LendingDto>>() {
    };
    ParameterizedTypeReference<List<LendingDto>> responseTypeList = new ParameterizedTypeReference<List<LendingDto>>() {
    };

    @Test
    public void findAllShouldReturnAllResult() {
        restTemplate.exchange(LOCALHOST + port + SERVICE_PATH + "/" + LENDING_ID_DELETE, HttpMethod.DELETE, null,
                responseTypeList);
        ResponseEntity<List<LendingDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH,
                HttpMethod.GET, null, responseTypeList);
        assertNotNull(response);
        assertEquals(LENDING_MAX_SIZE - 1, response.getBody().size());
    }

    @Test
    public void findFirstPageWithThreeSizeWithoutFilterShouldReturnThreeResult() {

        LendingSearchDto searchDto = new LendingSearchDto();
        searchDto.setLendingFilter(new LendingFilterDto(null, null, null));
        searchDto.setPageable(new PageableRequest(0, PAGE_SIZE));

        ResponseEntity<ResponsePage<LendingDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH,
                HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response);
        assertEquals(PAGE_SIZE, response.getBody().getContent().size());

    }

    @Test
    public void findFirstPageWithThreeSizeWithFilterTitleShouldReturnOneResult() {

        LendingSearchDto searchDto = new LendingSearchDto();
        searchDto.setLendingFilter(new LendingFilterDto(null, null, LENDING_FILTER_TITLE));
        searchDto.setPageable(new PageableRequest(0, PAGE_SIZE));

        ResponseEntity<ResponsePage<LendingDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH,
                HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response);
        assertEquals(1, response.getBody().getContent().size());
    }

    @Test
    public void findFirstPageWithThreeSizeWithFilterClientShouldReturnThreeResult() {
        LendingSearchDto searchDto = new LendingSearchDto();
        searchDto.setLendingFilter(new LendingFilterDto(LENDING_FILTER_CLIENT, null, null));
        searchDto.setPageable(new PageableRequest(0, PAGE_SIZE));

        ResponseEntity<ResponsePage<LendingDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH,
                HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response);
        assertEquals(1, response.getBody().getContent().size());
    }

    @Test
    public void findFirstPageWithThreeSizeWithFilterDateShouldReturnOneResult() throws ParseException {
        LendingSearchDto searchDto = new LendingSearchDto();
        searchDto.setLendingFilter(new LendingFilterDto(null, dateFormat.parse(LENDING_FILTER_DATE), null));
        searchDto.setPageable(new PageableRequest(0, PAGE_SIZE));

        ResponseEntity<ResponsePage<LendingDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH,
                HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response);
        assertEquals(1, response.getBody().getContent().size());
    }

    @Test
    public void findFirstPageWithThreeSizeWithFirstDayDateShouldReturnOneResult() throws ParseException {
        LendingSearchDto searchDto = new LendingSearchDto();
        searchDto.setLendingFilter(new LendingFilterDto(null, dateFormat.parse(LENDING_FILTER_DATE_FIRST_DAY), null));
        searchDto.setPageable(new PageableRequest(0, PAGE_SIZE));

        ResponseEntity<ResponsePage<LendingDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH,
                HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response);
        assertEquals(1, response.getBody().getContent().size());
    }

    @Test
    public void findFirstPageWithThreeSizeWithLastDayDateShouldReturnOneResult() throws ParseException {
        LendingSearchDto searchDto = new LendingSearchDto();
        searchDto.setLendingFilter(new LendingFilterDto(null, dateFormat.parse(LENDING_FILTER_DATE_LAST_DAY), null));
        searchDto.setPageable(new PageableRequest(0, PAGE_SIZE));

        ResponseEntity<ResponsePage<LendingDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH,
                HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response);
        assertEquals(1, response.getBody().getContent().size());
    }

    @Test
    public void findFirstPageWithThreeSizeWithAllFiltersShouldReturnOneResult() throws ParseException {
        LendingSearchDto searchDto = new LendingSearchDto();
        searchDto.setLendingFilter(new LendingFilterDto(LENDING_FILTER_CLIENT, dateFormat.parse(LENDING_FILTER_DATE),
                LENDING_FILTER_TITLE));
        searchDto.setPageable(new PageableRequest(0, PAGE_SIZE));

        ResponseEntity<ResponsePage<LendingDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH,
                HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response);
        assertEquals(1, response.getBody().getContent().size());
    }

    @Test
    public void saveShouldCreateNewLending() throws ParseException {
        long newMaxSize = LENDING_MAX_SIZE + 1L;
        int newPageSize = PAGE_SIZE + 1;
        GameDto game = new GameDto();
        LendingDto dto = new LendingDto();
        ClientDto client = new ClientDto();
        client.setId(1L);
        game.setId(1L);

        dto.setClient(client);
        dto.setGame(game);

        dto.setDateinit(dateFormat.parse("2023-12-12"));
        dto.setDateend(dateFormat.parse("2023-12-14"));

        restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.PUT, new HttpEntity<>(dto), Void.class);

        LendingSearchDto searchDto = new LendingSearchDto();
        searchDto.setLendingFilter(new LendingFilterDto(null, null, null));
        searchDto.setPageable(new PageableRequest(0, newPageSize));

        ResponseEntity<ResponsePage<LendingDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH,
                HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response);
        assertEquals(newMaxSize, response.getBody().getContent().size());

    }

    @Test
    public void saveWithGameAllreadyLendShouldNotCreateNewLending() throws ParseException {
        long newMaxSize = LENDING_MAX_SIZE + 1L;
        int newPageSize = PAGE_SIZE + 1;
        GameDto game = new GameDto();
        LendingDto dto = new LendingDto();
        ClientDto client = new ClientDto();
        client.setId(1L);
        game.setId(1L);
        dto.setClient(client);
        dto.setGame(game);

        dto.setDateinit(dateFormat.parse("2025-12-12"));
        dto.setDateend(dateFormat.parse("2025-12-14"));

        restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.PUT, new HttpEntity<>(dto), Void.class);

        dto.setDateinit(dateFormat.parse("2025-12-12"));
        dto.setDateend(dateFormat.parse("2025-12-14"));
        restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.PUT, new HttpEntity<>(dto), Void.class);

        LendingSearchDto searchDto = new LendingSearchDto();
        searchDto.setLendingFilter(new LendingFilterDto(null, null, null));
        searchDto.setPageable(new PageableRequest(0, newPageSize));

        ResponseEntity<ResponsePage<LendingDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH,
                HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response);
        assertEquals(newMaxSize, response.getBody().getContent().size());

    }

    @Test
    public void saveWithClientWithTwoGamesShouldNotCreateNewLending() throws ParseException {
        long newMaxSize = LENDING_MAX_SIZE + 2L;
        int newPageSize = PAGE_SIZE + 2;

        GameDto game = new GameDto();
        LendingDto dto = new LendingDto();
        ClientDto client = new ClientDto();
        client.setId(1L);
        game.setId(1L);
        dto.setClient(client);
        dto.setGame(game);
        dto.setDateinit(dateFormat.parse("2023-12-12"));
        dto.setDateend(dateFormat.parse("2023-12-15"));

        restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.PUT, new HttpEntity<>(dto), Void.class);
        game.setId(2L);
        dto.setDateinit(dateFormat.parse("2023-12-12"));
        dto.setDateend(dateFormat.parse("2023-12-15"));

        restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.PUT, new HttpEntity<>(dto), Void.class);
        game.setId(3L);
        dto.setDateinit(dateFormat.parse("2023-12-12"));
        dto.setDateend(dateFormat.parse("2023-12-15"));

        restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.PUT, new HttpEntity<>(dto), Void.class);

        LendingSearchDto searchDto = new LendingSearchDto();
        searchDto.setLendingFilter(new LendingFilterDto(null, null, null));
        searchDto.setPageable(new PageableRequest(0, newPageSize));

        ResponseEntity<ResponsePage<LendingDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH,
                HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response);
        assertEquals(newMaxSize, response.getBody().getContent().size());

    }

}

package com.ccsw.tutorial.loan;

import com.ccsw.tutorial.author.AuthorService;
import com.ccsw.tutorial.author.model.AuthorDto;
import com.ccsw.tutorial.author.model.AuthorSearchDto;
import com.ccsw.tutorial.category.CategoryService;
import com.ccsw.tutorial.client.ClientService;
import com.ccsw.tutorial.client.model.Client;
import com.ccsw.tutorial.client.model.ClientDto;
import com.ccsw.tutorial.common.pagination.PageableRequest;
import com.ccsw.tutorial.config.ResponsePage;
import com.ccsw.tutorial.game.GameService;
import com.ccsw.tutorial.game.model.Game;
import com.ccsw.tutorial.game.model.GameDto;
import com.ccsw.tutorial.loan.model.LoanDto;
import com.ccsw.tutorial.loan.model.LoanSearchDto;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class LoanIT {

    public static final String LOCALHOST = "http://localhost:";
    public static final String SERVICE_PATH = "/loan";

    private static final String NOT_EXISTS_GAME_TITLE = "NotExists";
    private static final Long NOT_EXISTS_GAME_ID = 7L;
    private static final Long EXISTS_GAME_ID = 1L;
    private static final String EXPECTED_GAME_TITLE = "Azul";
    private static final Long NOT_EXISTS_CLIENT_ID = 7L;
    private static final String NOT_EXISTS_CLIENT_NAME = "NotExists";
    private static final Long EXISTS_CLIENT_ID = 1L;
    private static final String EXPECTED_CLIENT_NAME = "Marta Alcalá";

    private static final String NEW_LOAN_DATE = "2026-09-20";
    private static final String NEW_RETURN_DATE = "2026-09-30";

    private static final Long MODIFY_LOAN_ID = 6L;

    private static final int TOTAL_LOANS = 7;
    private static final Long LAST_GAMES_ID = 6L;
    private static final Long LAST_CLIENTS_ID = 6L;
    private static final int PAGE_SIZE = 5;

    @Mock
    private GameService gameService;

    @Mock
    private ClientService clientService;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    ParameterizedTypeReference<ResponsePage<LoanDto>> responseTypePage = new ParameterizedTypeReference<ResponsePage<LoanDto>>(){};

    @Test
    public void findFirstPageWithFiveSizeShouldReturnFirstFiveResults() {

        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setPageable(new PageableRequest(0, PAGE_SIZE));

        ResponseEntity<ResponsePage<LoanDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response);
        assertEquals(TOTAL_LOANS, response.getBody().getTotalElements());
        assertEquals(PAGE_SIZE, response.getBody().getContent().size());
    }

    @Test
    public void findSecondPageWithFiveSizeShouldReturnLastTwoResults() {

        int elementsCount = TOTAL_LOANS - PAGE_SIZE;

        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setPageable(new PageableRequest(1, PAGE_SIZE));

        ResponseEntity<ResponsePage<LoanDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response);
        assertEquals(TOTAL_LOANS, response.getBody().getTotalElements());
        assertEquals(elementsCount, response.getBody().getContent().size());
    }

    @Test
    public void findExistsTitleShouldReturnGames() {

        int GAMES_WITH_FILTER = 1;

        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setPageable(new PageableRequest(0, PAGE_SIZE));
        searchDto.setGameId(EXISTS_GAME_ID);

        ResponseEntity<ResponsePage<LoanDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response);
        assertEquals(GAMES_WITH_FILTER, response.getBody().getContent().size());
    }

    @Test
    public void findExistsClientShouldReturnGames() {

        int GAMES_WITH_FILTER = 2;

        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setPageable(new PageableRequest(0, PAGE_SIZE));
        searchDto.setClientId(EXISTS_CLIENT_ID);

        ResponseEntity<ResponsePage<LoanDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response);
        assertEquals(GAMES_WITH_FILTER, response.getBody().getContent().size());
    }

    @Test
    public void findExistsTitleAndClientShouldReturnGames() {

        int GAMES_WITH_FILTER = 1;

        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setPageable(new PageableRequest(0, PAGE_SIZE));
        searchDto.setClientId(EXISTS_CLIENT_ID);
        searchDto.setGameId(EXISTS_GAME_ID);

        ResponseEntity<ResponsePage<LoanDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response);
        assertEquals(GAMES_WITH_FILTER, response.getBody().getContent().size());
    }

    @Test
    public void findNotExistsTitleShouldReturnEmpty() {

        int GAMES_WITH_FILTER = 0;

        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setPageable(new PageableRequest(0, PAGE_SIZE));
        searchDto.setGameId(NOT_EXISTS_GAME_ID);

        ResponseEntity<ResponsePage<LoanDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response);
        assertEquals(GAMES_WITH_FILTER, response.getBody().getContent().size());
    }

    @Test
    public void findNotExistsClientShouldReturnEmpty() {

        int GAMES_WITH_FILTER = 0;

        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setPageable(new PageableRequest(0, PAGE_SIZE));
        searchDto.setClientId(NOT_EXISTS_CLIENT_ID);

        ResponseEntity<ResponsePage<LoanDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response);
        assertEquals(GAMES_WITH_FILTER, response.getBody().getContent().size());
    }

    @Test
    public void findNotExistsTitleOrClientShouldReturnEmpty() {

        int GAMES_WITH_FILTER = 0;

        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setPageable(new PageableRequest(0, PAGE_SIZE));
        searchDto.setClientId(NOT_EXISTS_CLIENT_ID);
        searchDto.setGameId(NOT_EXISTS_GAME_ID);

        ResponseEntity<ResponsePage<LoanDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);
        assertNotNull(response);
        assertEquals(GAMES_WITH_FILTER, response.getBody().getContent().size());

        LoanSearchDto searchDtoC = new LoanSearchDto();
        searchDtoC.setPageable(new PageableRequest(0, PAGE_SIZE));
        searchDtoC.setClientId(NOT_EXISTS_CLIENT_ID);

        ResponseEntity<ResponsePage<LoanDto>> responseC = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);
        assertNotNull(responseC);
        assertEquals(GAMES_WITH_FILTER, responseC.getBody().getContent().size());

        LoanSearchDto searchDtoG = new LoanSearchDto();
        searchDtoG.setPageable(new PageableRequest(0, PAGE_SIZE));
        searchDto.setGameId(NOT_EXISTS_GAME_ID);

        ResponseEntity<ResponsePage<LoanDto>> responseG = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);
        assertNotNull(responseG);
        assertEquals(GAMES_WITH_FILTER, responseG.getBody().getContent().size());
    }

    @Test
    public void saveWithoutIdShouldCreateNewLoan() {

        long newLoanId = TOTAL_LOANS + 1;
        long newLoanSize = TOTAL_LOANS + 1;

        LoanDto dto = new LoanDto();
        dto.setLoanDate(LocalDate.parse(NEW_LOAN_DATE));
        dto.setReturnDate(LocalDate.parse(NEW_RETURN_DATE));
        Client client = new Client();
        Game game = new Game();

        ClientDto clientD = new ClientDto();
        clientD.setId(LAST_CLIENTS_ID);
        dto.setClient(clientD);

        GameDto gameD = new GameDto();
        gameD.setId(LAST_GAMES_ID);
        dto.setGame(gameD);

        client.setId(LAST_CLIENTS_ID);
        game.setId(LAST_GAMES_ID);

        when(clientService.get(anyLong())).thenReturn(client);
        when(gameService.get(anyLong())).thenReturn(game);

        restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.PUT, new HttpEntity<>(dto), Void.class);

        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setPageable(new PageableRequest(0, (int) newLoanSize));

        ResponseEntity<ResponsePage<LoanDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response);
        assertEquals(newLoanSize, response.getBody().getTotalElements());

        LoanDto loan = response.getBody().getContent().stream().filter(item -> item.getId().equals(newLoanId)).findFirst().orElse(null);
        assertNotNull(loan);
        assertEquals(EXPECTED_GAME_TITLE, loan.getGame().getTitle());
        assertEquals(EXPECTED_CLIENT_NAME, loan.getClient().getName());
    }

    @Test
    public void modifyWithExistsIdShouldModifyLoan() {

        LoanDto dto = new LoanDto();
        dto.setLoanDate(LocalDate.parse(NEW_LOAN_DATE));
        dto.setReturnDate(LocalDate.parse(NEW_RETURN_DATE));
        Client client = new Client();
        Game game = new Game();

        client.setId(LAST_CLIENTS_ID);
        game.setId(LAST_GAMES_ID);

        ClientDto clientD = new ClientDto();
        clientD.setId(LAST_CLIENTS_ID);
        dto.setClient(clientD);

        GameDto gameD = new GameDto();
        gameD.setId(LAST_GAMES_ID);
        dto.setGame(gameD);

        when(clientService.get(anyLong())).thenReturn(client);
        when(gameService.get(anyLong())).thenReturn(game);

        restTemplate.exchange(LOCALHOST + port + SERVICE_PATH + "/" + MODIFY_LOAN_ID, HttpMethod.PUT, new HttpEntity<>(dto), Void.class);

        LoanSearchDto searchDto = new LoanSearchDto();
        searchDto.setPageable(new PageableRequest(0, (int) TOTAL_LOANS));

        ResponseEntity<ResponsePage<LoanDto>> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH, HttpMethod.POST, new HttpEntity<>(searchDto), responseTypePage);

        assertNotNull(response);
        assertEquals(TOTAL_LOANS, response.getBody().getTotalElements());

        LoanDto loan = response.getBody().getContent().stream().filter(item -> item.getId().equals(MODIFY_LOAN_ID)).findFirst().orElse(null);
        assertNotNull(loan);
        assertEquals(EXPECTED_GAME_TITLE, loan.getGame().getTitle());
        assertEquals(EXPECTED_CLIENT_NAME, loan.getClient().getName());
    }

    @Test
    public void modifyWithNotExistsIdShouldThrowException() {

        long modifyLoanId = TOTAL_LOANS + 1;

        LoanDto dto = new LoanDto();

        ResponseEntity<?> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH + "/" + modifyLoanId, HttpMethod.PUT, new HttpEntity<>(dto), Void.class);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void deleteWithNotExistsIdShouldThrowException() {

        long deleteLoanId = TOTAL_LOANS + 1;

        ResponseEntity<?> response = restTemplate.exchange(LOCALHOST + port + SERVICE_PATH + "/" + deleteLoanId, HttpMethod.DELETE, null, Void.class);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

}
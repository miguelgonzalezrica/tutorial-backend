package com.ccsw.tutorial.loan;

import com.ccsw.tutorial.author.model.AuthorDto;
import com.ccsw.tutorial.client.ClientRepository;
import com.ccsw.tutorial.client.ClientService;
import com.ccsw.tutorial.client.model.Client;
import com.ccsw.tutorial.client.model.ClientDto;
import com.ccsw.tutorial.common.pagination.PageableRequest;
import com.ccsw.tutorial.game.GameRepository;
import com.ccsw.tutorial.game.GameService;
import com.ccsw.tutorial.loan.LoanServiceImpl;
import com.ccsw.tutorial.loan.model.Loan;
import com.ccsw.tutorial.loan.model.LoanDto;
import com.ccsw.tutorial.loan.LoanService;
import com.ccsw.tutorial.category.CategoryService;
import com.ccsw.tutorial.category.CategoryServiceImpl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.ccsw.tutorial.category.model.Category;
import com.ccsw.tutorial.category.model.CategoryDto;
import com.ccsw.tutorial.game.model.Game;
import com.ccsw.tutorial.game.model.GameDto;
import com.ccsw.tutorial.loan.model.LoanSearchDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LoanTest {

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private ClientService clientService;

    @Mock
    private GameService gameService;

    @InjectMocks
    private LoanServiceImpl loanService;

    public static final Long NOT_EXISTS_LOAN_ID = 0L;

    @Test
    public void getExistsLoanIdShouldReturnLoan() {

        Loan loan = mock(Loan.class);
        when(loan.getId()).thenReturn(EXISTS_LOAN_ID);
        when(loanRepository.findById(EXISTS_LOAN_ID)).thenReturn(Optional.of(loan));

        Loan loanResponse = loanService.get(EXISTS_LOAN_ID);

        assertNotNull(loanResponse);
        assertEquals(EXISTS_LOAN_ID, loan.getId());
    }

    @Test
    public void getNotExistsLoanIdShouldReturnNull() {

        when(loanRepository.findById(NOT_EXISTS_LOAN_ID)).thenReturn(Optional.empty());

        Loan loan = loanService.get(NOT_EXISTS_LOAN_ID);

        assertNull(loan);
    }

    public static final String LOAN_GAME_NAME = "Catan II";

    private LoanDto createLoanDto() {

        LoanDto loanDto = new LoanDto();

        ClientDto client = new ClientDto();
        client.setId(1L);

        GameDto game = new GameDto();
        game.setId(1L);

        loanDto.setGame(game);
        loanDto.setClient(client);

        loanDto.setLoanDate(LocalDate.parse("2026-08-05"));
        loanDto.setReturnDate(LocalDate.parse("2026-08-05"));

        return loanDto;
    }

    @Test
    public void saveNotExistsLoanIdShouldInsert() {

        LoanDto loanDto = createLoanDto();
        ArgumentCaptor<Loan> loan = ArgumentCaptor.forClass(Loan.class);

        Client client = new Client();
        client.setId(1L);
        Game game = new Game();
        game.setId(1L);
        game.setTitle(LOAN_GAME_NAME);
        when(clientService.get(anyLong())).thenReturn(client);
        when(gameService.get(anyLong())).thenReturn(game);

        loanService.save(null, loanDto);

        verify(loanRepository).save(loan.capture());

        assertEquals(LOAN_GAME_NAME, loan.getValue().getGame().getTitle());
    }

    public static final Long EXISTS_LOAN_ID = 1L;

    @Test
    public void saveExistsLoanIdShouldUpdate() {

        LoanDto loanDto = createLoanDto();

        Loan loan = new Loan();

        when(loanRepository.findById(EXISTS_LOAN_ID)).thenReturn(Optional.of(loan));

        loanService.save(EXISTS_LOAN_ID, loanDto);

        verify(loanRepository).save(loan);
    }
}
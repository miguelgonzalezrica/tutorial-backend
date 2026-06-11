package com.ccsw.tutorial.loan;

import com.ccsw.tutorial.author.AuthorService;
import com.ccsw.tutorial.client.ClientService;
import com.ccsw.tutorial.common.criteria.SearchCriteria;
import com.ccsw.tutorial.client.ClientRepository;
import com.ccsw.tutorial.game.GameRepository;
import com.ccsw.tutorial.game.GameService;
import com.ccsw.tutorial.loan.model.Loan;
import com.ccsw.tutorial.loan.model.LoanDto;
import com.ccsw.tutorial.loan.model.LoanSearchDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * @author migonza
 *
 */
@Service
@Transactional
public class LoanServiceImpl implements LoanService {

    @Autowired
    LoanRepository loanRepository;

    @Autowired
    ClientService clientService;

    @Autowired
    GameService gameService;


    /**
     * {@inheritDoc}
     */
    @Override
    public Loan get(Long id) {

        return this.loanRepository.findById(id).orElse(null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<Loan> findPage(LoanSearchDto dto) {
        LoanSpecification gameNameSpec = new LoanSpecification(new SearchCriteria("game.id", ":", dto.getGameId()));
        LoanSpecification clientNameSpec = new LoanSpecification(new SearchCriteria("client.id", ":", dto.getClientId()));
        LoanSpecification loanDateSpec = new LoanSpecification(new SearchCriteria("loanDate", "<=", dto.getActiveDate()));
        LoanSpecification returnDateSpec = new LoanSpecification(new SearchCriteria("returnDate", ">=", dto.getActiveDate()));
        Specification<Loan> spec = gameNameSpec.and(clientNameSpec).and(loanDateSpec).and(returnDateSpec);
        return this.loanRepository.findAll(spec, dto.getPageable().getPageable());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(Long id, LoanDto data) {
        Loan loan;
        if (id == null) {
            loan = new Loan();
        } else {
            loan = this.get(id);
        }
        BeanUtils.copyProperties(data, loan, "id", "client", "game");

        loan.setClient(clientService.get(data.getClient().getId()));
        loan.setGame(gameService.get(data.getGame().getId()));

        LocalDate start = data.getLoanDate();
        LocalDate end = data.getReturnDate();

        if (start == null || end == null) {
            throw new RuntimeException("Las fechas de inicio y fin de préstamo son obligatorias");
        }

        if (end.isBefore(start)) {
            throw new RuntimeException("La fecha de devolución debe ser posterior o igual a la de recogida");
        }

        long days = ChronoUnit.DAYS.between(start, end);
        if (days > 14) {
            throw new RuntimeException("El alquiler no puede exceder los 14 días");
        }

        boolean gameOverlap = loanRepository.existsOverlappingGameLoan(data.getGame().getId(),id,start,end);

        if (gameOverlap) {
            throw new RuntimeException("El juego ya está prestado en ese rango de fechas");
        }

        for (LocalDate d = start; !d.isAfter(end); d = d.plusDays(1)) {
            long count = loanRepository.countClientLoansOnDate(data.getClient().getId(),id,d);
            if (count >= 2) {
                throw new RuntimeException("El cliente ya tiene dos juegos alquilados para ese día");
            }
        }

        this.loanRepository.save(loan);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(Long id) throws Exception {

        if(this.get(id) == null){
            throw new Exception("Not exists");
        }

        this.loanRepository.deleteById(id);
    }

}
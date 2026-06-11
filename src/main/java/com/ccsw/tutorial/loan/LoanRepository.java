package com.ccsw.tutorial.loan;

import com.ccsw.tutorial.game.model.Game;
import com.ccsw.tutorial.loan.model.Loan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

/**
 * @author migonza
 *
 */
public interface LoanRepository extends CrudRepository<Loan, Long>, JpaSpecificationExecutor<Loan> {

    @Query("""
        SELECT COUNT(l) > 0 FROM Loan l
        WHERE l.game.id = :gameId
        AND (:loanId IS NULL OR l.id <> :loanId)
        AND l.loanDate <= :endDate
        AND l.returnDate >= :startDate
    """)
    boolean existsOverlappingGameLoan(
            @Param("gameId") Long gameId,
            @Param("loanId") Long loanId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    @Query("""
        SELECT COUNT(l) FROM Loan l
        WHERE l.client.id = :clientId
        AND (:loanId IS NULL OR l.id <> :loanId)
        AND l.loanDate <= :date
        AND l.returnDate >= :date
    """)
    long countClientLoansOnDate(
            @Param("clientId") Long clientId,
            @Param("loanId") Long loanId,
            @Param("date") LocalDate date
    );
}
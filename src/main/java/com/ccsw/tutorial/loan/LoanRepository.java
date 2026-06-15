package com.ccsw.tutorial.loan;

import com.ccsw.tutorial.loan.model.Loan;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;

/**
 * @author migonza
 *
 */
public interface LoanRepository extends CrudRepository<Loan, Long>, JpaSpecificationExecutor<Loan> {

    boolean existsByGameIdAndLoanDateLessThanEqualAndReturnDateGreaterThanEqual(
            Long gameId,LocalDate endDate,LocalDate startDate);

    boolean existsByGameIdAndIdNotAndLoanDateLessThanEqualAndReturnDateGreaterThanEqual(
            Long gameId, Long id, LocalDate endDate, LocalDate startDate);

    int countByClientIdAndLoanDateLessThanEqualAndReturnDateGreaterThanEqual(
            Long clientId, LocalDate dateToCompareL, LocalDate dateToCompareH);

    int countByClientIdAndIdNotAndLoanDateLessThanEqualAndReturnDateGreaterThanEqual(
            Long clientId, Long id, LocalDate dateToCompareL, LocalDate dateToCompareH);
}
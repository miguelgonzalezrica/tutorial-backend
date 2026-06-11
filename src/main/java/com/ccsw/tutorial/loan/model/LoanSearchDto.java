package com.ccsw.tutorial.loan.model;

import com.ccsw.tutorial.common.pagination.PageableRequest;

import java.time.LocalDate;

/**
 * @author migonza
 *
 */
public class LoanSearchDto {

    private PageableRequest pageable;

    public PageableRequest getPageable() {
        return pageable;
    }

    public void setPageable(PageableRequest pageable) {
        this.pageable = pageable;
    }

    private Number gameId;
    private Number clientId;
    private LocalDate activeDate;

    public Number getGameId() {
        return gameId;
    }

    public void setGameId(Number gameId) {
        this.gameId = gameId;
    }

    public Number getClientId() {
        return clientId;
    }

    public void setClientId(Number clientId) {
        this.clientId = clientId;
    }

    public LocalDate getActiveDate() {
        return activeDate;
    }

    public void setActiveDate(LocalDate activeDate) {
        this.activeDate = activeDate;
    }
}
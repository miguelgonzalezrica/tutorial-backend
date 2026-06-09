package com.ccsw.tutorial.loan.model;

/**
 * @author migonza
 *
 */
public class LoanDto {

    private Long id;

    private String gameName;

    private String clientName;

    private String loanDate;

    private String returnDate;

    /**
     * @return id
     */
    public Long getId() {

        return this.id;
    }

    /**
     * @param id new value of {@link #getId}.
     */
    public void setId(Long id) {

        this.id = id;
    }

    /**
     * @return gameName
     */
    public String getGameName() {

        return this.gameName;
    }

    /**
     * @param gameName new value of {@link #getGameName}.
     */
    public void setGameName(String gameName) {

        this.gameName = gameName;
    }

    /**
     * @return clientName
     */
    public String getClientName() {

        return this.clientName;
    }

    /**
     * @param clientName new value of {@link #getClientName}.
     */
    public void setClientName(String clientName) {

        this.clientName = clientName;
    }

    /**
     * @return loanDate
     */
    public String getLoanDate() {

        return this.loanDate;
    }

    /**
     * @param loanDate new value of {@link #getLoanDate}.
     */
    public void setLoanDate(String loanDate) {

        this.loanDate = loanDate;
    }

    /**
     * @return returnDate
     */
    public String getReturnDate() {

        return this.returnDate;
    }

    /**
     * @param returnDate new value of {@link #getReturnDate}.
     */
    public void setReturnDate(String returnDate) {

        this.returnDate = returnDate;
    }

}
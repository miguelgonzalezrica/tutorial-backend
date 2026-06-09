package com.ccsw.tutorial.loan.model;

import jakarta.persistence.*;

import java.util.Date;

/**
 * @author migonza
 *
 */
@Entity
@Table(name = "loan")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "gameName", nullable = false)
    private String gameName;

    @Column(name = "clientName")
    private String clientName;

    @Column(name = "loanDate")
    private Date loanDate;

    @Column(name = "returnDate")
    private Date returnDate;

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
    public void setSameName(String gameName) {
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
    public Date getLoanDate() {
        return this.loanDate;
    }

    /**
     * @param loanDate new value of {@link #getLoanDate}.
     */
    public void setLoanDate(Date loanDate) {
        this.loanDate = loanDate;
    }

    /**
     * @return returnDate
     */
    public Date getReturnDate() {
        return this.returnDate;
    }

    /**
     * @param returnDate new value of {@link #getReturnDate}.
     */
    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }
}

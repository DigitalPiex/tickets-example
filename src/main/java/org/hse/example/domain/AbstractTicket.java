package org.hse.example.domain;

/**
 * Абстрактное представление билета
 */
public abstract class AbstractTicket implements Ticket {
    private Long number;

    /**
     * @return порядковый номер билета
     */
    @Override
    public Long getNumber() {
        return this.number;
    }
}

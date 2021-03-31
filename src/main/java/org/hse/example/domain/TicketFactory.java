package org.hse.example.domain;

/**
 * Фабрика, конструирующая билеты по номерам
 */
public interface TicketFactory {
    /**
     * @param number пордковый номер билета
     * @return билет как экземпляр, реализующий {@link Ticket}
     */
    Ticket createTicket(Long number);
}

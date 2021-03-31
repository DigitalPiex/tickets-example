package org.hse.example.domain;

/**
 * Интерфейс представляющий билет
 */
public interface Ticket {
    /**
     * @return порядковый номер билета
     */
    Long getNumber();

    /**
     * @return true, если билет счастливый и false в противном случае
     */
    boolean isLucky();
}

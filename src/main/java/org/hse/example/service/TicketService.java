package org.hse.example.service;

public interface TicketService {
    /**
     * Выполняет необходимые вычисления
     *
     * @return экземпляр {@link TicketService}
     */
    TicketService doWork();

    /**
     * Выводит результат работы объекта
     */
    default void printResult() {
        System.out.println(this.getResult());
    }

    /**
     * @return результат работы метода
     */
    String getResult();

}

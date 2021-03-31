package org.hse.example.service;

import lombok.AllArgsConstructor;
import org.hse.example.domain.Ticket;
import org.hse.example.domain.TicketFactory;

import java.util.Optional;
import java.util.stream.LongStream;

/**
 * Выполняет подсчёт счастливых билетов путём полного перебора
 */
@AllArgsConstructor
public class SimpleTicketCounter implements TicketService {

    private final TicketFactory ticketFactory;
    private final Long digitsQnty;
    private Long luckyTicketsQnty;

    /**
     * Выполняет необходимые вычисления
     *
     * @return экземпляр {@link TicketService}
     */
    @Override
    public TicketService doWork() {
        luckyTicketsQnty =
                LongStream
                        .range(0, (long) (Math.pow(10, digitsQnty) - 1))
                        .mapToObj(ticketFactory::createTicket)
                        .filter(Ticket::isLucky)
                        .count();

        return this;
    }

    /**
     * @return результат работы метода
     */
    @Override
    public String getResult() {
        String result = Optional
                .ofNullable(luckyTicketsQnty)
                .map(value -> String.format("Всего счастливых билетов получилось %d", value))
                .orElseThrow(() -> new IllegalStateException("Нечего выводить!"));
        luckyTicketsQnty = null;
        return result;
    }
}

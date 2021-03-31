package org.hse.example.service;

import lombok.AccessLevel;
import lombok.Getter;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.IntStream;

/**
 * Находит минимальное расстояние между счастливыми билетами
 */
public class NearestTickets implements TicketService {
    private int maxNumber;
    private int[] digits;
    private int ticket = 0;
    private int distance;

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    private Optional<Comparator<Integer>> optionalComparator = Optional.empty();

    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    @Getter(AccessLevel.PACKAGE)
    private Optional<Predicate<Integer>> optionalCondition = Optional.empty();

    /**
     * @param digitsQnty количество цифр в билете
     */
    private NearestTickets(int digitsQnty) {
        this.maxNumber = (int) (Math.pow(10, digitsQnty) - 1);
        this.digits = new int[digitsQnty];
        this.distance = this.maxNumber;
    }

    /**
     * @param digitsQnty количество цифр в билете
     * @param condition  дополнительное условие
     */
    private NearestTickets(int digitsQnty, Predicate<Integer> condition) {
        this(digitsQnty);
        this.optionalCondition = Optional.ofNullable(condition);
    }

    private NearestTickets(final Integer digitsQnty,
                          final Predicate<Integer> condition,
                          final Comparator<Integer> comparator) {
        this(digitsQnty, condition);
        this.optionalComparator = Optional.of(comparator);
    }

    /**
     * Выполняет необходимые вычисления
     *
     * @return экземпляр {@link TicketService}
     */
    @Override
    public TicketService doWork() {
        IntStream
                .rangeClosed(1, maxNumber)
                .filter(number ->
                        this.optionalCondition
                            .map(value -> value.test(number))
                            .orElse(true))
                .filter(this::isLucky)
                .forEach(this::processNumber);
        return this;
    }

    /**
     * Обрабатывает очередной номер
     *
     * @param number номер
     */
    private void processNumber(int number) {
        int currentDistance = number - ticket;
        if (optionalComparator
                .map(value -> value.compare(currentDistance, distance))
                .orElseGet(() -> currentDistance - distance) < 0) {
            distance = currentDistance;
            ticket = number;
        }
    }

    /**
     * @param ticket номер проверяемого билета
     * @return true, если билет счастливый
     */
    private boolean isLucky(int ticket) {
        Arrays.fill(this.digits, 0);
        for (int i = 0, nextNumber = ticket; nextNumber > 0; nextNumber /= 10, i++) {
            this.digits[i] = nextNumber % 10;
        }

        int firstSum =
                Arrays
                    .stream(this.digits, 0, this.digits.length / 2)
                    .sum();
        int lastSum =
                Arrays
                    .stream(this.digits, this.digits.length / 2, this.digits.length)
                    .sum();

        return lastSum == firstSum;
    }

    /**
     * @return результат работы метода
     */
    @Override
    public String getResult() {
        String formattedTicket = "%0" + this.digits.length + "d\t";
        return String.format(formattedTicket, this.distance, this.ticket, (this.ticket - this.distance));
    }

    /**
     * Создаёт экземпляры {@link NearestTickets}
     */
    public interface Builder extends TicketServiceBuilder {

        /**
         * @return экземпляр {@link TicketService}
         */
        @Override
        default TicketService build() {
            if(produceDigitsQty() <= 0) {
                throw new IllegalArgumentException("Количество цифр должно быть положительным!");
            }
            if(produceDigitsQty() % 2 > 0){
                throw new IllegalArgumentException("Количество цифр должно быть чётным!");
            }
            return new NearestTickets(produceDigitsQty());
        }

        default TicketService build(Predicate<Integer> condition){
            NearestTickets nearestTickets = (NearestTickets) this.build();
            nearestTickets.optionalCondition = Optional.ofNullable(condition);

            return nearestTickets;
        }

        default TicketService build(final Predicate<Integer> condition, final Comparator<Integer> comparator) {
            NearestTickets nearestTickets = (NearestTickets) this.build(condition);
            nearestTickets.optionalComparator = Optional.ofNullable(comparator);

            return nearestTickets;
        }
    }
}

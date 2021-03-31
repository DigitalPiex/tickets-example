package org.hse.example.service;

import org.junit.Before;
import org.junit.Test;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

import static org.junit.Assert.*;

/**
 * Содержит тесты для {@link NearestTickets.Builder}
 */
public class NearestTicketsBuilderTest {
    private Predicate<Integer> condition;

    @Before
    public void init() {
        this.condition = Objects::nonNull;
    }

    @Test
    public void buildHappyPathTest(){
        //given
        NearestTickets.Builder builder = () -> 6;

        //when
        TicketService nearestTickets = builder.build();

        //then
        assertNotNull(nearestTickets);
    }

    @Test(expected = IllegalArgumentException.class)
    public void buildNegativeArgumentTest(){
        //given
        NearestTickets.Builder builder = () -> -6;

        //when
        builder.build();
    }

    @Test
    public void buildNegativeArgumentExceptionTest(){
        //given
        NearestTickets.Builder builder = () -> -6;

        //when
        assertException(builder, "Количество цифр должно быть положительным!");
    }

    @Test
    public void buildOddArgumentTest() {
        //given
        NearestTickets.Builder builder = () -> 3;

        //when
        assertException(builder, "Количество цифр должно быть чётным!");
    }

    @Test
    public void buildWithConditionTest() {
        //given
        NearestTickets.Builder builder = () -> 6;

        //when
        TicketService service = builder.build(condition);

        //then
        assertNotNull(service);

        NearestTickets nearestTickets = (NearestTickets) service;
        assertNotEquals(Optional.empty(), nearestTickets.getOptionalCondition());
        nearestTickets
                .getOptionalCondition()
                .ifPresent(predicate -> assertEquals(condition, predicate));
    }

    @Test
    public void buildWithNullConditionTest() {
        //given
        NearestTickets.Builder builder = () -> 6;

        //when
        TicketService service = builder.build(null);

        //then
        assertNotNull(service);

        NearestTickets nearestTickets = (NearestTickets) service;
        assertEquals(Optional.empty(), nearestTickets.getOptionalCondition());
    }

    /**
     * Проверяет, что при вызове builder.build() Получили {@link IllegalArgumentException} с переданным текстом
     *
     * @param builder билдер
     * @param message текст исключения
     */
    private void assertException(NearestTickets.Builder builder, String message) {
        Exception exception = null;
        try {
            builder.build();
        } catch (IllegalArgumentException e) {
            exception = e;
        } finally {
            //then
            assertNotNull(exception);
            assertEquals(message, exception.getMessage());
        }
    }

}
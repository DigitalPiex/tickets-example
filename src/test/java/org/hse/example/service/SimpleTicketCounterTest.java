package org.hse.example.service;

import org.hse.example.domain.TicketFactory;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Тесты для {@link SimpleTicketCounter}
 */
public class SimpleTicketCounterTest {
    //todo реализовать тесты

	@Test
	public void buildHappyPathTest(){
		//given
		NearestTickets.Builder builder = () -> 6;

		//when
		TicketService service = builder.build();

		//then
		assertNotNull(service.doWork());
	}

	@Test(expected = IllegalArgumentException.class)
	public void buildOddArgumentTest(){
		//given
		NearestTickets.Builder builder = () -> 5;

		//when
		builder.build();
	}

	@Test(expected = IllegalArgumentException.class)
	public void buildNegativeArgumentExceptionTest(){
		//given
		NearestTickets.Builder builder = () -> -6;

		//when
		builder.build();
	}

}
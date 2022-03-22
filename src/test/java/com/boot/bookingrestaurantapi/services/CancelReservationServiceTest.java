package com.boot.bookingrestaurantapi.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.boot.bookingrestaurantapi.entities.Reservation;
import com.boot.bookingrestaurantapi.exceptions.BookingException;
import com.boot.bookingrestaurantapi.repositories.ReservationRepository;
import com.boot.bookingrestaurantapi.services.impl.CancelReservationServiceImpl;

@TestInstance(Lifecycle.PER_CLASS)
public class CancelReservationServiceTest {

	private static final String LOCATOR = "BURGUER 09";
	private static final String RESERVATION_DELETED = "LOCATOR_DELETE";

	private static final Reservation RESERVATION = new Reservation();

	@Mock
	private ReservationRepository reservationRepository;

	@InjectMocks
	CancelReservationServiceImpl cancelReservationServiceImpl;

	@BeforeAll
	public void init() throws BookingException {
		MockitoAnnotations.initMocks(this);

	}

	@Test
	public void deleteReservationOK() throws BookingException {
		Mockito.when(reservationRepository.findByLocator(LOCATOR)).thenReturn(Optional.of(RESERVATION));
		Mockito.when(reservationRepository.deleteByLocator(LOCATOR)).thenReturn(Optional.of(RESERVATION));

		final String response = cancelReservationServiceImpl.deleteReservation(LOCATOR);
		assertEquals(response, RESERVATION_DELETED);
	}

	@Test
	public void deleteReservationNotFoundError() throws BookingException {
		assertThrows(BookingException.class, () -> {
			Mockito.when(reservationRepository.findByLocator(LOCATOR)).thenReturn(Optional.empty());
			Mockito.when(reservationRepository.deleteByLocator(LOCATOR)).thenReturn(Optional.of(RESERVATION));

			final String response = cancelReservationServiceImpl.deleteReservation(LOCATOR);
			assertEquals(response, RESERVATION_DELETED);
			fail();
		});
	}
	
	@Test
	public void deleteReservationInternalServerError() throws BookingException {
		assertThrows(BookingException.class, () -> {
			Mockito.when(reservationRepository.findByLocator(LOCATOR)).thenReturn(Optional.of(RESERVATION));
			Mockito.doThrow(Exception.class).when(reservationRepository).deleteByLocator(LOCATOR);

			final String response = cancelReservationServiceImpl.deleteReservation(LOCATOR);
			assertEquals(response, RESERVATION_DELETED);
			fail();
		});
	}

}

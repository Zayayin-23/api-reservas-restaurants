package com.boot.bookingrestaurantapi.services;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
import com.boot.bookingrestaurantapi.entities.Restaurant;
import com.boot.bookingrestaurantapi.entities.Turn;
import com.boot.bookingrestaurantapi.exceptions.BookingException;
import com.boot.bookingrestaurantapi.jsons.CreateReservationRest;
import com.boot.bookingrestaurantapi.repositories.ReservationRepository;
import com.boot.bookingrestaurantapi.repositories.RestaurantRepository;
import com.boot.bookingrestaurantapi.repositories.TurnRepository;
import com.boot.bookingrestaurantapi.services.impl.ReservationServiceImpl;

@TestInstance(Lifecycle.PER_CLASS)
public class ReservationServiceTest {

	private static final Date DATE = new Date();

	private static final String LOCATOR = "BURGER 03";
	private static final String TURNO = "TURN_12_004";

	private static final Long PERSON = 30L;
	private static final Long RESTAURANT_ID = 5L;
	private static final Long RESERVATION_ID = 5L;

	private static final Long TURN_ID = 5L;

	private static final String NAME = "Burger";
	private static final String DESCRIPTION = "Grandes Hamburguesas";
	private static final String ADDRESS = "Av. Galindo";
	private static final String IMAGE = "www.hamburguesas.cl";

	CreateReservationRest CREATE_RESERVATION_REST = new CreateReservationRest();
	private static final Restaurant RESTAURANT = new Restaurant();
	private static final Reservation RESERVATION = new Reservation();
	private static final Turn TURN = new Turn();

	private static final List<Turn> TURN_LIST = new ArrayList<>();

	private static final Optional<Restaurant> OPTIONAL_RESTAURANT = Optional.of(RESTAURANT);
	private static final Optional<Restaurant> OPTIONAL_RESTAURANT_EMPTY = Optional.empty();

	private static final Optional<Turn> OPTIONAL_TURN = Optional.of(TURN);
	private static final Optional<Turn> OPTIONAL_TURN_EMPTY = Optional.empty();

	private static final Optional<Reservation> OPTIONAL_RESERVATION_EMPTY = Optional.empty();
	private static final Optional<Reservation> OPTIONAL_RESERVATION = Optional.of(RESERVATION);

	@Mock
	RestaurantRepository restaurantRepository;

	@Mock
	TurnRepository turnRepository;

	@Mock
	ReservationRepository reservationRepository;

	@InjectMocks
	private ReservationServiceImpl reservationServiceImpl;

	@BeforeAll
	public void init() throws BookingException {
		// MockitoAnnotations.openMocks(this);
		MockitoAnnotations.initMocks(this);

		RESTAURANT.setName(NAME);
		RESTAURANT.setDescription(DESCRIPTION);
		RESTAURANT.setAddress(ADDRESS);
		RESTAURANT.setId(RESTAURANT_ID);
		RESTAURANT.setImage(IMAGE);
		RESTAURANT.setTurns(TURN_LIST);

		TURN.setId(TURN_ID);
		TURN.setName(NAME);
		TURN.setRestaurant(RESTAURANT);

		CREATE_RESERVATION_REST.setDate(DATE);
		CREATE_RESERVATION_REST.setPerson(PERSON);
		CREATE_RESERVATION_REST.setRestaurantId(RESTAURANT_ID);
		CREATE_RESERVATION_REST.setTurnId(TURN_ID);

		RESERVATION.setId(RESERVATION_ID);
		RESERVATION.setDate(DATE);
		RESERVATION.setLocator(LOCATOR);
		RESERVATION.setTurn(TURNO);
		RESERVATION.setPerson(PERSON);
		RESERVATION.setRestaurant(RESTAURANT);

	}

	@Test
	public void createReservationTest() throws BookingException {
		Mockito.when(restaurantRepository.findById(RESTAURANT_ID)).thenReturn(OPTIONAL_RESTAURANT);
		Mockito.when(turnRepository.findById(TURN_ID)).thenReturn(OPTIONAL_TURN);
		Mockito.when(reservationRepository.findByTurnAndRestaurantId(TURN.getName(), RESTAURANT.getId()))
				.thenReturn(OPTIONAL_RESERVATION_EMPTY);

		Mockito.when(reservationRepository.save(Mockito.any(Reservation.class))).thenReturn(new Reservation());

		reservationServiceImpl.createReservation(CREATE_RESERVATION_REST);
	}

	@Test()
	public void createReservationFindByIdTestError() throws BookingException {
		assertThrows(BookingException.class, () -> {
			Mockito.when(restaurantRepository.findById(RESTAURANT_ID)).thenReturn(OPTIONAL_RESTAURANT_EMPTY);
			reservationServiceImpl.createReservation(CREATE_RESERVATION_REST);
			fail();
		});
	}

	@Test
	public void createReservationTurnFindByIdTestError() throws BookingException {
		assertThrows(BookingException.class, () -> {
			Mockito.when(restaurantRepository.findById(RESTAURANT_ID)).thenReturn(OPTIONAL_RESTAURANT);
			Mockito.when(turnRepository.findById(TURN_ID)).thenReturn(OPTIONAL_TURN_EMPTY);
			reservationServiceImpl.createReservation(CREATE_RESERVATION_REST);
			fail();
		});
	}

	@Test
	public void createReservationTurnAndRestaurantIdTestError() throws BookingException {
		assertThrows(BookingException.class, () -> {
			Mockito.when(restaurantRepository.findById(RESTAURANT_ID)).thenReturn(OPTIONAL_RESTAURANT);
			Mockito.when(turnRepository.findById(TURN_ID)).thenReturn(OPTIONAL_TURN);
			Mockito.when(reservationRepository.findByTurnAndRestaurantId(TURN.getName(), RESTAURANT.getId()))
					.thenReturn(OPTIONAL_RESERVATION);
			reservationServiceImpl.createReservation(CREATE_RESERVATION_REST);
			fail();
		});
	}

	@Test
	public void createReservationInternalServerErrorTest() throws BookingException {
		
		assertThrows(BookingException.class, () -> {
			Mockito.when(restaurantRepository.findById(RESTAURANT_ID)).thenReturn(OPTIONAL_RESTAURANT);
			Mockito.when(turnRepository.findById(TURN_ID)).thenReturn(OPTIONAL_TURN);
			Mockito.when(reservationRepository.findByTurnAndRestaurantId(TURN.getName(), RESTAURANT.getId()))
					.thenReturn(OPTIONAL_RESERVATION_EMPTY);

			//Mockito.doThrow(Exception.class).when(reservationRepository).save(Mockito.any(Reservation.class));
			//Mockito.when(reservationRepository.save(Mockito.any(Reservation.class))).thenReturn(new Reservation());
			
			Mockito.doThrow(Exception.class).when(reservationRepository).save(Mockito.any(Reservation.class));
			
			reservationServiceImpl.createReservation(CREATE_RESERVATION_REST);
			fail();
		});
	}

}

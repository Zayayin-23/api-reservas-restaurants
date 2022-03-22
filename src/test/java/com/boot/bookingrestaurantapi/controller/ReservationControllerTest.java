package com.boot.bookingrestaurantapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.boot.bookingrestaurantapi.controllers.ReservationController;
import com.boot.bookingrestaurantapi.exceptions.BookingException;
import com.boot.bookingrestaurantapi.jsons.CreateReservationRest;
import com.boot.bookingrestaurantapi.response.BookingResponse;
import com.boot.bookingrestaurantapi.services.ReservationService;

@TestInstance(Lifecycle.PER_CLASS)
public class ReservationControllerTest {
	
	private static final String SUCCESS_STATUS = "Success";
	private static final String SUCCESS_CODE = "200 OK";
	private static final String OK = "OK";
	
	private static final Long RESTAURANT_ID = 1L;
	private static final Date DATE = new Date();
	private static final Long PERSON = 1L;
	private static final Long TURN_ID = 1L;
	private static final String LOCATOR = "BURGER 2";
	
	CreateReservationRest CREATE_RESERVATION_REST = new CreateReservationRest();
	
	@Mock
	ReservationService reservationService;
	
	@InjectMocks
	ReservationController reservationController;
	
	@BeforeAll
	public void init() throws BookingException {
		MockitoAnnotations.initMocks(this);
		
		CREATE_RESERVATION_REST.setDate(DATE);
		CREATE_RESERVATION_REST.setPerson(PERSON);
		CREATE_RESERVATION_REST.setRestaurantId(RESTAURANT_ID);
		CREATE_RESERVATION_REST.setTurnId(TURN_ID);
		
		Mockito.when(reservationService.createReservation(CREATE_RESERVATION_REST)).thenReturn(LOCATOR);
	}
	
	@Test
	public void createReservationTest() throws BookingException {
		BookingResponse<String> response = reservationController.createReservation(CREATE_RESERVATION_REST);
		
		assertEquals(response.getStatus(), SUCCESS_STATUS);
		assertEquals(response.getCode(), SUCCESS_CODE);
		assertEquals(response.getMessage(), OK);
		assertEquals(response.getData(), LOCATOR);
	}

}

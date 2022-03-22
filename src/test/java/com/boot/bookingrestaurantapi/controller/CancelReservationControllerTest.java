package com.boot.bookingrestaurantapi.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.boot.bookingrestaurantapi.controllers.CancelReservationController;
import com.boot.bookingrestaurantapi.exceptions.BookingException;
import com.boot.bookingrestaurantapi.response.BookingResponse;
import com.boot.bookingrestaurantapi.services.CancelReservationService;

@TestInstance(Lifecycle.PER_CLASS)
public class CancelReservationControllerTest {
	
	private static final String SUCCESS_STATUS = "Success";
	private static final String SUCCESS_CODE = "200 OK";
	private static final String OK = "OK";
	
	private static final String RESERVATION_DELETED = "LOCATOR_DELETED";
	private static final String LOCATOR = "BURGUER 007";
	
	@Mock
	CancelReservationService cancelReservationService;
	
	@InjectMocks
	CancelReservationController cancelReservationController;
	
	@BeforeAll
	public void init() throws BookingException {
		MockitoAnnotations.initMocks(this);
		
		Mockito.when(cancelReservationService.deleteReservation(LOCATOR)).thenReturn(RESERVATION_DELETED);
	}
	
	@Test
	public void deleteReservationTest() throws BookingException {
		
		final BookingResponse<String> response = cancelReservationController.deleteReservation(LOCATOR);
		
		assertEquals(response.getStatus(), SUCCESS_STATUS);
		assertEquals(response.getCode(), SUCCESS_CODE);
		assertEquals(response.getMessage(), OK);
		assertEquals(response.getData(), RESERVATION_DELETED);
	}

}

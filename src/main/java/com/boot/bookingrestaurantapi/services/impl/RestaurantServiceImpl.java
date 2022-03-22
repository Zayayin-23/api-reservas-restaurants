package com.boot.bookingrestaurantapi.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boot.bookingrestaurantapi.dtos.NotFoundException;
import com.boot.bookingrestaurantapi.entities.Restaurant;
import com.boot.bookingrestaurantapi.exceptions.BookingException;
import com.boot.bookingrestaurantapi.jsons.RestaurantRest;
import com.boot.bookingrestaurantapi.repositories.RestaurantRepository;
import com.boot.bookingrestaurantapi.services.RestaurantService;

@Service
public class RestaurantServiceImpl implements RestaurantService {

	@Autowired
	RestaurantRepository restaurantRepository;

	public static final ModelMapper modelMapper = new ModelMapper();

	@Override
	public RestaurantRest getRestaurantById(Long restauranId) throws BookingException {
		return modelMapper.map(getRestaurantEntity(restauranId), RestaurantRest.class);
	}

	@Override
	public List<RestaurantRest> getRestaurants() throws BookingException {
		final List<Restaurant> restaurantEntity = restaurantRepository.findAll();
		return restaurantEntity.stream().map(service -> modelMapper.map(service, RestaurantRest.class))
				.collect(Collectors.toList());
	}

	private Restaurant getRestaurantEntity(Long restauranId) throws BookingException {
		return restaurantRepository.findById(restauranId)
				.orElseThrow(() -> new NotFoundException("SNOT-404-1", "RESTAURANT_NOT_FOUND"));
	}

}

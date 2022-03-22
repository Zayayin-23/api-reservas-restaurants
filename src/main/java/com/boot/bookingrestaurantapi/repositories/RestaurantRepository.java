package com.boot.bookingrestaurantapi.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.boot.bookingrestaurantapi.entities.Restaurant;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
	
	Optional<Restaurant> findById(Long id);
	
	Optional<Restaurant> findByName(String nameRestaurant);

}

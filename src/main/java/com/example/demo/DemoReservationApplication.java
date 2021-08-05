
package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.Models.Reservation;
import com.example.demo.repository.ReservationRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class DemoReservationApplication {

	@Autowired
	private ReservationRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(DemoReservationApplication.class, args);
	}

	@GetMapping("/reservations")
	public ResponseEntity<List<Reservation>> getAllReservations(@RequestParam(required = false) String name) {
		try {
			List<Reservation> reservations = new ArrayList<Reservation>();
			if (name == null)
				repository.findAll().forEach(reservations::add);
			else
				repository.findByNameContaining(name).forEach(reservations::add);
			if (reservations.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			reservations.stream().forEach((p) -> {
				p.setName(p.getName().toUpperCase());
			});

			return new ResponseEntity<>(reservations, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/reservations")
	public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation) {
		try {
			Reservation _repository = repository
					.save(new Reservation(reservation.getId(), reservation.getName(), reservation.getTime()));
			return new ResponseEntity<>(_repository, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/reservations/{id}")
	public ResponseEntity<Reservation> updateReservation(@PathVariable("id") Integer id,
			@RequestBody Reservation reservation) {
		Optional<Reservation> reservationData = repository.findById(id);

		if (reservationData.isPresent()) {
			Reservation reservationRetrieved = reservationData.get();
			reservationRetrieved.setName(reservation.getName());
			return new ResponseEntity<>(repository.save(reservationRetrieved), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/reservations/{id}")
	public ResponseEntity<String> deletereservation(@PathVariable("id") Integer id) {
		try {
			repository.deleteById(id);
			return new ResponseEntity<>("The element has been deleted", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Bean
	public CommandLineRunner testApp(ReservationRepository repo) {
		return args -> {
			repo.save(new Reservation("Hotel 1", LocalDateTime.of(2019, Month.MARCH, 28, 14, 33, 48, 123456789)));
			repo.save(new Reservation("Hotel 2", LocalDateTime.of(2020, Month.AUGUST, 10, 14, 33, 48, 123456789)));
			Iterable<Reservation> allReservations = repo.findAll();
			System.out.println(repo.findAll());
			System.out.println("All reservations in DB: " + allReservations);
		};
	}

}
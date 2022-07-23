package com.zicodeng.learning.javaspringtutorial.webservice;

import java.util.Date;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

import com.zicodeng.learning.javaspringtutorial.business.ReservationService;
import com.zicodeng.learning.javaspringtutorial.business.RoomReservation;
import com.zicodeng.learning.javaspringtutorial.data.Guest;
import com.zicodeng.learning.javaspringtutorial.util.DateUtils;

@RestController
@RequestMapping("/api")
public class WebServiceController {
  public final DateUtils dateUtils;
  public final ReservationService reservationService;

  public WebServiceController(DateUtils dateUtils, ReservationService reservationService) {
    this.dateUtils = dateUtils;
    this.reservationService = reservationService;
  }

  @RequestMapping(path = "/reservations", method = RequestMethod.GET)
  public List<RoomReservation> getReservations(@RequestParam(value = "date", required = false) String dateString) {
    Date date = this.dateUtils.createDateFromDateString(dateString);
    List<RoomReservation> roomReservations = this.reservationService.getRoomReservationsForDate(date);

    // Spring will take care of marshalling RoomReservation objects to JSON.
    return roomReservations;
  }

  // @RequestMapping(path = "/guests", method = RequestMethod.GET)
  @GetMapping("/guests")
  public List<Guest> getGuests() {
    List<Guest> guests = this.reservationService.getGuests();

    return guests;
  }

  @PostMapping("/guests")
  @ResponseStatus(HttpStatus.CREATED)
  // @RequestBody will make sure the JSON input will be
  // unmarshalling to a Guest object.
  public void addGuest(@RequestBody Guest guest) {
    System.out.println("Adding a new guest: " + guest.toString());
    this.reservationService.addGuest(guest);
  }
}

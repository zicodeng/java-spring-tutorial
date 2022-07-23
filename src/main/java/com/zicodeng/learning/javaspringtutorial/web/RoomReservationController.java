package com.zicodeng.learning.javaspringtutorial.web;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.zicodeng.learning.javaspringtutorial.business.ReservationService;
import com.zicodeng.learning.javaspringtutorial.business.RoomReservation;
import com.zicodeng.learning.javaspringtutorial.util.DateUtils;

// The @Controller annotation is used to define the Controller portion of the MVC pattern
// within Spring and is a stereotype of @Component.
@Controller
@RequestMapping("/reservations")
public class RoomReservationController {
  private final DateUtils dateUtils;
  private final ReservationService reservationService;

  public RoomReservationController(DateUtils dateUtils, ReservationService reservationService) {
    this.dateUtils = dateUtils;
    this.reservationService = reservationService;
  }

  @RequestMapping(method = RequestMethod.GET)
  public String getReservations(@RequestParam(value = "date", required = false) String dateString, Model model) {
    Date date = this.dateUtils.createDateFromDateString(dateString);
    List<RoomReservation> roomReservations = this.reservationService.getRoomReservationsForDate(date);
    model.addAttribute("roomReservations", roomReservations);

    // Template file name. No need to provide file path and extension.
    // Spring will look for files in the "resources/templates" folder.
    return "room-reservations";
  }
}

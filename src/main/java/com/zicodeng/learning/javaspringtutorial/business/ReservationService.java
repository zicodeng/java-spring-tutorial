package com.zicodeng.learning.javaspringtutorial.business;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.zicodeng.learning.javaspringtutorial.data.Guest;
import com.zicodeng.learning.javaspringtutorial.data.GuestRepository;
import com.zicodeng.learning.javaspringtutorial.data.Reservation;
import com.zicodeng.learning.javaspringtutorial.data.ReservationRepository;
import com.zicodeng.learning.javaspringtutorial.data.Room;
import com.zicodeng.learning.javaspringtutorial.data.RoomRepository;

// The @Service annotation is a stereotype of @Component and is used for component scanning,
// but it also allows you a point for your own service base aspects and proxies if you desire.
@Service
public class ReservationService {
  private final RoomRepository roomRepository;
  private final GuestRepository guestRepository;
  private final ReservationRepository reservationRepository;

  public ReservationService(RoomRepository roomRepository, GuestRepository guestRepository,
      ReservationRepository reservationRepository) {
    this.roomRepository = roomRepository;
    this.guestRepository = guestRepository;
    this.reservationRepository = reservationRepository;
  }

  public List<RoomReservation> getRoomReservationsForDate(Date date) {
    Iterable<Room> rooms = this.roomRepository.findAll();
    Map<Long, RoomReservation> roomReservationMap = new HashMap<Long, RoomReservation>();

    rooms.forEach(room -> {
      RoomReservation roomReservation = new RoomReservation();

      roomReservation.setRoomId(room.getId());
      roomReservation.setRoomName(room.getName());
      roomReservation.setRoomNumber(room.getRoomNumber());

      roomReservationMap.put(room.getId(), roomReservation);
    });

    // Find all reservations for a given date.
    Iterable<Reservation> reservations = this.reservationRepository
        .findReservationByReservationDate(new java.sql.Date(date.getTime()));

    // For each reservation, retrieve guest info and add it to `roomReservation`.
    reservations.forEach(reservation -> {
      RoomReservation roomReservation = roomReservationMap.get(reservation.getRoomId());
      roomReservation.setDate(date);

      Guest guest = this.guestRepository.findById(reservation.getGuestId()).get();

      roomReservation.setFirstName(guest.getFirstName());
      roomReservation.setLastName(guest.getLastName());
      roomReservation.setGuestId(guest.getGuestId());
    });

    // Convert `roomReservationMap` to `roomReservations` list.
    List<RoomReservation> roomReservations = new ArrayList<RoomReservation>();
    for (Long id : roomReservationMap.keySet()) {
      roomReservations.add(roomReservationMap.get(id));
    }

    // Sort room reservations by room name. In case of tie, sort by room number.
    roomReservations.sort(new Comparator<RoomReservation>() {
      @Override
      public int compare(RoomReservation o1, RoomReservation o2) {
        if (o1.getRoomName().equals(o2.getRoomName())) {
          return o1.getRoomNumber().compareTo(o2.getRoomNumber());
        }
        return o1.getRoomName().compareTo(o2.getRoomName());
      }
    });

    return roomReservations;
  }

  public List<Guest> getGuests() {
    Iterable<Guest> guests = this.guestRepository.findAll();
    List<Guest> guestList = new ArrayList<>();

    guests.forEach(guest -> {
      guestList.add(guest);
    });
    guestList.sort(new Comparator<Guest>() {
      @Override
      public int compare(Guest o1, Guest o2) {
        if (o1.getLastName().equals(o2.getLastName())) {
          return o1.getFirstName().compareTo(o2.getFirstName());
        }
        return o1.getLastName().compareTo(o2.getLastName());
      }
    });

    return guestList;
  }

  public void addGuest(Guest guest) {
    if (guest == null) {
      throw new RuntimeException("Guest cannot be null");
    }

    this.guestRepository.save(guest);
  }
}

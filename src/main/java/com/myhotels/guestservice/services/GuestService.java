package com.myhotels.guestservice.services;

import com.myhotels.guestservice.entities.Guest;

import java.util.List;
import java.util.Map;

public interface GuestService {
    List<Guest> getAllGuests();

    Guest getGuest(Long phoneNumber);

    Guest createGuest(Guest guest);

    Guest updateGuest(Long phoneNumber, Map<String, String> params);

    void deleteGuest(Long phoneNumber);
}

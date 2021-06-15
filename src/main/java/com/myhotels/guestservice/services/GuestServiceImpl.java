package com.myhotels.guestservice.services;

import com.myhotels.guestservice.entities.Guest;
import com.myhotels.guestservice.exceptions.DataNotFoundException;
import com.myhotels.guestservice.exceptions.InvalidRequestException;
import com.myhotels.guestservice.repos.GuestRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

@Service
public class GuestServiceImpl implements GuestService{

    @Autowired
    private GuestRepo repo;

    @Override
    public List<Guest> getAllGuests() {
        List<Guest> guests = repo.findAll();
        if (guests.isEmpty()) {
            throw new DataNotFoundException("No guests found.");
        }
        return guests;
    }

    @Override
    public Guest getGuest(Long phoneNumber) {
        Guest guest = repo.findByPhoneNumber(phoneNumber);
        if (guest == null) {
            throw new DataNotFoundException("No guest found with phone number: " + phoneNumber);
        }
        return guest;
    }

    @Override
    public Guest createGuest(Guest guest) {
        return repo.save(guest);
    }

    @Override
    public Guest updateGuest(Long phoneNumber, Map<String, String> params) {
        Guest updateGuest = repo.findByPhoneNumber(phoneNumber);
        for (Entry<String, String> entry: params.entrySet()) {
            String value = entry.getValue();
            if (value.isEmpty()) {
                continue;
            }
            switch (entry.getKey()) {
                case "name":
                    updateGuest.setName(value);
                    break;
                case "address":
                    updateGuest.setAddress(value);
                    break;
                case "phoneNumber":
                    updateGuest.setPhoneNumber(Long.parseLong(value));
                    break;
                default:
                    throw new InvalidRequestException("Invalid request parameter: "+entry.getKey());
            }
        }
        return repo.save(updateGuest);
    }

    @Override
    public void deleteGuest(Long phoneNumber) {
        Guest guest = repo.findByPhoneNumber(phoneNumber);
        if (guest == null) {
            throw new DataNotFoundException("No guest found with phone number: "+ phoneNumber);
        }
        repo.delete(guest);
    }
}

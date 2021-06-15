package com.myhotels.guestservice.services;

import com.myhotels.guestservice.entities.Guest;
import com.myhotels.guestservice.exceptions.DataNotFoundException;
import com.myhotels.guestservice.exceptions.InvalidRequestException;
import com.myhotels.guestservice.repos.GuestRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
class GuestServiceTests {

    @Autowired
    private GuestService service;
    @MockBean
    private GuestRepo repo;

    private Guest guest1;
    private Guest guest2;
    private List<Guest> guests;

    @BeforeEach
    void setup() {
        guest1 = new Guest();
        guest1.setPhoneNumber(12345L);
        guest1.setAddress("add");
        guest1.setName("name");
        guest2 = new Guest();
        guest2.setPhoneNumber(12345L);
        guest2.setAddress("add");
        guest2.setName("name");
        guests = new ArrayList<>();
        guests.add(guest1);
        guests.add(guest2);
    }

    @Test
    void testGetAllGuests() {
        given(repo.findAll()).willReturn(guests);

        List<Guest> guestList = service.getAllGuests();
        assertEquals(guests, guestList);
    }

    @Test
    void testGetAllGuests_exception() {
        given(repo.findAll()).willReturn(new ArrayList<>());

        try {
            service.getAllGuests();
        } catch (DataNotFoundException ex) {
            assertEquals("No guests found.", ex.getMessage());
        }
    }

    @Test
    void testGetGuest() {
        given(repo.findByPhoneNumber(anyLong())).willReturn(guest1);

        Guest guest = service.getGuest(guest1.getPhoneNumber());
        assertEquals(guest1, guest);
    }

    @Test
    void testGetGuest_exception() {
        given(repo.findByPhoneNumber(anyLong())).willReturn(null);

        try {
            service.getGuest(guest1.getPhoneNumber());
        } catch (DataNotFoundException ex) {
            assertEquals("No guest found with phone number: " + guest1.getPhoneNumber(), ex.getMessage());
        }
    }

    @Test
    void testCreateGuest() {
        given(repo.save(any())).willReturn(guest1);

        Guest guest = service.createGuest(guest1);
        assertEquals(guest1, guest);
    }

    @Test
    void testUpdateGuest() {
        given(repo.findByPhoneNumber(anyLong())).willReturn(guest1);
        given(repo.save(guest1)).willReturn(guest1);
        Map<String, String> map = new HashMap<>();
        map.put("name", "new name");
        map.put("address", "new add");
        map.put("phoneNumber", "873504");

        Guest guest = service.updateGuest(guest1.getPhoneNumber(), map);
        assertEquals(guest, guest1);
    }

    @Test
    void testUpdateGuest_numberFormatException() {
        given(repo.findByPhoneNumber(anyLong())).willReturn(guest1);
        Map<String, String> map = new HashMap<>();
        map.put("phoneNumber", "87fw3504");

        try {
            service.updateGuest(guest1.getPhoneNumber(), map);
        } catch (InvalidRequestException e) {
            assertEquals("Invalid requst. Cause:For input string: \"87fw3504\"", e.getMessage());
        }
    }

    @Test
    void testUpdateGuest_emptyPhoneNumber() {
        given(repo.findByPhoneNumber(anyLong())).willReturn(guest1);
        Map<String, String> map = new HashMap<>();
        map.put("phoneNumber", "");

        try {
            service.updateGuest(guest1.getPhoneNumber(), map);
        } catch (InvalidRequestException e) {
            assertEquals("Invalid request. Phone number cannot be empty", e.getMessage());
        }
    }

    @Test
    void testUpdateGuest_notFoundException() {
        given(repo.findByPhoneNumber(anyLong())).willReturn(null);

        try {
            service.updateGuest(guest1.getPhoneNumber(), new HashMap<>());
        } catch (DataNotFoundException ex) {
            assertEquals("No guest found with phone number: "+guest1.getPhoneNumber(), ex.getMessage());
        }
    }

    @Test
    void testUpdateGuest_invalidRequestException() {
        given(repo.findByPhoneNumber(anyLong())).willReturn(guest1);

        try {
            service.updateGuest(guest1.getPhoneNumber(), new HashMap<>());
        }catch (InvalidRequestException ex) {
            assertEquals("Invalid request parameter: ", ex.getMessage());
        }
    }

    @Test
    void testDeleteGuest() {
        given(repo.findByPhoneNumber(anyLong())).willReturn(guest1);

        service.deleteGuest(guest1.getPhoneNumber());
        verify(repo, times(1)).delete(any());
    }

    @Test
    void testDeleteGuest_exception() {
        given(repo.findByPhoneNumber(anyLong())).willReturn(null);

        try {
            service.deleteGuest(guest1.getPhoneNumber());
        } catch (DataNotFoundException e) {
            assertEquals("No guest found with phone number: "+guest1.getPhoneNumber(), e.getMessage());
        }
    }

    @TestConfiguration
    static class GuestServiceTestConfiguration {
        @Bean
        public GuestService getGuestService() {
            return new GuestServiceImpl();
        }
    }

}

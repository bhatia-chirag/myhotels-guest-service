package com.myhotels.guestservice.controllers;

import com.myhotels.guestservice.dtos.GuestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/guest")
public interface GuestController {

    @GetMapping("/")
    ResponseEntity<List<GuestDto>> getAllGuests();

    @GetMapping("/phone/{phone}")
    ResponseEntity<GuestDto> getGuest(@PathVariable("phone") Long phoneNumber);

    @PostMapping("/add")
    ResponseEntity<GuestDto> createGuest(@RequestBody GuestDto guestDto);

    @PutMapping("/update/phone/{phone}")
    ResponseEntity<GuestDto> updateGuest(@PathVariable("phone") Long phoneNumber, @RequestParam Map<String, String> params);

    @DeleteMapping("/phone/{phone}")
    ResponseEntity<Void> deleteGuest(@PathVariable("phone") Long phoneNumber);
}

package com.myhotels.guestservice.controllers;

import com.myhotels.guestservice.dtos.GuestDto;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/guest")
public interface GuestController {

    @GetMapping("/")
    List<GuestDto> getAllGuests();

    @GetMapping("/phone/{phone}")
    GuestDto getGuest(@PathVariable("phone") Long phoneNumber);

    @PostMapping("/add")
    GuestDto createGuest(@RequestBody GuestDto guestDto);

    @PutMapping("/update/phone/{phone}")
    GuestDto updateGuest(@PathVariable("phone") Long phoneNumber, @RequestParam Map<String, String> params);

    @DeleteMapping("/phone/{phone}")
    void deleteGuest(@PathVariable Long phoneNumber);
}

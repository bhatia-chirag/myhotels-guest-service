package com.myhotels.guestservice.controllers;

import com.myhotels.guestservice.dtos.GuestDto;
import com.myhotels.guestservice.mappers.GuestMapper;
import com.myhotels.guestservice.services.GuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class GuestControllerImpl implements GuestController{
    @Autowired
    private GuestService service;
    @Autowired
    private GuestMapper mapper;

    @Override
    public List<GuestDto> getAllGuests() {
        return service.getAllGuests().stream()
                .map(guest -> mapper.guestToGuestDto(guest))
                .collect(Collectors.toList());
    }

    @Override
    public GuestDto getGuest(Long phoneNumber) {
        return mapper.guestToGuestDto(service.getGuest(phoneNumber));
    }

    @Override
    public GuestDto createGuest(GuestDto guestDto) {
        return mapper.guestToGuestDto(service.createGuest(mapper.guestDtoToGuest(guestDto)));
    }

    @Override
    public GuestDto updateGuest(Long phoneNumber, Map<String, String> params) {
        return mapper.guestToGuestDto(service.updateGuest(phoneNumber, params));
    }

    @Override
    public void deleteGuest(Long phoneNumber) {
        service.deleteGuest(phoneNumber);
    }
}

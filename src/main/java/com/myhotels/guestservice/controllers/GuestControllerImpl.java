package com.myhotels.guestservice.controllers;

import com.myhotels.guestservice.dtos.GuestDto;
import com.myhotels.guestservice.mappers.GuestMapper;
import com.myhotels.guestservice.services.GuestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
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
    public ResponseEntity<List<GuestDto>> getAllGuests() {
        return ResponseEntity.ok(service.getAllGuests().stream()
                .map(guest -> mapper.guestToGuestDto(guest))
                .collect(Collectors.toList()));
    }

    @Override
    public ResponseEntity<GuestDto> getGuest(Long phoneNumber) {
        return ResponseEntity.ok(mapper.guestToGuestDto(service.getGuest(phoneNumber)));
    }

    @Override
    public ResponseEntity<GuestDto> createGuest(GuestDto guestDto) {
        GuestDto dto = mapper.guestToGuestDto(service.createGuest(mapper.guestDtoToGuest(guestDto)));
        return ResponseEntity.created(URI.create("/guest/phone/"+dto.getPhoneNumber())).body(dto);
    }

    @Override
    public ResponseEntity<GuestDto> updateGuest(Long phoneNumber, Map<String, String> params) {
        GuestDto guestDto = mapper.guestToGuestDto(service.updateGuest(phoneNumber, params));
        return ResponseEntity.ok(guestDto);
    }

    @Override
    public ResponseEntity<Void> deleteGuest(Long phoneNumber) {
        service.deleteGuest(phoneNumber);
        return ResponseEntity.noContent().build();
    }
}

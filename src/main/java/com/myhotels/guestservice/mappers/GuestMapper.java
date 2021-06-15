package com.myhotels.guestservice.mappers;

import com.myhotels.guestservice.dtos.GuestDto;
import com.myhotels.guestservice.entities.Guest;
import org.mapstruct.Mapper;

@Mapper
public interface GuestMapper {
    GuestDto guestToGuestDto(Guest guest);
    Guest guestDtoToGuest(GuestDto guestDto);
}

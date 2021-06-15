package com.myhotels.guestservice.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GuestDto {
    private String name;
    private String address;
    private Long phoneNumber;
}

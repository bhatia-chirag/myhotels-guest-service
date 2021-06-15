package com.myhotels.guestservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myhotels.guestservice.dtos.GuestDto;
import com.myhotels.guestservice.entities.Guest;
import com.myhotels.guestservice.mappers.GuestMapper;
import com.myhotels.guestservice.services.GuestService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(GuestControllerImpl.class)
class GuestControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GuestService service;
    @MockBean
    private GuestMapper mapper;

    private Guest guest1;
    private Guest guest2;
    private List<Guest> guests;
    private GuestDto guestDto1;
    private GuestDto guestDto2;
    private List<GuestDto> guestDtos;

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

        guestDto1 = new GuestDto();
        guestDto1.setAddress(guest1.getAddress());
        guestDto1.setName(guest1.getName());
        guestDto1.setPhoneNumber(guest1.getPhoneNumber());
        guestDto2 = new GuestDto();
        guestDto2.setAddress(guest2.getAddress());
        guestDto2.setName(guest2.getName());
        guestDto2.setPhoneNumber(guest2.getPhoneNumber());
        guestDtos = new ArrayList<>();
        guestDtos.add(guestDto1);
        guestDtos.add(guestDto2);
    }

    @Test
    void testGetAllGuests() throws Exception {
        given(service.getAllGuests()).willReturn(guests);
        given(mapper.guestToGuestDto(any())).willReturn(guestDto1);

        mockMvc.perform(MockMvcRequestBuilders.get("/guest/"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"name\":\"name\",\"address\":\"add\",\"phoneNumber\":12345},{\"name\":\"name\",\"address\":\"add\",\"phoneNumber\":12345}]"));
    }

    @Test
    void testGetGuest() throws Exception {
        given(service.getGuest(anyLong())).willReturn(guest1);
        given(mapper.guestToGuestDto(any())).willReturn(guestDto1);

        mockMvc.perform(MockMvcRequestBuilders.get("/guest/phone/" + guest1.getPhoneNumber()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value(guestDto1.getName()));
    }

    @Test
    void testCreateGuest() throws Exception {
        given(mapper.guestToGuestDto(any())).willReturn(guestDto1);

        mockMvc.perform(MockMvcRequestBuilders.post("/guest/add")
                .content(new ObjectMapper().writeValueAsString(guestDto1))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("name").value(guestDto1.getName()));
    }

    @Test
    void testUpdateGuest() throws Exception {
        given(mapper.guestToGuestDto(any())).willReturn(guestDto1);
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("name", "new name");

        mockMvc.perform(MockMvcRequestBuilders.put("/guest/update/phone/" + guestDto1.getPhoneNumber())
                .params(map))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value(guestDto1.getName()));
    }

    @Test
    void testDeleteGuest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/guest/phone/" + guestDto1.getPhoneNumber()))
                .andExpect(status().isNoContent());
    }
}

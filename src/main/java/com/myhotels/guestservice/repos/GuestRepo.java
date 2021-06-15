package com.myhotels.guestservice.repos;

import com.myhotels.guestservice.entities.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GuestRepo extends JpaRepository<Guest, Long> {
    Guest findByPhoneNumber(Long phoneNumber);
}

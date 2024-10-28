package com.sevar.Hotel.Booking.App.security.user;

import com.sevar.Hotel.Booking.App.model.Users;
import com.sevar.Hotel.Booking.App.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HotelUserDetailsService implements UserDetailsService
{
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
        return HotelUserDetails.hotelUserDetails(user);
    }
}

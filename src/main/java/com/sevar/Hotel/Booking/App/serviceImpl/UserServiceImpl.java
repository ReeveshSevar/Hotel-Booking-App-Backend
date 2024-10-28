package com.sevar.Hotel.Booking.App.serviceImpl;

import com.sevar.Hotel.Booking.App.exception.UserAlreadyExistsException;
import com.sevar.Hotel.Booking.App.model.Role;
import com.sevar.Hotel.Booking.App.model.Users;
import com.sevar.Hotel.Booking.App.repository.RoleRepository;
import com.sevar.Hotel.Booking.App.repository.UserRepository;
import com.sevar.Hotel.Booking.App.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;

@Service
public class UserServiceImpl implements UserService
{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public Users registerUser(Users user) {
        if (userRepository.existsByEmail(user.getEmail())){
            throw new UserAlreadyExistsException(user.getEmail()+" Already Exist");
        }
        user.setPassword(encoder.encode(user.getPassword()));
        Role userRole = roleRepository.findByName("ROLE_USER").get();
        user.setRoles(Collections.singletonList(userRole));
        return userRepository.save(user);
    }

    @Override
    public List<Users> getUsers() {
        return userRepository.findAll();
    }

    @Transactional
    @Override
    public void deleteUser(String email) {
        Users users = getUser(email);
        if (users != null)
            userRepository.existsByEmail(email);
    }

    @Override
    public Users getUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
    }
}

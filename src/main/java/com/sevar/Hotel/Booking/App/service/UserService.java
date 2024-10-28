package com.sevar.Hotel.Booking.App.service;

import com.sevar.Hotel.Booking.App.model.Users;

import java.util.List;

public interface UserService{

    Users registerUser(Users user);

    List<Users> getUsers();

    void deleteUser(String email);

    Users getUser(String email);
}

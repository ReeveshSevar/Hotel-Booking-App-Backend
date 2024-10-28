package com.sevar.Hotel.Booking.App.service;

import com.sevar.Hotel.Booking.App.model.Role;
import com.sevar.Hotel.Booking.App.model.Users;

import java.util.List;

public interface RoleService {
    List<Role> getRoles();

    Role createRole(Role theRole);

    void deleteRole(Long roleId);

    Role findByName(String name);

    Users removeUserFromRole(Long userId, Long roleId);

    Users assignRoleToUser(Long userId, Long roleId);

    Role removeAllUsersFromRole(Long roleId);
}

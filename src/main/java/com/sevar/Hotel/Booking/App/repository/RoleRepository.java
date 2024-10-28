package com.sevar.Hotel.Booking.App.repository;

import com.sevar.Hotel.Booking.App.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long>
{
    Optional<Role> findByName(String role);

    boolean existsByName(String role);
}

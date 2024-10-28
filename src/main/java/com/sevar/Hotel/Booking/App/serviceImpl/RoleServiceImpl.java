package com.sevar.Hotel.Booking.App.serviceImpl;

import com.sevar.Hotel.Booking.App.exception.RoleAlreadyExistException;
import com.sevar.Hotel.Booking.App.exception.UserAlreadyExistsException;
import com.sevar.Hotel.Booking.App.model.Role;
import com.sevar.Hotel.Booking.App.model.Users;
import com.sevar.Hotel.Booking.App.repository.RoleRepository;
import com.sevar.Hotel.Booking.App.repository.UserRepository;
import com.sevar.Hotel.Booking.App.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService
{
    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role createRole(Role theRole) {
        String roleName = "ROLE_"+theRole.getName().toUpperCase();
        Role role = new Role(roleName);
        if (roleRepository.existsByName(roleName))
            throw new RoleAlreadyExistException(theRole.getName()+" Role Already Exists");
        return roleRepository.save(role);
    }

    @Override
    public void deleteRole(Long roleId) {
        this.removeAllUsersFromRole(roleId);
        roleRepository.deleteById(roleId);
    }

    @Override
    public Role findByName(String name) {
        roleRepository.findByName(name).get();
        return null;
    }

    @Override
    public Users removeUserFromRole(Long userId, Long roleId) {
        Optional<Users> user = userRepository.findById(userId);
        Optional<Role> role = roleRepository.findById(roleId);
        if (role.isPresent() && role.get().getUsers().contains(user)){
            role.get().removeUserFromRole(user.get());
            roleRepository.save(role.get());
            return user.get();
        }
            throw new UsernameNotFoundException("User Not Found");
    }

    @Override
    public Users assignRoleToUser(Long userId, Long roleId) {
        Optional<Users> user = userRepository.findById(userId);
        Optional<Role> role = roleRepository.findById(userId);
        if (user.isPresent() && user.get().getRoles().contains(role.get())){
            throw new UserAlreadyExistsException(
                    user.get().getFirstName()+" Is Already Assigned To The "+role.get().getName()+" Role");
        }
        if (role.isPresent()){
            role.get().assignRoleToUser(user.get());
            roleRepository.save(role.get());
        }
        return user.get();
    }

    @Override
    public Role removeAllUsersFromRole(Long roleId) {
        Optional<Role> role = roleRepository.findById(roleId);
        role.get().removeAllUsersFromRole();
        return roleRepository.save(role.get());
    }
}

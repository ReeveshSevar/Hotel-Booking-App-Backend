package com.sevar.Hotel.Booking.App.controller;

import com.sevar.Hotel.Booking.App.exception.RoleAlreadyExistException;
import com.sevar.Hotel.Booking.App.model.Role;
import com.sevar.Hotel.Booking.App.model.Users;
import com.sevar.Hotel.Booking.App.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import static org.springframework.http.HttpStatus.FOUND;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/all")
    public ResponseEntity<List<Role>> getAllRoles()
    {
        return new ResponseEntity<List<Role>>(roleService.getRoles(), FOUND);
    }

    @PostMapping("/create-new-role")
    public ResponseEntity<String> createRole(@RequestBody Role role){
        try{
            roleService.createRole(role);
            return ResponseEntity.ok("New Role Created Successfully");
        }catch (RoleAlreadyExistException ex){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
        }
    }

    @DeleteMapping("/delete/{roleId}")
    public void deleteRole(@PathVariable("roleId") Long roleId) {
        roleService.deleteRole(roleId);
    }

    @PostMapping("/remove-all-users-from-role/{roleId}")
    public Role removeAllUsersFromRole(@PathVariable("roleId") Long roleId)
    {
        return roleService.removeAllUsersFromRole(roleId);
    }

    @PostMapping("/remove-user-from-role")
    public Users removeUserFromRole(
            @RequestParam("roleId") Long roleId,
            @RequestParam("userId") Long userId){
        return roleService.removeUserFromRole(userId,roleId);
    }

    @PostMapping("/assign-user-to-role")
    public Users assignUserToRole(
            @RequestParam("userId") Long userId,
            @RequestParam("roomId") Long roleId){
        return roleService.assignRoleToUser(userId,roleId);
    }
}
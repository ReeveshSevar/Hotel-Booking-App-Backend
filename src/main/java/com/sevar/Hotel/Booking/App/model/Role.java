package com.sevar.Hotel.Booking.App.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    private Collection<Users> users = new HashSet<>();

    public void assignRoleToUser(Users user){
        user.getRoles().add(this);
        this.getUsers().add(user);
    }

    public void removeUserFromRole(Users user){
        user.getRoles().remove(this);
        this.getUsers().remove(user);
    }

    public void removeAllUsersFromRole(){
        if (this.getUsers() != null){
            List<Users> roleUsers = this.getUsers().stream().toList();
            roleUsers.forEach(this :: removeUserFromRole);
        }
    }

    public String getName()
    {
        return name != null ? name : "";
    }

    public Role(String name) {
        this.name = name;
    }
}

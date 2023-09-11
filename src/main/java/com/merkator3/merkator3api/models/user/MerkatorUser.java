package com.merkator3.merkator3api.models.user;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "user")
public class MerkatorUser implements UserDetails {

    @Id private ObjectId id;
    @Field("email") private String email;
    @Field("name") private String name;
    @Field("password") private String password;
    @Field("userRoutes") private List<ObjectId> userRoutes;
    @Field("userTrips") private List<ObjectId> userTrips;
    @Field("userCompletedTrips") private List<ObjectId> userCompletedTrips;

    private Role role;

    public MerkatorUser(String email, String name) {
        this.email = email;
        this.name = name;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }



    public void addRoute(ObjectId routeId) {
        if (this.userRoutes == null){
            this.userRoutes = new ArrayList<>();
        }
        userRoutes.add(routeId);
    }

    public List<ObjectId> getUserRoutes() {
        return userRoutes;
    }

    public void addTrip(ObjectId tripId) {
        if (this.userTrips == null) {
            this.userTrips = new ArrayList<>();
        }
        userTrips.add(tripId);
    }

    public List<ObjectId> getUserTrips() {
        return userTrips;
    }

    public void addCompletedTrip(ObjectId tripId) {
        if (this.userCompletedTrips == null) {
            this.userCompletedTrips = new ArrayList<>();
        }
        userCompletedTrips.add(tripId);
    }

    public List<ObjectId> getUserCompletedTrips() {
        return userCompletedTrips;
    }

}
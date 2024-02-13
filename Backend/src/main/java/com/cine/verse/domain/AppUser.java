package com.cine.verse.domain;

import com.cine.verse.enums.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class AppUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    private String firstname;
    private String lastname;
    private String email;
    private String password;

    private String image;
    private String bio;
    private String location;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "appUser", cascade = CascadeType.ALL)
    private Set<Review> reviews;

    @OneToMany(mappedBy = "appUser", cascade = CascadeType.ALL)
    private Set<Like> likes;

    @OneToMany(mappedBy = "appUser", cascade = CascadeType.ALL)
    private Set<WatchList> watchLists;

    @OneToMany(mappedBy = "appUser", cascade = CascadeType.ALL)
    private Set<MyList> myLists;

    // we should return a list of roles
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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
}


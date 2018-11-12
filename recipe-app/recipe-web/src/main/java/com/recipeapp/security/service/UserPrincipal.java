package com.recipeapp.security.service;

import static com.recipeapp.entity.UserRole.ROLE_USER;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.recipeapp.entity.UserEntity;


public class UserPrincipal implements UserDetails {

	private static final long serialVersionUID = -2098935599381477527L;

	private Integer id;

    private String name;

    private String username;

    private String email;
    
    private String surname;

    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;


    public UserPrincipal(Integer id, String name, String surname,
                         String username, String email, String password,
                         Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }


    public static UserPrincipal build(UserEntity user) {
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority(ROLE_USER.name()));

        return new UserPrincipal(
                user.getId(),
                user.getName(),
                user.getSurname(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                grantedAuthorities
        );
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    
    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }


    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserPrincipal user = (UserPrincipal) o;
        return Objects.equals(id, user.id);
    }
}


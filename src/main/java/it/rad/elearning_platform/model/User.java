package it.rad.elearning_platform.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class User implements UserDetails {

    private Long id;
    private Long contactId;
    private String username;
    private String password;
    private Role role;

    public User(String username, String password){
        this.username = username;
        this.password = password;
    }

    public User(Long id, Long contactId, String username, String password){
        this.id=id;
        this.contactId=contactId;
        this.username = username;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        //return List.of(new SimpleGrantedAuthority(role.name()));
        return List.of();
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

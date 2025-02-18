package it.rad.elearning_platform.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
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
    private String username;
    private String password;
    private Contact contact;

    public User(String username, String pass, Contact c){
        this.username=username;
        this.password=pass;
        this.contact=c;
    }

    public User(Long id, String username, String pass, Long contactId){
        Contact c = new Contact();
        c.setId(contactId);
        this.setId(id);
        this.setUsername(username);
        this.setPassword(pass);
        this.setContact(c);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
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

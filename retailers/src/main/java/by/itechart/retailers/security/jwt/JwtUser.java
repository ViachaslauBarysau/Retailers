package by.itechart.retailers.security.jwt;

import by.itechart.retailers.entity.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class JwtUser implements UserDetails {

    private final Long id;
    private final String firstName;
    private final String lastName;
    private final Collection<? extends GrantedAuthority> authorities;
    private final String email;
    private final String password;
    private final boolean isActive;

    public JwtUser(Long id,
                   String firstName,
                   String lastName,
                   Collection<? extends GrantedAuthority> authorities,
                   String email,
                   String password,
                   Status isActive) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.authorities = authorities;
        this.email = email;
        this.password = password;
        this.isActive = isActive.equals(Status.ACTIVE);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @JsonIgnore
    public Long getId() {
        return id;
    }
    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }


    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public boolean getUserStatus() {
        return isActive;
    }
    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return isActive;
    }
    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return isActive;
    }
    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return isActive;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }
}

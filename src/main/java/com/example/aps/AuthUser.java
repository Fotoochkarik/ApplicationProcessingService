package com.example.aps;

import com.example.aps.entity.User;
import lombok.Getter;
import lombok.ToString;
import org.springframework.lang.NonNull;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

@Getter
@ToString(of = "user")
public class AuthUser extends org.springframework.security.core.userdetails.User {

    private final User user;

    public AuthUser(@NonNull User user, Set<GrantedAuthority> grantedAuthorities) {
        super(user.getName(), user.getPassword(), grantedAuthorities);
        this.user = user;
    }

    public Long id() {
        return user.getId();
    }
}
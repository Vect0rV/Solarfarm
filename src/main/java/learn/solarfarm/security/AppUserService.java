package learn.solarfarm.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

public class AppUserService implements UserDetailsService {

    private final PasswordEncoder passwordEncoder;

    private List<UserDetails> users;

    public AppUserService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserDetails userDetails = users.stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .orElse(null);

        if (userDetails == null) {
            throw  new UsernameNotFoundException(username + " not found.");
        }

        return userDetails;
    }
}

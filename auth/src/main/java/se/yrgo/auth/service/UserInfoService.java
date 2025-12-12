package se.yrgo.auth.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import se.yrgo.auth.entity.UserInfo;
import se.yrgo.auth.repository.UserRepository;

@Service
public class UserInfoService implements UserDetailsService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;

    public UserInfoService(UserRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserInfo user = repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return User.withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getRoles().split(","))
                .build();
    }

    public String addUser(UserInfo userInfo) {
        if (repository.findByUsername(userInfo.getUsername()).isPresent()) {
            return "User already exists";
        }
        userInfo.setPassword(encoder.encode(userInfo.getPassword()));
        userInfo.setRoles("admin"); //REVISE THIS
        repository.save(userInfo);
        return "User added successfully.";
    }
}

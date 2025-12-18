package se.yrgo.auth.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import se.yrgo.auth.entity.UserInfo;
import se.yrgo.auth.repository.UserRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserInfoServiceTest {

    @Mock
    UserRepository repository;

    @Mock
    PasswordEncoder encoder;

    @InjectMocks
    UserInfoService userInfoService;

    @Test
    void loadUserByUsername_userExists_returnsUserDetails() {
        UserInfo user = new UserInfo();
        user.setUsername("john");
        user.setPassword("encoded-password");
        user.setRoles("admin,user");

        when(repository.findByUsername("john"))
                .thenReturn(Optional.of(user));

        UserDetails result = userInfoService.loadUserByUsername("john");

        assertThat(result.getUsername()).isEqualTo("john");
        assertThat(result.getPassword()).isEqualTo("encoded-password");
        assertThat(result.getAuthorities())
                .extracting("authority")
                .containsExactlyInAnyOrder("admin", "user");
    }

    @Test
    void loadUserByUsername_userDoesNotExist_throwsException() {
        when(repository.findByUsername("missing"))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> userInfoService.loadUserByUsername("missing"))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessage("User not found");
    }

    @Test
    void addUser_newUser_encodesPasswordAndSaves() {
        UserInfo user = new UserInfo();
        user.setUsername("alice");
        user.setPassword("plainPassword");

        when(repository.findByUsername("alice"))
                .thenReturn(Optional.empty());
        when(encoder.encode("plainPassword"))
                .thenReturn("encodedPassword");

        String result = userInfoService.addUser(user);

        assertThat(result).isEqualTo("User added successfully.");
        assertThat(user.getPassword()).isEqualTo("encodedPassword");
        assertThat(user.getRoles()).isEqualTo("admin");

        verify(repository).save(user);
    }

    @Test
    void addUser_existingUser_doesNotSave() {
        UserInfo user = new UserInfo();
        user.setUsername("bob");

        when(repository.findByUsername("bob"))
                .thenReturn(Optional.of(user));

        String result = userInfoService.addUser(user);

        assertThat(result).isEqualTo("User already exists");
        verify(repository, never()).save(any());
        verify(encoder, never()).encode(any());
    }
}

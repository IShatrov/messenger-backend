package ru.mipt.messenger.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.mipt.messenger.models.SecureUser;
import ru.mipt.messenger.models.User;
import ru.mipt.messenger.repositories.UserRepository;

import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class DatabaseUserDetailsService implements UserDetailsService {

    @Autowired
    private final UserRepository userRepository;

    @Override
    public SecureUser loadUserByUsername(String username) {
        Supplier<UsernameNotFoundException> s = () -> new UsernameNotFoundException("Nickname not found");
        User user = userRepository.findUserByNickname(username).orElseThrow(s);
        return new SecureUser(user);
    }
}

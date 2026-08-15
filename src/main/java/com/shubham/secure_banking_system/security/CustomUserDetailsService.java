package com.shubham.secure_banking_system.security;

import com.shubham.secure_banking_system.entity.User;
import com.shubham.secure_banking_system.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

        private final UserRepository userRepository;

        public CustomUserDetailsService(UserRepository userRepository) {
                this.userRepository = userRepository;
        }

        @Override
        public UserDetails loadUserByUsername(String username)
                        throws UsernameNotFoundException {

                User user = userRepository.findByUsername(username)
                                .orElseThrow(() -> new UsernameNotFoundException(
                                                "User not found: " + username));

                String authority = "ROLE_" + user.getRole().getRoleName().name();

                System.out.println(
                                "User: " + user.getUsername()
                                                + " | Role: " + user.getRole().getRoleName()
                                                + " | Authority: " + authority);

                return org.springframework.security.core.userdetails.User
                                .withUsername(user.getUsername())
                                .password(user.getPassword())
                                .authorities(List.of(
                                                new SimpleGrantedAuthority(authority)))
                                .disabled(!user.getEnabled())
                                .accountLocked(user.getAccountLocked())
                                .build();
        }
}
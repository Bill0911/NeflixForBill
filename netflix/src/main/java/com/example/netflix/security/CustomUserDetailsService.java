//package com.example.netflix.security;
//
//import com.example.netflix.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Lazy;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@EnableJpaRepositories
//@Service
//public class CustomUserDetailsService implements UserDetailsService
//{
//    @Autowired
//    private final UserRepository userRepository;
//
//    public CustomUserDetailsService(UserRepository userRepository)
//    {
//        this.userRepository = userRepository;
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String accountId) throws UsernameNotFoundException
//    {
//        return userRepository.findByAccountId(Integer.parseInt(accountId))
//                .map(CustomUserDetails::new)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found with accountId: " + accountId));
//    }
//}
//

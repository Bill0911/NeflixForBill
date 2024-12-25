//package com.example.netflix.security;
//
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import com.example.netflix.entity.User;
//
//import java.util.Collection;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
//public class CustomUserDetails implements UserDetails {
//
//    private final User user;
//
//    public CustomUserDetails(User user) {
//        this.user = user;
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        // Add role as "ROLE_<ROLE_NAME>"
//        SimpleGrantedAuthority roleAuthority = new SimpleGrantedAuthority("ROLE_" + user.getRole().name());
//
//        // Add permissions
//        Collection<SimpleGrantedAuthority> permissionAuthorities = user.getRole().getPermissions().stream()
//                .map(SimpleGrantedAuthority::new)
//                .collect(Collectors.toList());
//
//        return Stream.concat(Stream.of(roleAuthority), permissionAuthorities.stream())
//                .collect(Collectors.toSet());
//    }
//
//    @Override
//    public String getPassword() {
//        return user.getPassword();
//    }
//
//    @Override
//    public String getUsername() {
//        return user.getAccountId().toString();  // Use accountId as the username
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return !user.isBlocked();
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
//}

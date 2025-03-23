package es.ticketmaster.entrega1.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import es.ticketmaster.entrega1.model.UserEntity;

import es.ticketmaster.entrega1.repository.UserRepository;

@Service
public class SecurityUserDetails implements UserDetailsService{
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        // Get the user
        UserEntity user = userRepository.findByUserName(username).orElseThrow( () -> new UsernameNotFoundException("User not found"));

        // Add the user roles in the way Spring recognize them
        List<GrantedAuthority> roles = new ArrayList<>();
        for (String role: user.getRoles()){
            roles.add(new SimpleGrantedAuthority("ROLE_ " + role));
        }

        // Return the user
        return new User(user.getUserName(), user.getPassword(),roles);
    }
}

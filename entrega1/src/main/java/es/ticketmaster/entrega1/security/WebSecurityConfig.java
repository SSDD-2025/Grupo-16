package es.ticketmaster.entrega1.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    @Autowired
    public SecurityUserDetails securityUserDetails;
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(securityUserDetails);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authenticationProvider(authenticationProvider());

        http
            .authorizeHttpRequests(authorize -> authorize
                //Public Pages
                .requestMatchers("/").permitAll()
                .requestMatchers("/sign-up/*").permitAll()
                .requestMatchers("/artist/*").permitAll()
                //Registered User Pages
                .requestMatchers("/profile").hasAnyRole("USER", "ADMIN")
                .requestMatchers("/concert/*").hasAnyRole("USER", "ADMIN")
                .requestMatchers("/ticket/*").hasAnyRole("USER", "ADMIN")
                //Administrator Pages
                .requestMatchers("/admin/*").hasAnyRole("ADMIN")
            )
            .formLogin(formLogin -> formLogin
                .loginPage("/sign-in")
                .failureUrl("/sign-in?error=true")
                .defaultSuccessUrl("/")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .permitAll()
            );
        
        return http.build();
    }
}

package es.ticketmaster.entrega1.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import es.ticketmaster.entrega1.security.jwt.JwtRequestFilter;
import es.ticketmaster.entrega1.security.jwt.UnauthorizedHandlerJwt;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
	private JwtRequestFilter jwtRequestFilter;

    @Autowired
    private UnauthorizedHandlerJwt unauthorizedHandlerJwt;

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
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}

    @Bean
    @Order(1)
    public SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
        http.authenticationProvider(authenticationProvider());
        http
            .securityMatcher("/api/**")
            .exceptionHandling(handling -> handling.authenticationEntryPoint(unauthorizedHandlerJwt));

        http
            .authorizeHttpRequests(authorize -> authorize
                    // PRIVATE ENDPOINTS:
                    // UserRestController:
                    .requestMatchers(HttpMethod.GET, "/api/users/**").hasAnyRole("USER", "ADMIN")
                    .requestMatchers(HttpMethod.POST, "/api/users/**").permitAll()
                    .requestMatchers(HttpMethod.PUT, "/api/users/**").hasAnyRole("USER", "ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/api/users/**").hasAnyRole("USER", "ADMIN")

                    // ConcertRestController
                    .requestMatchers(HttpMethod.GET, "/api/concerts/**").permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/concerts/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PUT, "/api/concerts/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/api/concerts/**").hasRole("ADMIN")

                    // ArtistRestController
                    .requestMatchers(HttpMethod.GET, "/api/artists/**").permitAll()
                    .requestMatchers(HttpMethod.POST, "/api/artists/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PUT, "/api/artists/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/api/artists/**").hasRole("ADMIN")

                    // TicketRestController
                    .requestMatchers(HttpMethod.GET, "/api/tickets/**").hasAnyRole("USER", "ADMIN")
                    .requestMatchers(HttpMethod.POST, "/api/concert/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.PUT, "/api/tickets/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.DELETE, "/api/tickets/**").hasRole("ADMIN")

                    // PUBLIC ENDPOINTS:
                    .anyRequest().permitAll());

        // Disable Form login Authentication
        http.formLogin(formLogin -> formLogin.disable());

        // Disable CSRF protection.
        http.csrf(csrf -> csrf.disable());

        // Disable Basic Authentication.
        http.httpBasic(httpBasic -> httpBasic.disable());

        // Stateless session.
        http.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // Add JWT Token filter
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
    
    @Bean
    @Order(2)
    public SecurityFilterChain webfilterChain(HttpSecurity http) throws Exception {

        http.authenticationProvider(authenticationProvider());

        http
            .authorizeHttpRequests(authorize -> authorize
                // Access to static files
                .requestMatchers("/css/**").permitAll()
                .requestMatchers("/js/**").permitAll()
                .requestMatchers("/DDBB/**").permitAll()
                .requestMatchers("/media/**").permitAll()
                .requestMatchers("/concert/*/download-poster").permitAll()
                .requestMatchers("/artist/*/download-photo").permitAll()

                // Public Pages
                .requestMatchers("/").permitAll()
                .requestMatchers("/sign-up/**").permitAll()
                .requestMatchers("/artist/**").permitAll()
                .requestMatchers("/sign-in").permitAll()
                // Error Page
                .requestMatchers("/error").permitAll()
                // Registered User Pages
                .requestMatchers("/profile/**").hasAnyRole("USER", "ADMIN")
                .requestMatchers("/concert/**").hasAnyRole("USER", "ADMIN")
                .requestMatchers("/ticket/**").hasAnyRole("USER", "ADMIN")
                // Administrator Pages
                .requestMatchers("/admin/**").hasAnyRole("ADMIN")
            )
            .formLogin(formLogin -> formLogin
                .loginPage("/sign-in")
                .loginProcessingUrl("/login")
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
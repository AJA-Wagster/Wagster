package com.example.wagster.config;

//import com.codeup.codeupspringblog.services.UserDetailsLoader;
import com.example.wagster.services.UserDetailsLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.core.userdetails.UserDetailsService;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
//will allow us to edit the MVC security for our application
@EnableWebSecurity
public class SecurityConfiguration {
    //depenency that we inject, so that we can retrieve details about the user who is trying to log in
    private UserDetailsLoader usersLoader;

    public SecurityConfiguration(UserDetailsLoader usersLoader) {
        this.usersLoader = usersLoader;
    }

    //    the @BEAN annotation means that the class is managed by spring
    @Bean
//    a class that is managed by spring, specifically to hash and unhash our user password
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
//    this class is used to manage the users authentication status
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
//    this class will provide filters for our spring security for different URL mappings
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) ->
                        requests
                        /* Pages that require authentication
                         * only authenticated users can create and edit ads */
                        .requestMatchers("/posts/create", "/posts/*/edit", "/posts/*/update", "/posts/*/delete", "/feed",  "/profile", "/profile/edit", "/profile/delete", "/events/create", "/events/*/edit", "/events/*/delete", "/location/create").authenticated()
                        /* Pages that do not require authentication
                         * anyone can visit the home page, register, login, and view ads */
                        .requestMatchers("/", "/parks", "/posts/*", "/register", "/login", "/posts","/map").permitAll()
                        // allow loading of static resources
                        .requestMatchers("/css/**", "/js/**", "/img/**").permitAll()
                )
                /* Login configuration */
                .formLogin((login) ->
                        login
                                .loginPage("/login")
                                .defaultSuccessUrl("/feed")
                )
                /* Logout configuration */
                .logout((logout) ->
                        logout
                                .logoutSuccessUrl("/"))
                .httpBasic(withDefaults());

        return http.build();
    }

}


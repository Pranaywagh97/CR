package com.cashrich.security;

import com.cashrich.entity.User;
import com.cashrich.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService())
        .passwordEncoder(NoOpPasswordEncoder.getInstance());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/cr/signup", "/cr/login", "/v2/api-docs", "/configuration/**", "/swagger*/**", "/webjars/**").permitAll()
            .antMatchers(HttpMethod.PUT, "/cr/update/**").authenticated()
            .antMatchers(HttpMethod.GET, "/fetch/{userName}").authenticated()
            .anyRequest().permitAll()
            .and()
            .httpBasic()
            .and()
            .csrf().disable();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                User user = userRepository.findByUserName(username);
                if (user == null) {
                    throw new UsernameNotFoundException("User not found with username: " + username);
                }
                return org.springframework.security.core.userdetails.User.builder()
                        .username(user.getUserName())
                        .password(user.getPassword()) 
                        .roles("USER")
                        .build();
            }
        };
    }
}

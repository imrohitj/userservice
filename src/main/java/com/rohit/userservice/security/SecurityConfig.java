package com.rohit.userservice.security;


import com.rohit.userservice.filter.CustomAuthenticationFilter;
import com.rohit.userservice.filter.CustomAuthorisationFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/** Configuration for web security mechanisms. */
@Configuration @EnableWebSecurity @AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private UserDetailsService userDetailsService;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
        super.configure(auth);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        /** Override spring default login Url */
       CustomAuthenticationFilter CustomAuthenticationFilter = new CustomAuthenticationFilter(authenticationManager());
       CustomAuthenticationFilter.setFilterProcessesUrl("/api/login");


       http.csrf().disable();
       http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

       /** Begin Authorization rules configuration. */

       // Allow all login ann signup request.
       http.authorizeRequests().antMatchers("/api/login/**").permitAll();
       http.authorizeRequests().antMatchers("/api/registration/**").permitAll();

        // Only Super Admin requests for all users list.
       http.authorizeRequests().antMatchers(HttpMethod.GET,"/api/users/**").hasAnyAuthority("ROLE_SUPER_ADMIN");

       http.authorizeRequests().antMatchers(HttpMethod.POST,"/api/user/**").hasAnyAuthority("ROLE_ADMIN");
       http.authorizeRequests().antMatchers(HttpMethod.POST,"/api/role/create").hasAnyAuthority("ROLE_ADMIN");
       http.authorizeRequests().anyRequest().authenticated();

        /** End Authorization rules configuration. */

       http.addFilter(CustomAuthenticationFilter);
       http.addFilterBefore(new CustomAuthorisationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManager() throws Exception{
        return super.authenticationManager();
    }
}

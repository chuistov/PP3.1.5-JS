package ru.chuistov.springboot.crud.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.chuistov.springboot.crud.security.LoginSuccessHandler;
import ru.chuistov.springboot.crud.services.UserDetailsServiceImpl;

// security needs
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final LoginSuccessHandler loginSuccessHandler;

    @Autowired
    public SecurityConfig(UserDetailsServiceImpl userDetailsService,
                          LoginSuccessHandler loginSuccessHandler) {
        this.userDetailsService = userDetailsService;
        this.loginSuccessHandler = loginSuccessHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()                // authorization
                .antMatchers(
                        "/",
                        "/authentication/login",
                        "/authentication/register",
                        "/css/**",
                        "/js/**",
                        "/error"
                        ).permitAll() // the only pages accessible by non-authenticated person

                // security is disabled for testing purposes (next 4 lines)
                .antMatchers("/admin/**").permitAll()//.hasRole("ADMIN")
                .antMatchers("/user").permitAll()//.hasRole("USER")
                .antMatchers("/api/**").permitAll()
                .antMatchers("/**").permitAll()

                .anyRequest().authenticated()   // all other pages only after authentication
            .and()
            .formLogin()                                // form for entering username and password
                .loginPage("/authentication/login")     // form page URL
                .loginProcessingUrl("/process_login")   // URL of page for processing username and password (for Spring Security)
                .successHandler(loginSuccessHandler) // choosing starting web page depending on user's role
//                .defaultSuccessUrl("/user", true) // URL to go in case of success
                .failureUrl("/authentication/login?error") // URL to go after entering wrong username or password
            .and()
            .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/authentication/login");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }
}

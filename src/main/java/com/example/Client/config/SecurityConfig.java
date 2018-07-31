package com.example.Client.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Qualifier("dataSource")
    @Autowired
    DataSource dataSource;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests().antMatchers("/client/delete/**","/massage/delete/**",
                "/users","/user/delete","/formUser").hasAnyAuthority("ADMIN")
                .antMatchers("/css/**","/js/**").permitAll()
        .anyRequest().authenticated().
                and().
                formLogin().loginPage("/login").permitAll()
                .and().logout().permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/error_403");

    }
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(bCryptPasswordEncoder)
                .usersByUsernameQuery("select user_name,password,enabled from user where user_name = ?")
                .authoritiesByUsernameQuery("select user_name,role from user where user_name = ?");


//                auth.inMemoryAuthentication()
//                .withUser("admin").password("{noop}123").roles("ADMIN","USER").and()
//                .withUser("user").password("{noop}123").roles("USER");

    }

}
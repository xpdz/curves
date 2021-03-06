package com.curves.franchise.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebMvcSecurity
class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/login", "/index.htm", "/logout").permitAll()
                .antMatchers("/management.htm", "/benchmarking.htm", "CA_all.htm", "ca_summary.htm", "PJ_all.htm", "pj_summary.htm", "us_report.htm").hasRole("ADMIN")
                .antMatchers("/PJ.htm").hasAnyRole("ADMIN", "USER")
                //.antMatchers("/CA.htm", "ranking.htm", "trends.htm").hasAnyRole("ADMIN", "USER", "COACH")
                .anyRequest().authenticated()
                .and().formLogin().loginPage("/index.htm").loginProcessingUrl("/login")
                    .defaultSuccessUrl("/loginSuccess", true).failureUrl("/errorMsg")
                .and().logout().permitAll().logoutSuccessUrl("/index.htm")
                .and().rememberMe()
                .and().csrf().disable();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication().dataSource(this.dataSource).passwordEncoder(new BCryptPasswordEncoder(8));
    }
}

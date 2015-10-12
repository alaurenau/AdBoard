package com.lavr.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.web.filter.CharacterEncodingFilter;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private DataSource dataSource;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("select login, password, true from User where login=?")
                .authoritiesByUsernameQuery("select login, 'ROLE_USER' from User where login=?")
                .groupAuthoritiesByUsername("select distinct g.id, g.name, g.authority " +
                        "from groups g, group_members gm " +
                        "where gm.login = ? " +
                        "and g.id = gm.group_id");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);

        http.addFilterBefore(filter, CsrfFilter.class);

        http.formLogin()
                .loginPage("/")
                .failureUrl("/?error=401")
                .defaultSuccessUrl("/cabinet")
                .and()
                .rememberMe()
                .key("superKey")
                .and()
                .logout()
                .logoutSuccessUrl("/?logout")
                .and()
                .authorizeRequests()
                .antMatchers("/cabinet", "/editAd", "/comment/*", "/ad/*")
                .hasAuthority("ROLE_USER")
                .anyRequest().permitAll();
    }
}

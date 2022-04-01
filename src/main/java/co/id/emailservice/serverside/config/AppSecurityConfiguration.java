package co.id.emailservice.serverside.config;

import co.id.emailservice.serverside.service.AppUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AppSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private AppUserDetailService appUserDetailService;

    @Autowired
    public AppSecurityConfiguration(AppUserDetailService appUserDetailService) {
        this.appUserDetailService = appUserDetailService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(appUserDetailService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
//                .antMatchers("/email/**").hasAuthority("USER")
//                .antMatchers("/emailListName/**").hasAuthority("USER")
//                .antMatchers("/konten/**").hasAuthority("USER")
//                .antMatchers("/participant/**").hasAuthority("USER")
//                .antMatchers("/template/**").hasAuthority("USER")
//                .antMatchers("/user/**").hasAuthority("USER")
                .anyRequest().permitAll()
                .and()
                .httpBasic();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean(); //To change body of generated methods, choose Tools | Templates.
    }
}

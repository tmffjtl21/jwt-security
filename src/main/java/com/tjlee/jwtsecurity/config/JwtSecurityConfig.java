package com.tjlee.jwtsecurity.config;

import com.tjlee.jwtsecurity.security.JwtAuthenticationEntryPoint;
import com.tjlee.jwtsecurity.security.JwtAuthenticationProvider;
import com.tjlee.jwtsecurity.security.JwtAuthenticationTokenFilter;
import com.tjlee.jwtsecurity.security.JwtSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.Collections;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class JwtSecurityConfig extends WebSecurityConfigurerAdapter {

    private JwtAuthenticationProvider authenticationProvicder;
    private JwtAuthenticationEntryPoint entryPoint;

    public JwtSecurityConfig(JwtAuthenticationProvider authenticationProvicder, JwtAuthenticationEntryPoint entryPoint) {
        this.authenticationProvicder = authenticationProvicder;
        this.entryPoint = entryPoint;
    }

    @Bean
    public JwtAuthenticationTokenFilter authenticationTokenFilter() {
        JwtAuthenticationTokenFilter filter = new JwtAuthenticationTokenFilter();
        filter.setAuthenticationManager(authenticationManager());
        filter.setAuthenticationSuccessHandler(new JwtSuccessHandler());
        return filter;
    }
    
    @Bean
    public AuthenticationManager authenticationManager(){
        return new ProviderManager(Collections.singletonList(authenticationProvicder));
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // default
        // this.logger.debug("Using default configure(HttpSecurity). If subclassed this will potentially override subclass configure(HttpSecurity).");
        //        ((HttpSecurity)((HttpSecurity)((AuthorizedUrl)http.authorizeRequests().anyRequest()).authenticated().and()).formLogin().and()).httpBasic();

        http.csrf().disable()
                .authorizeRequests()
                    .antMatchers("**/rest/**").authenticated()
                    .antMatchers("/token/**").permitAll()
                .and()
                    .exceptionHandling().authenticationEntryPoint(entryPoint)     // 오류를 redirect
                .and()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(authenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);  // UsernamePasswordAuthenticationFilter 전에 authenticationTokenFilter 추가
        http.headers().cacheControl();  // ?
    }
}

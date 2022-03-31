package com.sesac.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Slf4j
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity httpSecurity) {
        httpSecurity.authorizeExchange()
                .pathMatchers("/**").permitAll()
//                .pathMatchers(
//                        "/user-service/users/join",
//                        "/user-service/managers/join",
//                        "/user-service/managers/status",
//                        "/user-service/logins",
//                        "/user-service/validation/email",
//                        "/user-service/validation/name").permitAll()
//                .pathMatchers("/user-service/managers/status", "/user-service/managers/validate").hasRole("ROLE_MANAGER")
//                .anyExchange().authenticated().and()
//                .httpBasic().and()
//                .formLogin().loginPage(Constants.LOGIN_URL)                    //
//                .authenticationSuccessHandler(authenticationSuccessHandler)    //
//                .authenticationFailureHandler(authenticationFailureHandler)    //
//                .and().exceptionHandling().authenticationEntryPoint(shopHttpBasicServerAuthenticationEntryPoint)
                .and().csrf().disable();                          //
//                .logout().logoutUrl(Constants.LOGOUT_URL);       //

        return httpSecurity.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

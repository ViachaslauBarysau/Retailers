package by.itechart.retailers.config;

import by.itechart.retailers.security.jwt.JwtConfigurer;
import by.itechart.retailers.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.cors.CorsConfiguration;

import static by.itechart.retailers.constant.UrlConstants.*;

@Configuration
@EnableWebSecurity

public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;


    @Autowired
    public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                .disable()
                .cors()
                .configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues())
                .and()
                .csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(URL_LOGIN_ENDPOINT)
                .permitAll()
                .antMatchers(URL_LOGOUT_ENDPOINT)
                .hasAnyAuthority("SYSTEM_ADMIN", "DIRECTOR", "WAREHOUSE_MANAGER", "SHOP_MANAGER", "DISPATCHER", "ADMIN")
                .antMatchers(URL_BILLS)
                .hasAnyAuthority("DIRECTOR", "SHOP_MANAGER")
                .antMatchers(URL_CATEGORIES)
                .hasAuthority("DIRECTOR")
                .antMatchers(HttpMethod.GET, URL_CATEGORIES)
                .hasAuthority("DISPATCHER")
                .antMatchers(URL_CUSTOMERS)
                .hasAuthority("SYSTEM_ADMIN")
                .antMatchers(URL_INNER_APPLICATIONS)
                .hasAnyAuthority("WAREHOUSE_MANAGER", "SHOP_MANAGER")
                .antMatchers(URL_LOCATIONS)
                .hasAnyAuthority("DIRECTOR", "WAREHOUSE_MANAGER", "SHOP_MANAGER", "DISPATCHER", "ADMIN")
                .antMatchers(URL_LOCATION_PRODUCTS)
                .hasAnyAuthority("DISPATCHER", "WAREHOUSE_MANAGER", "SHOP_MANAGER")
                .antMatchers(URL_PRODUCTS)
                .hasAuthority("DISPATCHER")
                .antMatchers(URL_STATES)
                .hasAnyAuthority("SYSTEM_ADMIN", "DIRECTOR", "WAREHOUSE_MANAGER", "SHOP_MANAGER", "DISPATCHER")
                .antMatchers(URL_SUPPLIER_APPLICATIONS)
                .hasAuthority("DISPATCHER")
                .antMatchers(URL_SUPPLIERS)
                .hasAnyAuthority("DISPATCHER", "ADMIN")
                .antMatchers(URL_SUPPLIER_WAREHOUSES)
                .hasAnyAuthority("DISPATCHER", "DIRECTOR")
                .antMatchers(URL_USERS)
                .hasAuthority("ADMIN")
                .antMatchers(HttpMethod.PUT, URL_USERS)
                .hasAnyAuthority("SYSTEM_ADMIN", "DIRECTOR", "WAREHOUSE_MANAGER", "SHOP_MANAGER", "DISPATCHER")
                .antMatchers(URL_WRITE_OFF_ACTS)
                .hasAnyAuthority("DIRECTOR", "WAREHOUSE_MANAGER", "SHOP_MANAGER", "DISPATCHER")
                .anyRequest()
                .authenticated()
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider));
    }

}


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

import static by.itechart.retailers.constant.UriConstants.*;

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
                .antMatchers(URI_LOGIN_ENDPOINT)
                .permitAll()
                .antMatchers(URI_LOGOUT_ENDPOINT)
                .hasAnyAuthority("SYSTEM_ADMIN", "DIRECTOR", "WAREHOUSE_MANAGER", "SHOP_MANAGER", "DISPATCHER", "ADMIN")
                .antMatchers(URI_BILLS)
                .hasAnyAuthority("DIRECTOR", "SHOP_MANAGER")
                .antMatchers(URI_CATEGORIES)
                .hasAuthority("DIRECTOR")
                .antMatchers(HttpMethod.GET, URI_CATEGORIES)
                .hasAuthority("DISPATCHER")
                .antMatchers(URI_CUSTOMERS)
                .hasAuthority("SYSTEM_ADMIN")
                .antMatchers(URI_INNER_APPLICATIONS)
                .hasAnyAuthority("WAREHOUSE_MANAGER", "SHOP_MANAGER")
                .antMatchers(URI_LOCATIONS)
                .hasAnyAuthority("DIRECTOR", "WAREHOUSE_MANAGER", "SHOP_MANAGER", "DISPATCHER", "ADMIN")
                .antMatchers(URI_LOCATION_PRODUCTS)
                .hasAnyAuthority("DISPATCHER", "WAREHOUSE_MANAGER", "SHOP_MANAGER")
                .antMatchers(URI_PRODUCTS)
                .hasAuthority("DISPATCHER")
                .antMatchers(URI_STATES)
                .hasAnyAuthority("SYSTEM_ADMIN", "DIRECTOR", "WAREHOUSE_MANAGER", "SHOP_MANAGER", "DISPATCHER")
                .antMatchers(URI_SUPPLIER_APPLICATIONS)
                .hasAuthority("DISPATCHER")
                .antMatchers(URI_SUPPLIERS)
                .hasAnyAuthority("DISPATCHER", "ADMIN")
                .antMatchers(URI_SUPPLIER_WAREHOUSES)
                .hasAnyAuthority("DISPATCHER", "DIRECTOR")
                .antMatchers(URI_USERS)
                .hasAuthority("ADMIN")
                .antMatchers(HttpMethod.PUT, URI_USERS)
                .hasAnyAuthority("SYSTEM_ADMIN", "DIRECTOR", "WAREHOUSE_MANAGER", "SHOP_MANAGER", "DISPATCHER")
                .antMatchers(URI_WRITE_OFF_ACTS)
                .hasAnyAuthority("DIRECTOR", "WAREHOUSE_MANAGER", "SHOP_MANAGER", "DISPATCHER")
                .anyRequest()
                .authenticated()
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider));
    }

}


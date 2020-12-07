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

@Configuration
@EnableWebSecurity

public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String LOGIN_ENDPOINT = "/api/login";
    private static final String LOGOUT_ENDPOINT = "/api/logout";
    private static final String BILLS = "/api/bills/**";
    private static final String CATEGORIES = "/api/categories/**";
    private static final String CUSTOMERS = "/api/customers/**";
    private static final String INNER_APPLICATIONS = "/api/inner_applications/**";
    private static final String LOCATIONS = "/api/locations/**";
    private static final String LOCATION_PRODUCTS = "/api/location_products/**";
    private static final String PRODUCTS = "/api/products/**";
    private static final String STATES = "/api/states/**";
    private static final String SUPPLIER_APPLICATIONS = "/api/supplier_applications/**";
    private static final String SUPPLIERS = "/api/suppliers/**";
    private static final String SUPPLIER_WAREHOUSES = "/api/supplier_warehouses/**";
    private static final String USERS = "/api/users/**";
    private static final String WRITE_OFF_ACTS = "/api/write_of_acts/**";
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
                .antMatchers(LOGIN_ENDPOINT)
                .permitAll()
                .antMatchers(LOGOUT_ENDPOINT)
                .permitAll()
                .antMatchers(BILLS)
                .hasAnyAuthority("DIRECTOR", "SHOP_MANAGER")
                .antMatchers(CATEGORIES)
                .hasAuthority("DIRECTOR")
                .antMatchers(HttpMethod.GET, CATEGORIES)
                .hasAuthority("DISPATCHER")
                .antMatchers(CUSTOMERS)
                .hasAuthority("SYSTEM_ADMIN")
                .antMatchers(INNER_APPLICATIONS)
                .hasAnyAuthority("WAREHOUSE_MANAGER", "SHOP_MANAGER")
                .antMatchers(LOCATIONS)
                .hasAnyAuthority("DIRECTOR", "WAREHOUSE_MANAGER", "SHOP_MANAGER", "DISPATCHER", "ADMIN")
                .antMatchers(LOCATION_PRODUCTS)
                .hasAnyAuthority("DISPATCHER", "WAREHOUSE_MANAGER", "SHOP_MANAGER")
                .antMatchers(PRODUCTS)
                .hasAuthority("DISPATCHER")
                .antMatchers(STATES)
                .hasAnyAuthority("SYSTEM_ADMIN", "DIRECTOR", "WAREHOUSE_MANAGER", "SHOP_MANAGER", "DISPATCHER")
                .antMatchers(SUPPLIER_APPLICATIONS)
                .hasAuthority("DISPATCHER")
                .antMatchers(SUPPLIERS)
                .hasAnyAuthority("DISPATCHER", "ADMIN")
                .antMatchers(SUPPLIER_WAREHOUSES)
                .hasAnyAuthority("DISPATCHER", "DIRECTOR")
                .antMatchers(USERS)
                .hasAuthority("ADMIN")
                .antMatchers(HttpMethod.PUT, USERS)
                .hasAnyAuthority("SYSTEM_ADMIN", "DIRECTOR", "WAREHOUSE_MANAGER", "SHOP_MANAGER", "DISPATCHER")
                .antMatchers(WRITE_OFF_ACTS)
                .hasAnyAuthority("DIRECTOR", "WAREHOUSE_MANAGER", "SHOP_MANAGER", "DISPATCHER")
                .anyRequest()
                .authenticated()
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider));
    }

}


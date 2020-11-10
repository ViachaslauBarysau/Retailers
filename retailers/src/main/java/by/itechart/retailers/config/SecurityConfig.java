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
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity

public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String CUSTOMERS = "/customers/**";
    private static final String LOCATIONS = "/locations/**";
    private static final String SUPPLIERS = "/suppliers/**";
    private static final String PRODUCTS = "/products/**";
    private static final String WRITE_OFF_ACTS = "/write_of_acts/**";
    private static final String WRITE_OFF_ACT_RECORDS = "/write_of_act_records/**";
    private static final String USERS = "/users/**";
    private static final String LOGIN_ENDPOINT = "/login";
    private static final String LOGOUT_ENDPOINT = "/logout";
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
                .csrf()
                .disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(LOGIN_ENDPOINT).permitAll()
                .antMatchers(LOGOUT_ENDPOINT).permitAll()
                .antMatchers(CUSTOMERS).hasAuthority("SYSTEM_ADMIN")
                .antMatchers(LOCATIONS).hasAuthority("ADMIN")
                .antMatchers(SUPPLIERS).hasAuthority("ADMIN")
                .antMatchers(USERS).hasAuthority("ADMIN")
                .antMatchers(PRODUCTS).hasAuthority("DISPATCHER")
                .antMatchers(WRITE_OFF_ACTS).hasAuthority("DISPATCHER")
                .antMatchers(WRITE_OFF_ACT_RECORDS).hasAuthority("DISPATCHER")
                .anyRequest()
                .authenticated()
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider));
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH");
            }
        };
    }
}


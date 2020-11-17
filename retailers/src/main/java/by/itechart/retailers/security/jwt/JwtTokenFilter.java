package by.itechart.retailers.security.jwt;

import by.itechart.retailers.dto.ErrorResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

  /*  @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;


        String token = jwtTokenProvider.resolveToken((HttpServletRequest) req);
        try {
            if (token != null && jwtTokenProvider.validateToken(token)) {
                Authentication auth = jwtTokenProvider.getAuthentication(token);

                if (auth != null) {
                    SecurityContextHolder.getContext()
                                         .setAuthentication(auth);
                }
            }
        } catch (JwtAuthenticationException e) {

            // return;
        }
//        ((HttpServletResponse) res).setHeader("Access-Control-Allow-Origin", "*");
//        ((HttpServletResponse) res).setHeader("Access-Control-Allow-Methods", "*");

        filterChain.doFilter(req, res);
    }*/

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = jwtTokenProvider.resolveToken(request);
        try {
            if (token != null && jwtTokenProvider.validateToken(token)) {
                Authentication auth = jwtTokenProvider.getAuthentication(token);

                if (auth != null) {
                    SecurityContextHolder.getContext()
                                         .setAuthentication(auth);
                }
            }
        } catch (JwtAuthenticationException e) {

          /*  ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setHttpStatus(HttpStatus.UNAUTHORIZED);
            errorResponse.setMessage(e.getMessage());
            response.getWriter()
                    .write(convertObjectToJson(errorResponse));*/
           // response= new ResponseEntity<Object>(e.getMessage(), HttpStatus.UNAUTHORIZED);
            responseEntityToHttpServletResponse(new ResponseEntity<Object>(e.getMessage(), HttpStatus.UNAUTHORIZED),response);
            return;
        }
        filterChain.doFilter(request, response);
    }

    public static void responseEntityToHttpServletResponse(ResponseEntity<Object> responseEntity, HttpServletResponse response)
            throws IOException {
        for (Map.Entry<String, List<String>> header : responseEntity.getHeaders().entrySet()) {
            String chave = header.getKey();
            for (String valor : header.getValue()) {
                response.addHeader(chave, valor);
            }
        }

        response.setStatus(responseEntity.getStatusCodeValue());
        response.getWriter().write(String.valueOf(responseEntity.getBody()));
    }
}


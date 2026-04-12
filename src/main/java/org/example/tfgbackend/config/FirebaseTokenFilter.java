package org.example.tfgbackend.config;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class FirebaseTokenFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain)
            throws ServletException, IOException {


        String header = request.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            try {
                FirebaseToken decoded = FirebaseAuth.getInstance().verifyIdToken(token);
                String uid  = decoded.getUid();
                String role = (String) decoded.getClaims()
                        .getOrDefault("rol", "CLIENTE");

                // Inyectar UID en header para que los controllers lo lean
                request.setAttribute("firebaseUid", uid);

                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(
                                uid, null,
                                List.of(new SimpleGrantedAuthority("ROLE_" + role))
                        );
                SecurityContextHolder.getContext().setAuthentication(auth);

            } catch (Exception e) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write("{\"error\":\"Token inválido\"}");
                return;
            }
        }

        chain.doFilter(request, response);

    }
}

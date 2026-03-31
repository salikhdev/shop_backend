package uz.salikhdev.shop_backend.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import tools.jackson.databind.ObjectMapper;
import uz.salikhdev.shop_backend.dto.response.ErrorResponse;
import uz.salikhdev.shop_backend.entity.User;
import uz.salikhdev.shop_backend.repository.UserRepository;

import java.io.IOException;
import java.nio.file.AccessDeniedException;

@Component
@RequiredArgsConstructor
public class TokenFilter extends OncePerRequestFilter {

    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(
            @NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response,
            @NotNull FilterChain filterChain
    ) throws ServletException, IOException {

        try {
            String bearerToken = request.getHeader("Authorization");

            String token = bearerToken.substring(7);

            User user = userRepository.findByToken(token)
                    .orElseThrow(() -> new RuntimeException("Unauthorized"));

            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(
                            user,
                            null,
                            user.getAuthorities()
                    )
            );

            filterChain.doFilter(request, response);
        }
        catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write(objectMapper.writeValueAsString(
                    ErrorResponse.builder()
                            .message("Unauthorized: Token is missing or invalid")
                            .code(401)
                            .build()
            ));

        }
    }

    // RBAC

    @Override
    protected boolean shouldNotFilter(@NotNull HttpServletRequest request) throws ServletException {
        return request.getRequestURI().startsWith("/api/auth") ||
                request.getRequestURI().startsWith("/v3/api-docs") ||
                request.getRequestURI().startsWith("/swagger-ui") ||
                request.getRequestURI().startsWith("/docs");
    }
}

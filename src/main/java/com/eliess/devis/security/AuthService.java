package com.eliess.devis.security;

import com.eliess.devis.dto.LoginRequest;
import com.eliess.devis.dto.RegisterRequest;
import com.eliess.devis.dto.AuthResponse;
import com.eliess.devis.entity.AppUser;
import com.eliess.devis.repository.AppUserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
//@Slf4j
public class AuthService {

    private final AppUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private static final Logger log = 
        LoggerFactory.getLogger(AuthService.class);

    public AuthService(AppUserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService,
                       AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    // REGISTER
    public void register(RegisterRequest request) {

        AppUser user = new AppUser();
        user.setUsername(request.username());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole("ROLE_USER");

        userRepository.save(user);

        // Logger
        log.info("Nouvel utilisateur enregistre : {}", request.username());
    }

    // LOGIN
    public AuthResponse login(LoginRequest request) {

        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                request.username(),
                request.password()
            )
        );

        String token = jwtService.generateToken(request.username());

        // Logger
        log.info("Connexion reussie pour : {}", request.username());

        return new AuthResponse(token);
    }
}
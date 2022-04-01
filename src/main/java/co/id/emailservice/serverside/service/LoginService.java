package co.id.emailservice.serverside.service;

import co.id.emailservice.serverside.model.User;
import co.id.emailservice.serverside.model.dto.LoginData;
import co.id.emailservice.serverside.model.dto.LoginResponseData;
import co.id.emailservice.serverside.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LoginService {

    private AppUserDetailService appUserDetailService;
    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;

    public LoginResponseData login(LoginData loginData) {
        UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(loginData.getEmail(), loginData.getPassword());
        Authentication auth = authenticationManager.authenticate(authReq);
        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(auth);

        UserDetails userDetails = appUserDetailService.loadUserByUsername(loginData.getEmail());

        List<String> authorities = userDetails.getAuthorities()
                .stream().map(authority -> authority.getAuthority())
                .collect(Collectors.toList());

        User user = userRepository.findByEmail(loginData.getEmail()).get();

        return new LoginResponseData(user.getEmail(), user.getEmailListName(), user.getKontens(), authorities);
    }
}

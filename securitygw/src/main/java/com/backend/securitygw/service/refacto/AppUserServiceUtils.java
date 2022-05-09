package com.backend.securitygw.service.refacto;

import com.backend.securitygw.dataobject.sqlrepository.AppUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AppUserServiceUtils implements UserDetailsService {
    private static final String USER_NOT_FOUND = "user with %s not found";
    private AppUserRepository appUserRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND, email)));
    }

    public boolean isUserExists(String email) {
        return appUserRepository.findByEmail(email).isPresent();
    }
}

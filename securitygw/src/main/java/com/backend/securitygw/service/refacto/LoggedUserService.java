package com.backend.securitygw.service.refacto;

import com.backend.securitygw.common.enumerator.AppUserRole;
import com.backend.securitygw.config.PasswordEncoder;
import com.backend.securitygw.dataobject.request.ChangeEmailRequest;
import com.backend.securitygw.dataobject.request.ChangePasswordRequest;
import com.backend.securitygw.dataobject.sqlentity.AppUser;
import com.backend.securitygw.dataobject.sqlrepository.AppUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class LoggedUserService {
    final AppUserRepository appUserRepository;
    final AppUserServiceUtils appUserServiceUtils;
    final PasswordEncoder passwordEncoder;

    public void ChangeEmail(ChangeEmailRequest request) {
        AppUser appUser = (AppUser) appUserServiceUtils.loadUserByUsername(request.getCurrentEmail());

        final boolean validateCredential = passwordEncoder.validatePwd(
                request.getCurrentPwd(),
                appUser.getSalt(),
                appUser.getPassword());
        if (!validateCredential) {
            return;
        }

        appUser.setEmail(request.getNewEmail());
    }

    public void ChangePwd(ChangePasswordRequest request) {
        AppUser appUser = (AppUser) appUserServiceUtils.loadUserByUsername(request.getCurrentEmail());

        final boolean validateCredential = passwordEncoder.validatePwd(
                request.getCurrentPwd(),
                appUser.getSalt(),
                appUser.getPassword());
        if (!validateCredential) {
            return;
        }

        final String newHashedPwd = passwordEncoder.hashPwd(request.getNewPwd(), appUser.getSalt());
        appUser.setPassword(newHashedPwd);
    }

    public void grantAdminRole(String email) {
        Optional<AppUser> futureAdmin = appUserRepository.findByEmail(email);
        if (futureAdmin.isPresent()) {
            AppUser futureAdminExisting = futureAdmin.get();
            futureAdminExisting.setAppUserRole(AppUserRole.ROLE_ADMIN);

            appUserRepository.save(futureAdminExisting);
        }
    }
}

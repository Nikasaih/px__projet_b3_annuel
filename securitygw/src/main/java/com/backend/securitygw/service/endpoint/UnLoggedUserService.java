package com.backend.securitygw.service.endpoint;

import com.backend.securitygw.common.exception.AccountNotEnableExc;
import com.backend.securitygw.common.exception.CredentialNotMatchingAccount;
import com.backend.securitygw.dataobject.request.UserCurrentCredential;
import com.backend.securitygw.dataobject.response.JwtDatagram;
import com.backend.securitygw.dataobject.sqlentity.UserSqlEntity;
import com.backend.securitygw.dataobject.sqlrepository.UserSqlRepository;
import com.backend.securitygw.service.encryptor.PasswordEncoder;
import com.backend.securitygw.service.miniservices.JwtService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UnLoggedUserService {
    final PasswordEncoder passwordEncoder;
    final ModelMapper mapper = new ModelMapper();
    final UserSqlRepository userSqlRepository;
    final JwtService jwtService;

    public String signIn(UserCurrentCredential userCurrentCredential) throws CredentialNotMatchingAccount, AccountNotEnableExc {
        Optional<UserSqlEntity> user = userSqlRepository.findByEmail(userCurrentCredential.getCurrentEmail());
        if (user.isEmpty()) {
            throw new CredentialNotMatchingAccount();
        }
        if (!passwordEncoder.validatePwd(userCurrentCredential.getCurrentPwd(), user.get().getHashedPasswordSalt(), user.get().getHashedPassword())) {
            throw new CredentialNotMatchingAccount();
        }
        if (!user.get().getIsEnabled()) {
            throw new AccountNotEnableExc();
        }
        JwtDatagram jwtDatagram = mapper.map(user.get(), JwtDatagram.class);
        return jwtService.generateToken(jwtDatagram);
    }

    public void pwdForgot(String email) {
        //todo generate pwd confirmation token and send by email
    }
}

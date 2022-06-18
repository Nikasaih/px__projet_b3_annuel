package com.backend.securitygw.controller;

import com.backend.securitygw.aspect.auth.AdminRequired;
import com.backend.securitygw.aspect.auth.SuperadminRequired;
import com.backend.securitygw.common.exception.CredentialNotMatchingAccount;
import com.backend.securitygw.dataobject.request.GrantRoleRequest;
import com.backend.securitygw.service.endpoint.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/manage-roles")
public class RoleManagerController {
    @Autowired
    UserRoleService userRoleService;

    @SuperadminRequired
    @PostMapping("/grant/admin")
    public ResponseEntity<Object> grantAdmin(@RequestBody @Valid GrantRoleRequest grantRoleRequest, BindingResult result) {
        if (result.hasErrors()) {
            String errors = result.getAllErrors().toString();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }
        try {
            userRoleService.grantAdminRole(grantRoleRequest.getEmail());
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
        } catch (CredentialNotMatchingAccount e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @AdminRequired
    @PostMapping("/grant/store-manager")
    public ResponseEntity<Object> grantStoreManager(@RequestBody @Valid GrantRoleRequest grantRoleRequest, BindingResult result) {
        if (result.hasErrors()) {
            String errors = result.getAllErrors().toString();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }
        try {
            userRoleService.grantStoreManagerRole(grantRoleRequest.getEmail());
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
        } catch (CredentialNotMatchingAccount e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

}
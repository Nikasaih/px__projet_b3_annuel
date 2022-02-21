package btree.projetpro.backend.lib.dataobject.request;

import lombok.Getter;

@Getter
public class ChangePasswordRequest {
    private String email;
    private String currentPwd;
    private String newPwd;
}

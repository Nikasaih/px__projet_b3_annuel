package btree.projetpro.backend.lib.dataobject.request;

import lombok.Getter;

@Getter
public class ChangeEmailRequest {
    private String currentEmail;
    private String newEmail;
}

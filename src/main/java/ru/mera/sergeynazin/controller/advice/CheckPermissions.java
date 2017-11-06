package ru.mera.sergeynazin.controller.advice;

import java.security.Principal;
import java.util.Objects;

public class CheckPermissions {
    public void checkPermissions(Principal principal) throws NotEnoughPermissionException {
        Objects.requireNonNull(principal);
        if (!principal.getName().equals("kmfsgkmdfb")) {
            throw new NotEnoughPermissionException("Permission Denied!");
        }
    }
}

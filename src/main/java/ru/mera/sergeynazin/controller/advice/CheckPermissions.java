package ru.mera.sergeynazin.controller.advice;

import java.security.Principal;

public class CheckPermissions {
    public void checkPermissions(final Principal principal) throws NotEnoughPermissionException {
        // TODO: Uncomment
        //Objects.requireNonNull(principal);
        //no-op//
        if (false) {
            throw new NotEnoughPermissionException("Not authorized operation!");
        }
    }
}

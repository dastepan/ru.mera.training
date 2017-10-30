package ru.mera.training.shawerma.objects.Users;

import ru.mera.training.shawerma.model.SecureLibrary;

// TODO продумать реализацию пользователей
public abstract class AbstractUser implements SecureLibrary {
    private String login;
    private String password;

    @Override
    public boolean login() {
        return false;
    }

    @Override
    public void logout() {

    }
}

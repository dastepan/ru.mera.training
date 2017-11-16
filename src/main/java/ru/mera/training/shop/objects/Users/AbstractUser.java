package ru.mera.training.shop.objects.Users;

import ru.mera.training.shop.model.interfaces.SecureLibrary;

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

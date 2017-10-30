package ru.mera.training.shawerma.objects.Users;
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

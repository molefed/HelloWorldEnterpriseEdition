package ru.molefed.controller.dto;

import ru.molefed.db.entity.AEntityWithNameAndId;

@SuppressWarnings("unused")
public class SignInResponseTO extends RefreshTokenResponseTO {
    private AEntityWithNameAndId user;
    private String refreshToken;
    protected long refreshExpiresIn;

    public AEntityWithNameAndId getUser() {
        return user;
    }

    public void setUser(AEntityWithNameAndId user) {
        this.user = user;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public long getRefreshExpiresIn() {
        return refreshExpiresIn;
    }

    public void setRefreshExpiresIn(long refreshExpiresIn) {
        this.refreshExpiresIn = refreshExpiresIn;
    }

}

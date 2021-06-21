const TOKEN_KEY = 'token';

export type TokenInfo = (DTO.SignInResponseTO & {
    expiresTime: number;
    refreshExpiresTime: number;
});

export function getToken(): TokenInfo | undefined {
    const tokenString = localStorage.getItem(TOKEN_KEY);
    if (tokenString) {
        return JSON.parse(tokenString);
    }
}

export function setToken(userToken: TokenInfo | undefined) {
    if (userToken) {
        calcExpiredDates(userToken);
        localStorage.setItem(TOKEN_KEY, JSON.stringify(userToken));
    } else {
        localStorage.removeItem(TOKEN_KEY);
    }
}

export function refreshToken(refreshToken: DTO.RefreshTokenResponseTO): TokenInfo {
    const token = getToken();
    if (token) {
        token.token = refreshToken.token;
        token.expiresIn = refreshToken.expiresIn;
        setToken(token);
        return token;
    }

    throw Error("Not store token");
}

function calcExpiredDates(userToken: TokenInfo) {
    const difInSec = 5;
    userToken.expiresTime = calcExpiredDateTime(userToken.expiresIn, difInSec);
    userToken.refreshExpiresTime = calcExpiredDateTime(userToken.refreshExpiresIn, difInSec);
}

function calcExpiredDateTime(expiresIn: number, difInSec: number) {
    const now = new Date();
    now.setSeconds(now.getSeconds() + expiresIn - difInSec);
    return now.getTime();
}

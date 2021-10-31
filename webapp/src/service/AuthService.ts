import {api} from "../utils/http";
import {getToken, setToken, TokenInfo} from "./TokenFolderService";

function goToRootFolder() {
    window.location.href = '/';
}

export async function logout() {
    const token = getToken();

    if (token) {
        await api.post<DTO.RefreshTokenRequestTO, void>("/auth/signout", {
            token: token.token
        });

        goToRootFolder();
        setToken(undefined);
    }
}

async function loginUser(credentials: DTO.SignInRequestTO): Promise<DTO.SignInResponseTO> {
    return api.post<DTO.SignInRequestTO, DTO.SignInResponseTO>("/auth/generateToken", credentials);
}

export async function login(username: string, password: string) {
    const token = await loginUser({
        username,
        password
    });

    if (token) {
        setToken(token as TokenInfo);
        goToRootFolder();
    }
}

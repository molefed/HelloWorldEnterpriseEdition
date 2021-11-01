import {api, axiosCreate} from "../utils/http";
import {getToken, setToken, TokenInfo} from "./TokenFolderService";
import {AxiosResponse} from "axios";

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

export async function refreshToken(refreshToken: string): Promise<DTO.RefreshTokenResponseTO> {
    const response: AxiosResponse = await axiosCreate().post<DTO.RefreshTokenRequestTO, AxiosResponse>(
        "/auth/refreshToken",
        {token: refreshToken}
    );

    return response.data;
}

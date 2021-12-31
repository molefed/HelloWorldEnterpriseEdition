import {api, axiosCreate} from "../utils/http";
import {getToken, setToken, TokenInfo} from "./TokenFolderService";
import {AxiosResponse} from "axios";

function goToRootFolder() {
    window.location.href = '/';
}

export function goToRootAsAnonimus() {
    setToken(undefined);
    goToRootFolder();
}


export async function logout() {
    const token = getToken();

    if (token) {
        await api.post<DTO.RefreshTokenRequestTO, void>("/auth/signout", {
            token: token.token
        });

        goToRootAsAnonimus();
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

export async function requestRefreshToken(refreshToken: string): Promise<AxiosResponse> {
    const response: AxiosResponse = await axiosCreate().post<DTO.RefreshTokenRequestTO, AxiosResponse>(
        "/auth/refreshToken",
        {token: refreshToken}
    );

    return response;
}

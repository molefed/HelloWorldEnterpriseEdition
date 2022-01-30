import {api} from "../utils/http";

export async function loadUser(pattern: string): Promise<DTO.AppUserDto[]> {
    return api.post<DTO.SearchAppUserDTO, DTO.AppUserDto[]>("/api/v1/users/search", {
        pattern: pattern
    } as DTO.SearchAppUserDTO);
}

export async function saveUser(user: DTO.AppUserDto): Promise<DTO.AppUserDto> {
    return api.post<DTO.SearchAppUserDTO, DTO.AppUserDto>("/api/v1/users/save", user);
}

export async function recovery(email: string) {
    const request: DTO.RecoveryTO = {
        email: email
    };

    return api.post<DTO.RecoveryTO, void>("/api/v1/users/recovery", request);
}

export async function setupPasswordByKey(key: string, password: string): Promise<DTO.AppUserDto> {
    return api.post<DTO.SetupPasswordByKeyTO, DTO.AppUserDto>("/api/v1/users/setup-password-by-key",
        {
            key: key,
            password: password
        });
}

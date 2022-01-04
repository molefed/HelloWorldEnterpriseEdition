import {api} from "../utils/http";

export async function loadUser(pattern: string): Promise<DTO.AppUserDto[]> {
    return api.post<DTO.SearchAppUserDTO, DTO.AppUserDto[]>("/api/v1/users/search", {
        pattern: pattern
    } as DTO.SearchAppUserDTO);
}

export async function saveUser(user: DTO.AppUserDto): Promise<DTO.AppUserDto[]> {
    return api.post<DTO.SearchAppUserDTO, DTO.AppUserDto[]>("/api/v1/users/save", user);
}

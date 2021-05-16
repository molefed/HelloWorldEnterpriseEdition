import {useState} from 'react';

export const getToken = () => {
    const tokenString = localStorage.getItem('token');
    if (tokenString) {
        return JSON.parse(tokenString) as DTO.SignInResponseTO;
    }
};

export default function useToken() {
    const [token, setToken] = useState(getToken());

    const saveToken = (userToken: DTO.SignInResponseTO | undefined) => {
        if (userToken) {
            localStorage.setItem('token', JSON.stringify(userToken));
        } else {
            localStorage.removeItem('token');
        }

        setToken(userToken);
    };

    return {
        setToken: saveToken,
        token
    }
}

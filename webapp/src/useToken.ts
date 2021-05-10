import {useState} from 'react';

export type UserToken = {
    token: string;
}

export const getToken = () => {
    const tokenString = localStorage.getItem('token');
    if (tokenString) {
        const userToken: UserToken = JSON.parse(tokenString);
        return userToken.token
    }
};

export default function useToken() {
    const [token, setToken] = useState(getToken());

    const saveToken = (userToken: UserToken) => {
        localStorage.setItem('token', JSON.stringify(userToken));
        setToken(userToken.token);
    };

    return {
        setToken: saveToken,
        token
    }
}

import React, {useState} from 'react';
import PropTypes from 'prop-types';
import {UserToken} from '../../useToken';

type Credentials = {
    username: string,
    password: string
}

async function loginUser(credentials: Credentials): Promise<UserToken|void> {
    return fetch('http://localhost:8085', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(credentials)
    })
        .then(data => /*data.json()*/ {
            return { token: 't123'}
        })
        .catch(reason => console.log(reason));
}

export default function Login({setToken}: { setToken: (token: UserToken) => void }) {
    const [username, setUserName] = useState("");
    const [password, setPassword] = useState("");

    const handleSubmit = async (e: React.FormEvent) => {
        e.preventDefault();
        const token: UserToken | void = await loginUser({
            username,
            password
        } as Credentials);

        if (token) {
            setToken(token);
        }
    }

    return (
        <div className="login-wrapper">
            <h1>Please Log In</h1>
            <form onSubmit={handleSubmit}>
                <label>
                    <p>Username</p>
                    <input type="text" onChange={e => setUserName(e.target.value)}/>
                </label>
                <label>
                    <p>Password</p>
                    <input type="password" onChange={e => setPassword(e.target.value)}/>
                </label>
                <div>
                    <button type="submit">Submit</button>
                </div>
            </form>
        </div>
    )
}

Login.propTypes = {
    setToken: PropTypes.func.isRequired
};

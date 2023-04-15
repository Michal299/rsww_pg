import { createContext, useState, useEffect } from "react";
import jwt_decode from "jwt-decode";
import { useNavigate } from "react-router-dom";


const AuthContext = createContext();
export default AuthContext;


export const AuthProvider = ({children}) => {

    const navigate = useNavigate();

    const authTokenUrl = 'http://127.0.0.1:8000/api/token/';
    const refreshAuthTokenUrl = authTokenUrl + 'refresh/';

    let localStorageAuthTokens = localStorage.getItem('authTokens');

    let [authTokens, setAuthTokens] = useState(() => localStorageAuthTokens ? JSON.parse(localStorageAuthTokens) : null);
    let [user, setUser] = useState(() => localStorageAuthTokens ? jwt_decode(localStorageAuthTokens) : null);
    let [loading, setLoading] = useState(true);


    let loginUser = async (username, password) => {
        let response = await fetch(authTokenUrl, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                username: username,
                password: password
            })
        });
        let data = await response.json();

        if(response.status === 200){
            setAuthTokens(data);
            setUser(jwt_decode(data.access));
            localStorage.setItem('authTokens', JSON.stringify(data));
        }

        return response;
    }

    let logoutUser = () => {
        setAuthTokens(null);
        setUser(null);
        localStorage.removeItem('authTokens');
    }

    let updateToken = async () => {
        console.log('UPDATE');
        let response = await fetch(refreshAuthTokenUrl, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({refresh: authTokens?.refresh})
        });
        let data = await response.json();

        if(response.status == 200) {
            setAuthTokens(data);
            setUser(jwt_decode(data.access));
            localStorage.setItem('authTokens', JSON.stringify(data));
        } else {
            logoutUser();
            navigate('/login');
        }

        if(loading){
            setLoading(false);
        }

        return response;
    }

    let contextData = {
        loginUser: loginUser,
        logoutUser: logoutUser,
        user: user,
        authTokens: authTokens
    }

    useEffect(() => {
        if(loading) {
            updateToken();
        }

        let fourteenMinutes = 14 * 60 * 1000;

        let interval =  setInterval(()=> {
            if(authTokens){
                updateToken();
            }
        }, fourteenMinutes);
        return () => clearInterval(interval)

    }, [authTokens, loading])

    return (
        <AuthContext.Provider value={contextData}>
            {loading ? null : children}
        </AuthContext.Provider>
    )
}
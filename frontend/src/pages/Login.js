import React, { useContext, useState, useEffect } from "react";
import { Navigate, useNavigate } from 'react-router-dom';

import Form from "react-bootstrap/Form";
import FloatingLabel from 'react-bootstrap/FloatingLabel';
import InputGroup from 'react-bootstrap/InputGroup';
import Button from 'react-bootstrap/Button';
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faUser, faLock, faSun, faRightToBracket } from "@fortawesome/free-solid-svg-icons";

import AuthContext from "../context/AuthContext";


const Login = () => {

    let [username, setUsername] = useState(null);
    let [password, setPassword] = useState(null);
    let {loginUser, user} = useContext(AuthContext);
    let [isLoggingIn, setIsLoggingIn] = useState(false);

    const navigate = useNavigate();

    let handleSumbit = async (event) => {
        event.preventDefault();
        
        setIsLoggingIn(true);
        let response = await loginUser(username, password);
        setIsLoggingIn(false);
        
        if(response.status === 200) {
            navigate('/');
        } else {
            alert('Something gone wrong. Status code: ' + response.status);
        }
    }
    
    return (
        user ? <Navigate to={'/'} /> :

        <div className="row">
        <div className="d-none d-lg-block d-lg-block col-lg-3"></div>
        
        <div className="login col col-lg-6 d-flex flex-column min-vh-100 g-5">
        <div className="mx-auto my-auto align-middle w-100 rounded p-3 border shadow">
            <div className="text-warning fs-3 text-center mb-3 border-bottom pb-3">
                <FontAwesomeIcon icon={ faSun } className="fa-fw fa-spin me-1" />
                Sunny Holidays
            </div>
            <Form onSubmit={handleSumbit}>
                <InputGroup className="mb-2">
                    <InputGroup.Text>
                        <FontAwesomeIcon icon={faUser} className="fa-fw" />
                    </InputGroup.Text>
                    <FloatingLabel
                        controlId="usernameInput"
                        label="Username"
                    >
                        <Form.Control 
                            type="text" 
                            placeholder="Username" 
                            onChange={(e) => setUsername(e.target.value)}
                        />
                    </FloatingLabel>
                </InputGroup>
                <InputGroup className="mb-3">
                    <InputGroup.Text>
                        <FontAwesomeIcon icon={faLock} className="fa-fw" />
                    </InputGroup.Text>
                    <FloatingLabel
                        controlId="passwordInput"
                        label="Password"
                    >
                        <Form.Control 
                            type="password" 
                            placeholder="Password"
                            onChange={(e) => setPassword(e.target.value)}
                        />
                    </FloatingLabel>
                </InputGroup>
                <Button type="submit" variant="outline-warning" className="w-100 mb-2">
                    <FontAwesomeIcon icon={faRightToBracket} className="fa-fw me-1" />
                    Sign in
                </Button>
            </Form>
        </div>
        </div>
        
        </div>
    )
}

export default Login;
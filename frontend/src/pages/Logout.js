import React from "react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faSun } from "@fortawesome/free-solid-svg-icons";
import { Link } from "react-router-dom";
import Login from "./Login";
import { Button } from "react-bootstrap";


export default class Logout extends React.Component {

    render() {
        return (
            <div className="row">
            <div className="d-none d-lg-block d-lg-block col-lg-3"></div>
            
            <div className="login col col-lg-6 d-flex flex-column min-vh-100 g-5">
            <div className="mx-auto my-auto align-middle w-100 rounded p-3 border shadow">
                <div className="text-warning fs-3 text-center mb-3 border-bottom pb-3">
                    <FontAwesomeIcon icon={ faSun } className="fa-fw fa-spin me-1" />
                    Sunny Holidays
                </div>
                <div className="fw-bold fs-3 text-center">
                    <p>Thanks for Your time You have spent with us</p>
                    <div className="border-top"></div>
                    <p>See you next time ^^</p>
                </div>
                <Link to={'/login'}>
                    <Button variant="success"
                        className="w-100"
                    >
                        Go to login page
                    </Button>
                </Link>
            </div>
            </div>
            
            </div>
        )
    }
}

import Nav from "react-bootstrap/Nav";
import Navbar from "react-bootstrap/Navbar";
import Container from "react-bootstrap/Container";
import NavDropdown from "react-bootstrap/NavDropdown"
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faSun, faUserCircle, faRightFromBracket } from "@fortawesome/free-solid-svg-icons";
import { Link, useNavigate } from "react-router-dom";
import { useContext } from "react";
import AuthContext from "../context/AuthContext";


const CollapsibleNavbar = () => {
    const {logoutUser, user} = useContext(AuthContext);
    const navigate = useNavigate();

    let handleClick = (e) => {
        logoutUser();
        navigate('/logout');
    }

    return (
        <Navbar collapseOnSelect expand="lg" bg="dark" variant="dark">
            <Container fluid="lg">
                <Navbar.Brand href="/" className="text-warning">
                    <FontAwesomeIcon icon={ faSun } className="fa-fw fa-spin me-1" />
                    Sunny Holidays
                </Navbar.Brand>
                {   user ? 
                    <>
                    <Navbar.Toggle aria-controls="responsive-navbar-nav" />
                    <Navbar.Collapse id="responsive-navbar-nav" className="d-flex justify-content-end" >
                        <Nav>
                            <NavDropdown
                                title={<FontAwesomeIcon icon={faUserCircle} className="fa-xl" />}
                            >
                                <NavDropdown.Header>Your Account</NavDropdown.Header>
                                <NavDropdown.Divider className="my-1"></NavDropdown.Divider>
                                <NavDropdown.Item className="text-danger" onClick={handleClick}>
                                    <FontAwesomeIcon icon={faRightFromBracket} className="fa-fw me-1" />
                                    Log Out
                                </NavDropdown.Item>
                            </NavDropdown>
                        </Nav>
                    </Navbar.Collapse>
                    </>
                    :
                    null
                }
            </Container>
        </Navbar>
    )
}


export default CollapsibleNavbar;
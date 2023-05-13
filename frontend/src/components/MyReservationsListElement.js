import React from "react";
import { Link } from "react-router-dom";
import Card from "react-bootstrap/Card";
import Button from "react-bootstrap/Button";
import ListGroup from "react-bootstrap/ListGroup";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faLocationDot, faCalendar, faHotel, faCoins, faStar } from "@fortawesome/free-solid-svg-icons";


const MyReservationsListElement = ({reservation}) => {

    const handleCancelBookingClick = (event) => {
        event.preventDefault();
        let reservations = JSON.parse(sessionStorage.getItem("reservations"));
        console.log(reservations);
        reservations = reservations.filter((r) => {
            return r.tripId != reservation.tripId;
        });
        console.log(reservations);
        sessionStorage.setItem("reservations", JSON.stringify(reservations));
    };

    const handlePayForBookingClick = (event) => {
        event.preventDefault();
    };

    return (
        <Card className="w-75 mx-auto p-3 shadow mb-3">
            <div className="row g-0">
                <div className="col-md-6">
                    <Card.Img src={reservation.trip.hotel.photo} alt="Hotel Image" />
                </div>
                <div className="col-md-6">
                    <Card.Body>
                        <Card.Title className="border-bottom">
                            <h5 className="text-success">
                                <FontAwesomeIcon icon={faLocationDot} className="me-1 fa-fw"/>
                                {reservation.trip.hotel.country} | {reservation.trip.hotel.city}
                            </h5>
                        </Card.Title>
                        <Card.Text>
                            <ListGroup className="list-group-flush">
                                <ListGroup.Item className="px-1">
                                    <FontAwesomeIcon icon={faCalendar} className="me-1 fa-fw" />
                                    {reservation.trip.transport.departureDateTime}
                                </ListGroup.Item>
                                <ListGroup.Item className="px-1">
                                    <FontAwesomeIcon icon={faHotel} className="me-1 fa-fw" />
                                    <span className="me-2">{reservation.trip.hotel.name}</span>
                                    {reservation.trip.hotel.stars} <FontAwesomeIcon icon={faStar}/>
                                </ListGroup.Item>
                                <ListGroup.Item className="px-1">
                                    <FontAwesomeIcon icon={faCoins} className="me-1 fa-fw" />
                                    {reservation.trip.tripPrice} zł
                                </ListGroup.Item>
                            </ListGroup>
                        </Card.Text>
                        <div className="mb-2">
                            <Button variant="outline-success" className="w-100" onClick={handlePayForBookingClick}>Pay for trip</Button>
                        </div>
                        <div>
                            <Button variant="outline-danger" className="w-100" onClick={handleCancelBookingClick}>Cancel booking</Button>
                        </div>
                    </Card.Body>
                </div>
            </div>
        </Card>
    )
}
export default MyReservationsListElement;
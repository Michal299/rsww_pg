import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import useFetch from "../hooks/useFetch";
import UrlBuilder from "../components/UrlBuilder";
import TripsListElementSkeleton from "../components/TripsListElementSkeleton";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { 
    faStar, faHotel, faLocationDot, faTruckPlane, faPlaneDeparture, 
    faPlaneArrival, faClock, faSignature, faRightLong, faPersonShelter,
    faUtensils
} from "@fortawesome/free-solid-svg-icons";
import { Button, Form } from "react-bootstrap";
import TripRoomsElement from "../components/TripRoomsElement";
import TripFoodElement from "../components/TripFoodElement";


const trip = {
    hotel: {
        "id": 1,
        "name": "Nazwa hotelu",
        "country": "Kraj hotelu",
        "city": "Miasto hotelu", // may be null/empty
        "stars": 3, // integer 0 - 5, 0 jesli nie ma danych
        "description": "...",
        "photo": "https://r.cdn.redgalaxy.com/scale/o2/TUI/hotels/NBE16043/S22/19905149.jpg?dstw=1200&dsth=644.0795159896282&srcw=1157&srch=621&srcx=1%2F2&srcy=1%2F2&srcmode=3&type=1&quality=80",
        "rooms": [ // dostępne pokoje, wedlug typu
            {
                "key": 1,
                "capacity": 4, // max liczba osob w pokoju
                "name": "Pokój 4-osobowy", // nazwa widoczna na karcie
                "features": "klimatyzacja|TV|telefon" // lista zapisana w formacie string oddzielona |
            },
            {
                "key": 2,
                "capacity": 6, // max liczba osob w pokoju
                "name": "Pokój 6-osobowy", // nazwa widoczna na karcie
                "features": "klimatyzacja|TV|telefon|suszarka|lodówka" // lista zapisana w formacie string oddzielona |
            }
        ],
        "airport": "Paris [CDG]",
        "food": "none|breakfast|two-dishes|three-dishes|all-inclusive" // enum, minimum jedna z tych opcji, może nie być wcale
    },
    transport: {
        "departureAirport": "Warsaw (KOD)",
        "arrivalAirport": "Paris (CDG)",
        "departureDateTime": "26.06.2022 16:59",
        "arrivalDate": "26.06.2022 18:00", // format dd.mm.yyyy hh:MM
        "travelTime": 65, // podane w minutach,
        "placesCount": 50, // liczba miejsc w samolocie
        "placesOccupied": 15 // liczba zajetych miejsc
    }
}


const TripDetails = () => {
    const checkReservation = () => {
        const reservations = sessionStorage.getItem('reservations');
        if(reservations != null) {
            for(let reservation of JSON.parse(reservations))
                if(reservation.tripId == id)
                    return true;
        }
        return false;
    }

    const { id } = useParams();

    let [chosenRoom, setChosenRoom] = useState(null);
    let [chosenFood, setChosenFood] = useState(null);
    let [booked, setBooked] = useState(() => checkReservation());

    const urlBuilder = new UrlBuilder();
    let {data, isPending, error} = useFetch(urlBuilder.build('REACT_APP_API_ROOT_URL', 'REACT_APP_API_TRIPS_URL') + '?id=' + id);
    if(!isPending && data == null)
        data = trip;

    let handleClickBookTrip = (event) => {
        event.preventDefault();
        
        if(chosenRoom == null || chosenFood == null) {
            alert('Please select room and food before booking');
            return;
        }
        let reservations = sessionStorage.getItem('reservations');
        if(reservations == null)
            reservations = [];
        else
            reservations = JSON.parse(reservations);
        
        reservations.push({
            tripId: id,
            trip: data,
            room: chosenRoom,
            food: chosenFood
        });
        sessionStorage.setItem('reservations', JSON.stringify(reservations));
        setBooked(true);
    }

    return (
        isPending ? <TripsListElementSkeleton /> :

        <div className="tripDetails">
        <div className="my-3 row">
            <div className="col-12 col-lg-9">
                <img className="img-fluid rounded" loading="lazy" src={data.hotel.photo} alt="Hotel Photo" />
            </div>
            <div className="col-12 col-lg-3">
                <div className="text-center border-bottom border-top mb-2 py-2">
                    If you want to be 100% sure you will buy this trip you have to book it
                </div>
                <Button variant="success w-100" onClick={handleClickBookTrip} className={booked ? "disabled" : ""}>
                    <FontAwesomeIcon icon={faSignature} className="fa-fw me-1" />
                    Book This Trip
                </Button>
            </div>
        </div>
        <div>   
            <div className="mb-2 pb-2 text-warning border-bottom d-flex justify-content-start w-100 align-items-center fw-bold">
                <FontAwesomeIcon icon={faHotel} className="fa-fw me-2 bg-warning p-2 rounded text-white" />
                <div className="me-2">{trip.hotel.name}</div>
                <div className="me-2">{trip.hotel.stars}<FontAwesomeIcon icon={faStar} className="fa-fw"/></div>
            </div>
            <div className="mb-2 pb-2 text-info border-bottom d-flex justify-content-start w-100 align-items-center fw-bold">
                <FontAwesomeIcon icon={faLocationDot} className="fa-fw me-2 bg-info p-2 rounded text-white" />
                <div className="me-2">{trip.hotel.country} | {trip.hotel.city}</div>
            </div>
            <div className="mb-2 pb-2 text-success border-bottom d-flex justify-content-start w-100 align-items-top">
                <FontAwesomeIcon icon={faTruckPlane} className="fa-fw me-2 bg-success p-2 rounded text-white my-auto" />
                <div className="me-2">
                    <div><FontAwesomeIcon icon={faPlaneDeparture} className="fa-fw me-1"/> Departure</div>
                    <div>{trip.transport.departureAirport}</div>
                    <div>{trip.transport.departureDateTime}</div>
                </div>
                <div className="mx-3 my-auto">
                    <FontAwesomeIcon icon={faRightLong} />
                </div>
                <div className="mx-2 text-center">
                    <div><FontAwesomeIcon icon={faClock} className="fa-fw me-1"/>Travel time</div>
                    <div>{trip.transport.travelTime} min</div>
                </div>
                <div className="mx-3 my-auto">
                    <FontAwesomeIcon icon={faRightLong} />
                </div>
                <div className="mx-2">
                    <div><FontAwesomeIcon icon={faPlaneArrival} className="fa-fw me-1"/>Arrival</div>
                    <div>{trip.transport.arrivalAirport}</div>
                    <div>{trip.transport.arrivalDate}</div>
                </div>
            </div>
        </div>
        <div>
            <div className="border-bottom pb-2 mb-2">
                <div>{trip.hotel.description}</div>
            </div>
            <div className="border-bottom mb-2 pb-2">
                <div className="mb-2 text-success d-flex justify-content-start fw-bold">
                    <FontAwesomeIcon icon={faUtensils} className="fa-fw me-2 bg-success p-2 rounded text-white" />
                    <div className="my-auto">Food</div>
                </div>
                <div className="d-flex justify-items-start">
                    {trip.hotel.food.split("|").map((food) => (
                        <TripFoodElement food={food} setFood={setChosenFood} />
                    ))}
                </div>
            </div>
            
            <div>
                <div className="pb-2 mb-2 text-success d-flex justify-content-start fw-bold">
                    <FontAwesomeIcon icon={faPersonShelter} className="fa-fw me-2 bg-success p-2 rounded text-white" />
                    <div className="my-auto">Rooms</div>
                </div>
                <div className="row row-cols-1 row-cols-lg-4">
                    {trip.hotel.rooms.map((room) => (
                        <TripRoomsElement room={room} setRoom={setChosenRoom} />
                    ))}
                </div>
            </div>
        </div>
        </div>
    )
}

export default TripDetails;
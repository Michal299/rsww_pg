import React, { useState } from "react";
import { Navigate, useNavigate } from 'react-router-dom';
import Button from 'react-bootstrap/Button';
import Form from 'react-bootstrap/Form';
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faPeopleGroup, faMagnifyingGlass } from "@fortawesome/free-solid-svg-icons";

import TripDestinationInput from "../components/TripDestinationInput";
import TripDateInput from "../components/TripDateInput";
import TripDeparturePlaceInput from "../components/TripDeparturePlaceInput";
import TripPeopleCollapse from "../components/TripPeopleCollapse";
import TripsListElement from "../components/TripsListElement";
import TripsListElementSkeleton from "../components/TripsListElementSkeleton";
import useFetch from "../hooks/useFetch";
import UrlBuilder from "../components/UrlBuilder";

const trips = {
    trips: [
        {
            "id": 1,
            "hotel": {
                "id": 1,
                "name": "Samira Club Spa & Aquapark",
                "stars": 3,
                "place": "Kraj / Miasto", // concat kraju i miasta
                "photo": "https://r.cdn.redgalaxy.com/scale/o2/TUI/hotels/NBE16043/S22/19905149.jpg?dstw=1200&dsth=644.0795159896282&srcw=1157&srch=621&srcx=1%2F2&srcy=1%2F2&srcmode=3&type=1&quality=80",

            },
            "tripPrice": 1800, // ca
            "dateStart": "26.06.2022 16:59",
            "dateEnd": "28.06.2022 16:59"
        },
        {
            "id": 2,
            "hotel": {
                "id": 1,
                "name": "Hotel Ładny & Plaża",
                "stars": 3,
                "place": "Kraj / Miasto", // concat kraju i miasta
                "photo": "https://r.cdn.redgalaxy.com/scale/o2/TUI/hotels/AYT13043/S22/20150564.jpg?dstw=1200&dsth=644.0795159896282&srcw=1157&srch=621&srcx=1%2F2&srcy=1%2F2&srcmode=3&type=1&quality=80",

            },
            "tripPrice": 10400, // ca
            "dateStart": "22.03.2022 16:59",
            "dateEnd": "30.03.2022 16:59"
        }
    ]
}


const Home = () => {
    
    let [destination, setDestination] = useState("all");
    let [startDate, setStartDate] = useState((new Date()).toISOString().substring(0, 10));
    let [departurePlace, setDeparturePlace] = useState("all");
    let [people3To9, setPeople3To9] = useState(0);
    let [people10To17, setPeople10To17] = useState(0);
    let [peopleOver18, setPeopleOver18] = useState(0);
    let [peopleCollapseOpen, setPeopleCollapseOpen] = useState(false);

    const maxParticipants = 8;
    const navigate = useNavigate();
    const urlBuilder = new UrlBuilder();

    let {data: destinations, isPending: isDestinationsPending, error: destinationsError} = useFetch(urlBuilder.build('REACT_APP_API_ROOT_URL', 'REACT_APP_API_TRIPS_DESTINATIONS_URL'));
    let {data: departurePlaces, isPending: isDeparturePlacesPending, error: departurePlacesError} = useFetch(urlBuilder.build('REACT_APP_API_ROOT_URL', 'REACT_APP_API_TRIPS_DEPARTURE_PLACES_URL'));

    destinations = destinations == null ? ['Egipt', 'Turcja'] : destinations;
    departurePlaces = departurePlaces == null ? ['Warszawa (WAW)', 'Gdańsk (GDA)'] : departurePlaces;
    let {data: exampleTrips, isPending: isExampleTripsPending, error: exampleTripsError} = useFetch(
        urlBuilder.build('REACT_APP_API_ROOT_URL', 'REACT_APP_API_TRIPS_URL')+`?destination=${destinations[0]}&departurePlace=${departurePlaces[0]}&startDate=${(new Date()).toISOString().substring(0, 10)}&peopleOver18=2`
    );
    if(!isExampleTripsPending) {
        exampleTrips = exampleTrips != null ? exampleTrips : trips.trips;
    }

    let handleSubmit = (event) => {
        event.preventDefault();

        let urlParams = `?destination=${destination}&` + 
            `startDate=${startDate}&` +
            `people3To9=${people3To9}` + 
            `people10To17=${people10To17}` + 
            `adults=${peopleOver18}` + 
            `departurePlace=${departurePlace}`;
        navigate('/trips' + urlParams);
    }

    const handleAddParticipant = (actVal, newValue, setFunction) => {
        if(people3To9 + people10To17 + peopleOver18 + (newValue-actVal) <= maxParticipants)
            setFunction(newValue);
    }

    return (
        <div className="home">
            <div className="mt-5 fw-bold fs-3 mb-3 border-bottom pb-1 text-warning">
                Configure your dream trip
            </div>
            <Form className="row row-cols-1 row-cols-lg-5 border-bottom mb-3 pb-3" onSubmit={handleSubmit}>

                <div className="col mb-2 mb-lg-0">
                    <TripDestinationInput destination={destination} setDestination={setDestination}/>
                </div>
                
                <div className="col mb-2 mb-lg-0">
                    <TripDateInput date={startDate} setDate={setStartDate} />
                </div>

                <div className="col mb-2 mb-lg-0">
                    <TripDeparturePlaceInput departurePlace={departurePlace} setDeparturePlace={setDeparturePlace} />
                </div>

                <div className="col mb-2 mb-lg-0">
                <Button
                    variant="outline-secondary"
                    onClick={() => setPeopleCollapseOpen(!peopleCollapseOpen)}
                    aria-controls="participants-collapse"
                    aria-expanded={peopleCollapseOpen}
                    className="w-100 h-100"
                >
                    <FontAwesomeIcon icon={faPeopleGroup} className="fa-fw me-1" />
                    Participants ({Math.min(maxParticipants, people3To9+people10To17+peopleOver18)})
                </Button>
                </div>

                <div className="col mb-2 mb-lg-0">
                <Button type="submit" variant="primary" className="h-100 w-100">
                    <FontAwesomeIcon icon={faMagnifyingGlass} className="fw-fw me-1" />
                    Search
                </Button>
                </div>
                
                <div className="mt-2"></div>
                <TripPeopleCollapse 
                    open={peopleCollapseOpen}
                    people3To9={people3To9}
                    setPeople3To9={(value) => handleAddParticipant(people3To9, value, setPeople3To9)}
                    people10To17={people10To17}
                    setPeople10To17={(value) => handleAddParticipant(people10To17, value, setPeople10To17)}
                    peopleOver18={peopleOver18}
                    setPeopleOver18={(value) => handleAddParticipant(peopleOver18, value, setPeopleOver18)}
                />                
            </Form>

            <div className="fw-bold fs-3 mb-3 border-bottom pb-1 text-info">
                Examples of trips You could buy
            </div>
            <div className="mt-2">
                {
                    exampleTrips ? 
                    exampleTrips.map(trip => (
                        <TripsListElement trip={trip} key={trip.id} />
                    ))
                    :
                    <TripsListElementSkeleton />    
                }
            </div>
        </div>
    )
}
export default Home;
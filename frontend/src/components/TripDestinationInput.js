import FloatingLabel from 'react-bootstrap/FloatingLabel';
import InputGroup from 'react-bootstrap/InputGroup';
import Form from 'react-bootstrap/Form';
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faLocationDot } from "@fortawesome/free-solid-svg-icons";
import useFetch from "../hooks/useFetch";
import UrlBuilder from "./UrlBuilder";


const destinations = {
    destinations: [
        'Egipt',
        'Turcja',
        'Włochy',
        'Hiszpania'
    ]
}

export default function TripDeparturePlaceInput({destination, setDestination}) {
    let urlBuilder = new UrlBuilder();
    let {data, isPending, error} = useFetch(urlBuilder.build('REACT_APP_API_ROOT_URL', 'REACT_APP_API_TRIPS_DESTINATIONS_URL'));
    if(!isPending && data == null)
        data = destinations;

    return (
        <InputGroup>
            <InputGroup.Text>
                <FontAwesomeIcon icon={faLocationDot} className="fa-fw" />
            </InputGroup.Text>
            <FloatingLabel
                controlId="desetinationInput"
                label="Destination"
            >
                <Form.Select 
                    type="select" 
                    onChange={(e) => setDestination(e.target.value)} 
                    value={destination}
                    className={isPending ? 'disabled' : ''}
                    required
                >
                    <option value="all">Any</option>
                    {
                    isPending ?
                        null
                    :
                        data.destinations.map((dest) => (
                            <option value={dest}>{dest}</option>
                        ))
                    }
                </Form.Select>
            </FloatingLabel>
        </InputGroup>
    )
}
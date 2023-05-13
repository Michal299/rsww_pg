import MyReservationsListElement from "../components/MyReservationsListElement";


const MyReservations = () => {
    let reservations = sessionStorage.getItem('reservations');
    if(reservations != null)
        reservations = JSON.parse(reservations);

    return (
        !reservations ? 
        <div>You don't have any reservations</div>
        :
        <div className="my-reservations">
            {reservations.map((reservation) => (
                <MyReservationsListElement reservation={reservation} />
            ))}
        </div>
    )
}

export default MyReservations;
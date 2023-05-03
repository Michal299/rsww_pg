package pl.edu.pg.accommodation.reservation.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import pl.edu.pg.accommodation.room.entity.RoomEntity;

import java.time.LocalDateTime;

@Entity
@Table(name = "Reservations")
public class ReservationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "roomId", nullable = false)
    private RoomEntity room;
    private LocalDateTime reservationStart;
    private LocalDateTime reservationStop;
    private int numberOfPeople;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RoomEntity getRoom() {
        return room;
    }

    public void setRoom(RoomEntity room) {
        this.room = room;
    }

    public LocalDateTime getReservationStart() {
        return reservationStart;
    }

    public void setReservationStart(LocalDateTime reservationStart) {
        this.reservationStart = reservationStart;
    }

    public LocalDateTime getReservationStop() {
        return reservationStop;
    }

    public void setReservationStop(LocalDateTime reservationStop) {
        this.reservationStop = reservationStop;
    }

    public int getNumberOfPeople() {
        return numberOfPeople;
    }

    public void setNumberOfPeople(int numberOfPeople) {
        this.numberOfPeople = numberOfPeople;
    }

    @Override
    public String toString() {
        return "ReservationEntity{" +
                "id=" + id +
                ", room=" + room +
                ", reservationStart=" + reservationStart +
                ", reservationStop=" + reservationStop +
                ", numberOfPeople=" + numberOfPeople +
                '}';
    }
}

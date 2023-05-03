package pl.edu.pg.accommodation.reservation.repository;

import pl.edu.pg.accommodation.repository.CrudRepository;
import pl.edu.pg.accommodation.reservation.entity.ReservationEntity;

public interface ReservationRepository extends CrudRepository<ReservationEntity, Long> {
}

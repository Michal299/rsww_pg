package pl.edu.pg.accommodation.room.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.edu.pg.accommodation.room.entity.RoomEntity;
@Repository

public interface RoomRepository extends JpaRepository<RoomEntity, Long> {
}

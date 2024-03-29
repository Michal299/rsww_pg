package pl.edu.pg.accommodation.hotel.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import pl.edu.pg.accommodation.event.AddHotelEvent;
import pl.edu.pg.accommodation.event.AddRoomEvent;
import pl.edu.pg.accommodation.hotel.service.HotelService;

@Component
public class HotelUpdateListener {

    private final static Logger log = LoggerFactory.getLogger(HotelUpdateListener.class);
    private final HotelService hotelService;

    @Autowired
    public HotelUpdateListener(final HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @RabbitListener(queues = "#{autoDeleteQueue}")
    public void addHotelUpdate(Message<AddHotelEvent> addHotelEvent) {
        log.info("{}", addHotelEvent.getPayload());
        final var hotel = AddHotelEvent.dtoToEntityMapper().apply(addHotelEvent.getPayload());
        hotelService.addHotel(hotel);
    }

    @RabbitListener(queues =  "${spring.rabbitmq.queue.update.hotel.remove}")
    public void removeHotelUpdate() {

    }

    @RabbitListener(queues = "${spring.rabbitmq.queue.update.hotel.room.add}")
    public void addRoomUpdate(final AddRoomEvent addRoomEvent) {
        final var room = AddRoomEvent.dtoToEntityMapper().apply(addRoomEvent);
        final var hotelId = addRoomEvent.getHotelId();
        hotelService.addRoomInHotel(hotelId, room);
    }

    @RabbitListener(queues = "${spring.rabbitmq.queue.update.hotel.room.remove}")
    public void removeRoomUpdate() {

    }

    @RabbitListener(queues = "${spring.rabbitmq.queue.update.hotel.price.notify}")
    public void roomPriceUpdated(final AddRoomEvent updatedRoom) {
        final var room = AddRoomEvent.dtoToEntityMapper().apply(updatedRoom);
        hotelService.updateRoomPrice(room, updatedRoom.getHotelId());
    }
}

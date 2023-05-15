package pl.edu.pg.gateway.user;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.edu.pg.gateway.user.dto.TokenDto;
import pl.edu.pg.gateway.user.dto.UserDto;

@Service
class UserService {
    private final RabbitTemplate rabbitTemplate;
    private static final String AMQP_POST_TOKEN_PAIR_QUEUE = "PostTokenPair";

    @Autowired
    UserService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    ResponseEntity<Object> loginUser(UserDto userDto) {
        return new ResponseEntity<>(userDto.username() + " " + userDto.password(), HttpStatus.OK); // ???
    }

    ResponseEntity<String> refreshToken(TokenDto tokenDto) {
        return new ResponseEntity<>(tokenDto.token(), HttpStatus.OK);
    }

    ResponseEntity<String> verifyToken(TokenDto tokenDto) {
        return new ResponseEntity<>(tokenDto.token(), HttpStatus.OK);
    }
}

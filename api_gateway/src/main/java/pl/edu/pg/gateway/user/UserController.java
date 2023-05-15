package pl.edu.pg.gateway.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pg.gateway.user.dto.TokenDto;
import pl.edu.pg.gateway.user.dto.UserDto;

@RestController
@RequestMapping("/api/token")
class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    ResponseEntity<Object> loginUser(@RequestBody UserDto userDto) {
        return userService.loginUser(userDto);
    }

    @PostMapping("refresh")
    ResponseEntity<String> refreshToken(@RequestBody TokenDto tokenDto) {
        return userService.refreshToken(tokenDto);
    }

    @PostMapping("verify")
    ResponseEntity<String> verifyToken(@RequestBody TokenDto tokenDto) {
        return userService.verifyToken(tokenDto);
    }
}

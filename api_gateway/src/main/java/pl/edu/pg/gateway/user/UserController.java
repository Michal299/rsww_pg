package pl.edu.pg.gateway.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pg.gateway.user.dto.PostTokenPairResponse;
import pl.edu.pg.gateway.user.dto.PostTokenRefreshResponse;
import pl.edu.pg.gateway.user.dto.RefreshTokenDto;
import pl.edu.pg.gateway.user.dto.UserDto;

@RestController
@RequestMapping("/api/token")
class UserController {
    private final UserService userService;

    @Autowired
    UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    ResponseEntity<PostTokenPairResponse> loginUser(@RequestBody UserDto userDto) {
        return userService.loginUser(userDto);
    }

    @PostMapping("refresh")
    ResponseEntity<PostTokenRefreshResponse> refreshToken(@RequestBody RefreshTokenDto tokenDto) {
        return userService.refreshToken(tokenDto);
    }

}

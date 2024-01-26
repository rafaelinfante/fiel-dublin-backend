package ie.fieldublin.domain.user.controller;

import ie.fieldublin.domain.user.dto.UserRequestDto;
import ie.fieldublin.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Tag(name = "Users", description = "User management endpoints")
public class UserController {
    private final UserService userService;

    @GetMapping(value = "{id}")
    @Operation(summary = "Get specific user")
    public ResponseEntity<UserRequestDto> getUser(@PathVariable Integer id) {
        UserRequestDto userDTO = userService.getUser(id);
        if (userDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping()
    @Operation(summary = "Get all users")
    public ResponseEntity<List<UserRequestDto>> getAllUser() {
        return ResponseEntity.ok(userService.findAll());
    }
}

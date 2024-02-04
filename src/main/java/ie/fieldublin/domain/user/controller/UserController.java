package ie.fieldublin.domain.user.controller;

import ie.fieldublin.domain.user.dto.UserResponseDto;
import ie.fieldublin.domain.user.service.UserService;
import ie.fieldublin.security.MyUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Tag(name = "Users", description = "User management endpoints")
public class UserController {
    private final UserService userService;

    @GetMapping(value = "{id}")
    @Operation(summary = "Get specific user")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable Integer id) {
        UserResponseDto userDTO = userService.getUser(id);
        if (userDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping()
    @Operation(summary = "Get all users")
    public ResponseEntity<List<UserResponseDto>> getAllUser() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/current")
    @Operation(summary = "Get current user info")
    public ResponseEntity<UserResponseDto> getCurrentUser(@AuthenticationPrincipal MyUserDetails userDetails) {
        UserResponseDto userDTO = userService.getUserByUsername(userDetails.getUsername());
        if (userDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userDTO);
    }
}

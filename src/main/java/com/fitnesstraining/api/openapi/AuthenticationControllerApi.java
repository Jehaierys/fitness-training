package com.fitnesstraining.api.openapi;

import com.fitnesstraining.api.handler.ErrorResponse;
import com.fitnesstraining.domain.dto.request.UsernamePasswordAuthenticationRequest;
import com.fitnesstraining.domain.dto.response.JwtAuthenticationResponse;
import com.fitnesstraining.domain.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.fitnesstraining.utils.Paths.BASE_AUTHENTICATION_CONTROLLER_URL;

@RequestMapping(BASE_AUTHENTICATION_CONTROLLER_URL)
@Tag(
        name = "Authentication",
        description = "User access control related operations"
)
public interface AuthenticationControllerApi {



    @Operation(
            summary = "Change password",
            description = "Updates password for the authenticated user"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Password changed successfully"),
            @ApiResponse(
                    responseCode = "400",
                    description = "Invalid request data",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @PatchMapping("/password")
    void changePassword(
            @Parameter(hidden = true)
            @AuthenticationPrincipal User user,
            @RequestParam String newPassword
    );



    @Operation(
            summary = "Authenticate user",
            description = "Authenticates user with username and password and returns JWT access token"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Authentication successful",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = JwtAuthenticationResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "Invalid username or password"
            )
    })
    @PostMapping()
    ResponseEntity<JwtAuthenticationResponse> login(
            @RequestBody UsernamePasswordAuthenticationRequest dto,
            HttpServletRequest servletRequest
    );



    @Operation(
            summary = "Set user active status",
            description = "Toggles the active/inactive status of a user profile."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status successfully updated"),
            @ApiResponse(
                    responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @PatchMapping("/{id}")
    ResponseEntity<Void> setActive(
            @PathVariable Long id,
            @RequestParam(required = true) Boolean active,
            @AuthenticationPrincipal User principal
    );



    @Operation(
            summary = "Set user active status",
            description = "Toggles the active/inactive status of a user profile."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status successfully updated"),
            @ApiResponse(
                    responseCode = "401", description = "Unauthorized",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(
                    responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))
            )
    })
    @PatchMapping
    ResponseEntity<Void> setActive(
            @RequestParam(required = true) String username,
            @RequestParam(required = true) Boolean active,
            @AuthenticationPrincipal User principal
    );


    @Operation(
            summary = "Logout",
            description = "Logs out the current user by deleting the JWT authentication cookie."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "204",
                    description = "Successfully logged out"
            )
    })
    @DeleteMapping
    public ResponseEntity<Void> logout();
}

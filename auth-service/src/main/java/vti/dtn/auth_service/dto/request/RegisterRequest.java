package vti.dtn.auth_service.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RegisterRequest {
    @NotBlank(message = "Username must to the blank")
    private String username;

    private String firstName;
    private String lastName;

    @Email(message = "Malformed email")
    @NotBlank(message = "Email must to the blank")
    private String email;

    @NotBlank(message = "Password must to the blank")
    private String password;

    @NotBlank(message = "Role must to the blank")
    @Pattern(regexp = "ADMIN|MANAGER|USER", message = "The role must be ADMIN, MANAGER OR USER")
    private String role;
}

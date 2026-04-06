package SFWE405.Project.dto;

import lombok.Data;

/**
 * @author Brandon Sisco
 *
 * DTO representing a login request.
 *
 * This object is used to receive username and password
 * from the client in JSON format.
 */
@Data
public class LoginRequest {
    private String userName;
    private String password;
}

package server.mail.api.dto;

import server.mail.service.dto.LoginInf;

public record LoginRequest(

        String id,
        String password

) {
    public LoginInf to() {
        return new LoginInf(id, password);
    }
}

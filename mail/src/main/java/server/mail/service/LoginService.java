package server.mail.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import server.mail.service.dto.LoginInf;

@Service
@RequiredArgsConstructor
public class LoginService {

    public void login(LoginInf loginInf) {
        if ("test".equals(loginInf.id()) && "1234".equals(loginInf.password())) {
            return;
        }
        throw new RuntimeException("아이디나 비밀번호가 잘못 되었습니다.");
    }
}

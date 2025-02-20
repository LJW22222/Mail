package server.mail.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import server.mail.Repository.UserRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Map<String, List<String>> getUserList() {
        Map<String, List<String>> userList = new HashMap<>();


        userList.put("users", null);
        return userList;
    }

}

package server.mail.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import server.mail.persistence.admin.AdminEntity;
import server.mail.persistence.admin.repository.AdminRepository;

@Service
@RequiredArgsConstructor
public class UserDetailsManager implements UserDetailsService {

    private final AdminRepository repo;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        AdminEntity adminEntity = repo.findByUserId(userId).orElseThrow(() -> new UsernameNotFoundException("존재 하지 않는 관리자"));
        return User.withUsername(adminEntity.getUserId())
                .password(adminEntity.getPassword())
                .roles("ADMIN")
                .build();
    }
}

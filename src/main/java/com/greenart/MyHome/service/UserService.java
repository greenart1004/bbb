package com.greenart.MyHome.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.greenart.MyHome.model.Role;
import com.greenart.MyHome.model.User;
import com.greenart.MyHome.repository.UserRepository;



@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    
    @Autowired
    private PasswordEncoder passwordEncoder;

    
       
    public User save(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        
        user.setPassword(encodedPassword);
        user.setEnabled(true);      // 기본적으로 회원가입을하면 enable로 활성화시킴
        
        Role role = new Role();
        role.setId(1l);              // 권한을 기본적으로 role 권한에서 id=1번인 user로 하드코딩 설정함
        user.getRoles().add(role);
        
        return userRepository.save(user);
    }

}
package com.example.officebuilding.security.service;

import com.example.officebuilding.security.entities.User;
import com.example.officebuilding.service.IGeneralService;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IUserService extends IGeneralService<User>, UserDetailsService {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    List<Map<String, Object>> findAllUser();
    int update(Integer userId,User user);
    int lockUser(Integer userId);
    List<Map<String, Object>> findAllbyUserStatus(Integer userStatus);
}

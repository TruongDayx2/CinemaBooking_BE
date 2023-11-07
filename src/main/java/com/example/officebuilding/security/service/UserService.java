package com.example.officebuilding.security.service;

import com.example.officebuilding.security.UserPrinciple;
import com.example.officebuilding.security.entities.User;
import com.example.officebuilding.security.repo.IUserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {
    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    public List<Map<String,Object>> findAllUser(){
        List<User> users = findAll();
        List<Map<String, Object>> userMaps = users.stream()
                .map(user -> {
                    Map<String, Object> userMap = new HashMap<>();
                    userMap.put("id", user.getId());
                    userMap.put("username", user.getUsername());
                    userMap.put("email", user.getEmail());
                    userMap.put("fullname",user.getFullname());
                    userMap.put("status",user.getUserStatus());
                    // Thêm các trường khác nếu cần thiết
                    return userMap;
                })
                .collect(Collectors.toList());

        return userMaps;
    }
    @Override
    public List<Map<String, Object>> findAllbyUserStatus(Integer userStatus){
        List<User> users = userRepository.findAllByUserStatus(userStatus);
        List<Map<String, Object>> userResponses = users.stream()
                .map(user -> {
                    Map<String, Object> response = new HashMap<>();
                    response.put("id", user.getId());
                    response.put("username", user.getUsername());
                    response.put("email", user.getEmail());
                    response.put("fullname",user.getFullname());
                    // Tùy chỉnh các trường nếu cần thiết
                    return response;
                })
                .collect(Collectors.toList());

        return userResponses;
    }
    @Override
    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }

    @Override
    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
    public int update(Integer userId, User updatedUser){
        User existingUser = userRepository.findById(userId).orElse(null);
        if (existingUser == null) {
            throw new UsernameNotFoundException("Người dùng không tồn tại với id: " + userId);
        }
        // Cập nhật thông tin của người dùng
        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setFullname(updatedUser.getFullname());
        // Cập nhật các trường khác nếu cần thiết
        userRepository.save(existingUser);
        // Lưu người dùng đã được cập nhật vào cơ sở dữ liệu và trả về
        return 1;
    }

    public int lockUser(Integer userId){
        User existingUser = userRepository.findById(userId).orElse(null);
        if (existingUser == null) {
            throw new UsernameNotFoundException("Người dùng không tồn tại với id: " + userId);
        }
        // Cập nhật thông tin của người dùng
        existingUser.setUserStatus(0);
        // Cập nhật các trường khác nếu cần thiết
        userRepository.save(existingUser);
        // Lưu người dùng đã được cập nhật vào cơ sở dữ liệu và trả về
        return 1;
    }
    @Override
    public void remove(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (!userOptional.isPresent()) {
            throw new UsernameNotFoundException(username);
        }
        return UserPrinciple.build(userOptional.get());
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}

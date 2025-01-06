package com.calorator.service;

import com.calorator.dto.UserDTO;

import java.util.List;

public interface UserService {
    void save(UserDTO userDTO);
    UserDTO findById(int id);
    UserDTO findByName(String name);
    UserDTO findByEmail(String email);

    void update(UserDTO userDTO);
    List<UserDTO> findAll();
    void delete(int id);
    boolean validateUsername(String username);
    boolean validateEmail(String email);
    boolean validatePassword(String password);
    boolean isEmailValid(String email);
    boolean isPasswordValid(String password);
    boolean isUsernameValid(String username);
    boolean authenticate(String username, String password);

}

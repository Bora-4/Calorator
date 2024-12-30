package com.calorator.service.impl;

import com.calorator.dto.UserDTO;
import com.calorator.entity.UserEntity;
import com.calorator.mapper.UserMapper;
import com.calorator.repository.UserRepository;
import com.calorator.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserRepository userRepository;

    public boolean authenticate(String username, String password) {
        // Replace this with actual authentication logic (e.g., database check)
        return "admin".equals(username) && "password".equals(password);
    }

    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public void save(UserDTO userDTO) {
        UserEntity userEntity = UserMapper.toEntity(userDTO);
        userRepository.save(userEntity);
    }

    @Override
    public UserDTO findById(Long id) {
        UserEntity userEntity = this.userRepository.findById(id);
        if (userEntity != null){
            return UserMapper.toDTO(userEntity);
        }
        throw new EntityNotFoundException("User with id " + id + " was not found.");
    }

    @Override
    public UserDTO findByName(String name) {
        UserEntity userEntity = this.userRepository.findByName(name);
        if (userEntity != null){
            return UserMapper.toDTO(userEntity);
        }
        throw new EntityNotFoundException("User with name " + name + "was not found.");
    }

    @Override
    public void update(UserDTO userDTO) {
        UserEntity existingUser = userRepository.findById(userDTO.getId());
        if (existingUser != null) {
            UserEntity updatedUser = UserMapper.toEntity(userDTO);
            userRepository.update(updatedUser);
        } else {
            throw new EntityNotFoundException("User with id " + userDTO.getId() + " was not found.");
        }
    }

    @Override
    public List<UserDTO> findAll() {
        return userRepository.findAll().stream()
                .map(UserMapper::toDTO).toList();
    }

    @Override
    public void delete(Long id) {
        UserEntity userEntity = userRepository.findById(id);
        if (userEntity == null) {
            throw new EntityNotFoundException("User with id " + id + "was not found.");
        }
        userRepository.delete(id);
    }

    @Override
    public boolean validateEmail(String email) {
        return userRepository.findByEmail(email).isEmpty();
    }

    @Override
    public boolean validatePassword(String password) {
        String passwordPattern = "^(?=.*[A-Z])(?=.*[0-9]).{8,}$";
        return password.matches(passwordPattern);
    }

    @Override
    public boolean validateUsername(String username) {
        return userRepository.findByUserName(username).isEmpty();
    }
}

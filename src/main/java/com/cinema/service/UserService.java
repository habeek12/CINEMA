package com.cinema.service;

import org.springframework.stereotype.Service;

import com.cinema.model.User;
import com.cinema.repository.UserRepository;

@Service
public class UserService {

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User findByUsername(String username) {
    return userRepository.findByUsername(username).orElse(null);
  }

  public User findById(Integer id) {
    return userRepository.findById(id).orElse(null);
  }
}

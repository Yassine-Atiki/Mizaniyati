package com.mizaniyati.service.interfaces;

import com.mizaniyati.entity.User;

public interface UserService {
    User findByEmail(String email);
    User findById(Long userId);
}
package com.inventrack.inventrackcore.service;

import com.inventrack.inventrackcore.dto.AuthRequest;
import com.inventrack.inventrackcore.dto.AuthResponse;
import com.inventrack.inventrackcore.dto.UserRegisterDto;

public interface UserService {
    Long register(UserRegisterDto dto);
    AuthResponse authenticate(AuthRequest req);
}
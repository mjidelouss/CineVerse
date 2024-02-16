package com.cine.verse.service;

import com.cine.verse.Dto.request.AuthenticationRequest;
import com.cine.verse.Dto.request.RegisterRequest;
import com.cine.verse.Dto.response.AuthenticationResponse;

public interface AuthenticationService {
    AuthenticationResponse register(RegisterRequest request);
    AuthenticationResponse authenticate(AuthenticationRequest request);
}
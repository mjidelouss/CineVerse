package com.cine.verse.service;

import com.cine.verse.Dto.request.RefreshTokenRequest;
import com.cine.verse.Dto.response.RefreshTokenResponse;
import com.cine.verse.domain.RefreshToken;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

public interface RefreshTokenService {

    RefreshToken createRefreshToken(Long userId);
    RefreshToken verifyExpiration(RefreshToken token);
    Optional<RefreshToken> findByToken(String token);
    RefreshTokenResponse generateNewToken(RefreshTokenRequest request);
    String getRefreshTokenFromLocalStorage(HttpServletRequest request);
    void deleteByToken(String token);
}
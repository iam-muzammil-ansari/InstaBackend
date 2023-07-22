package com.geekster.InstaBackend.service;

import com.geekster.InstaBackend.model.AuthenticationToken;
import com.geekster.InstaBackend.repository.IAuthenticationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationAdminService {

    @Autowired
    IAuthenticationRepo authenticationRepo;

    public boolean authenticateAdmin(String email, String authTokenValue)
    {
        AuthenticationToken authToken = authenticationRepo.findFirstByTokenValue(authTokenValue);

        if(authToken == null)
        {
            return false;
        }


        String tokenConnectedEmail = authToken.getAdmin().getAdminEmail();
        return tokenConnectedEmail.equals(email);
    }
}

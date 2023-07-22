package com.geekster.InstaBackend.service;


import com.geekster.InstaBackend.model.Admin;
import com.geekster.InstaBackend.model.AuthenticationToken;
import com.geekster.InstaBackend.model.Post;
import com.geekster.InstaBackend.model.dto.SignInInput;
import com.geekster.InstaBackend.model.dto.SignUpOutput;
import com.geekster.InstaBackend.repository.IAdminRepo;
import com.geekster.InstaBackend.repository.IAuthenticationRepo;
import com.geekster.InstaBackend.repository.IPostRepo;
import com.geekster.InstaBackend.service.emailUtility.EmailHandler;
import com.geekster.InstaBackend.service.hashingUtility.PasswordEncrypter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    IAdminRepo adminRepo;

    @Autowired
    IAuthenticationRepo authenticationRepo;

    @Autowired
    IPostRepo postRepo;


    public SignUpOutput signUpAdmin(Admin admin) {

       boolean signUpStatus = true;
       String signUpStatusMessage = null;

       String newEmail = admin.getAdminEmail();

       if(newEmail == null || !newEmail.toLowerCase().endsWith("@instagram.com")) {
           signUpStatusMessage = "Invalid email format. Email must end with @instagram.com";
           signUpStatus = false;
           return new SignUpOutput(signUpStatus,signUpStatusMessage);
       }

       Admin existingAdmin = adminRepo.findFirstByAdminEmail(newEmail);

       if (existingAdmin != null) {
           signUpStatusMessage = "Email already registered";
           signUpStatus = false;
           return  new SignUpOutput(signUpStatus,signUpStatusMessage);
       }

       try {
           String encryptedPassword = PasswordEncrypter.encryptPassword(admin.getAdminPassword());

           admin.setAdminPassword(encryptedPassword);
           adminRepo.save(admin);
           return new SignUpOutput(signUpStatus, "Admin registered successfully");
       } catch (Exception e) {
           signUpStatusMessage = "Internal error occurred during sign up";
           signUpStatus = false;

           return new SignUpOutput(signUpStatus,signUpStatusMessage);
       }
    }

    public String signInAdmin(SignInInput signInInput) {
        String signInStatusMessage = null;

        String signInEmail = signInInput.getEmail();

        if(signInEmail == null)
        {
            signInStatusMessage = "Invalid email";
            return signInStatusMessage;

        }

        //check if this patient email already exists ??
        Admin existingAdmin = adminRepo.findFirstByAdminEmail(signInEmail);

        if(existingAdmin == null)
        {
            signInStatusMessage = "Email not registered!!!";
            return signInStatusMessage;

        }

        //match passwords :

        //hash the password: encrypt the password
        try {
            String encryptedPassword = PasswordEncrypter.encryptPassword(signInInput.getPassword());
            if(existingAdmin.getAdminPassword().equals(encryptedPassword))
            {
                //session should be created since password matched and user id is valid
                AuthenticationToken authToken  = new AuthenticationToken(existingAdmin);
                authenticationRepo.save(authToken);

                EmailHandler.sendEmail(signInEmail,"email testing",authToken.getTokenValue());
                return "Token sent to your email";
            }
            else {
                signInStatusMessage = "Invalid credentials!!!";
                return signInStatusMessage;
            }
        }
        catch(Exception e)
        {
            signInStatusMessage = "Internal error occurred during sign in";
            return signInStatusMessage;
        }
    }

    public String signOutAdmin(String email) {
        Admin admin = adminRepo.findFirstByAdminEmail(email);
        authenticationRepo.delete(authenticationRepo.findFirstByAdmin(admin));
        return "admin Signed out successfully";
    }
}
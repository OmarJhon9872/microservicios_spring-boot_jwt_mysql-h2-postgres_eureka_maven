package com.jonOmar.microservice3gateway.security.extrasNotUsed;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class CryptPasswordBcrypt {

    public static String bCryptPasswordEncoder(String password){
        return new BCryptPasswordEncoder().encode(password);
    }

    public static void main(String[] args) {
        System.out.println("Password: "+ bCryptPasswordEncoder("12345678910"));
    }

}

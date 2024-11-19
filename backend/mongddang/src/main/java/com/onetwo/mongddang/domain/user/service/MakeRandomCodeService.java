package com.onetwo.mongddang.domain.user.service;

import com.onetwo.mongddang.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@RequiredArgsConstructor
@Service
public class MakeRandomCodeService {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int LENGTH = 20; // 원하는 길이 설정
    private static final SecureRandom random = new SecureRandom();
    private  final UserRepository userRepository;

    public String generateCodeString() {
        StringBuilder sb = new StringBuilder(LENGTH);
        for (int i = 0; i < LENGTH; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }

    public String makeCodeAndCheckUnique() {
        String randomString;
        do {
            randomString = generateCodeString();
        } while (userRepository.existsByInvitationCode(randomString));
        return randomString;
    }

}

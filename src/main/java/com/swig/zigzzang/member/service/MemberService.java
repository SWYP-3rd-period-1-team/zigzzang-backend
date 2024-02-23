package com.swig.zigzzang.member.service;

import com.swig.zigzzang.email.dto.EmailResponseDto;
import com.swig.zigzzang.email.service.EmailService;
import com.swig.zigzzang.global.exception.HttpExceptionCode;
import com.swig.zigzzang.global.redis.RedisService;
import com.swig.zigzzang.member.domain.Member;
import com.swig.zigzzang.member.dto.MemberJoinRequest;
import com.swig.zigzzang.member.exception.MemberExistException;
import com.swig.zigzzang.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Duration;
import java.util.Optional;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Value("${spring.mail.auth-code-expiration-millis}")
    private long authCodeExpirationMillis;
    private static final String AUTH_CODE_PREFIX = "AuthCode ";
    private final EmailService mailService;
    private final RedisService redisService;



    public Member save(MemberJoinRequest memberJoinRequest) {
        Member member = memberJoinRequest.toEntity();

        member.setPassword(bCryptPasswordEncoder.encode(member.getPassword()));

        Member savedmember = memberRepository.save(member);


        return savedmember;
    }
    public void sendCodeToEmail(String toEmail) {
        this.checkDuplicatedEmail(toEmail);
        String title = "직짱건강 이메일 인증 번호";
        String authCode = this.createCode();
        mailService.sendEmail(toEmail, title, authCode);
        // 이메일 인증 요청 시 인증 번호 Redis에 저장 ( key = "AuthCode " + Email / value = AuthCode )
        redisService.setValues(AUTH_CODE_PREFIX + toEmail,
                authCode, Duration.ofMillis(this.authCodeExpirationMillis));
    }

    private void checkDuplicatedEmail(String email) {
        Optional<Member> member = memberRepository.findByEmail(email);
        if (member.isPresent()) {
            log.debug("MemberServiceImpl.checkDuplicatedEmail exception occur email: {}", email);
            throw new MemberExistException(HttpExceptionCode.MEMBER_EXISTS);
        }
    }

    private String createCode() {
        int lenth = 6;
        try {
            Random random = SecureRandom.getInstanceStrong();
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < lenth; i++) {
                builder.append(random.nextInt(10));
            }
            return builder.toString();
        } catch (NoSuchAlgorithmException e) {
            log.debug("MemberService.createCode() exception occur");
            throw new MemberExistException(HttpExceptionCode.MEMBER_EXISTS);
        }
    }

    public EmailResponseDto verifiedCode(String email, String authCode) {
        this.checkDuplicatedEmail(email);
        String redisAuthCode = redisService.getValues(AUTH_CODE_PREFIX + email);
        boolean authResult = redisService.checkExistsValue(redisAuthCode) && redisAuthCode.equals(authCode);

        return EmailResponseDto.of(authResult);
    }

}

package com.example.demo.service.impl;

import com.example.demo.entity.Member;
import com.example.demo.enums.UserRoleEnums;
import com.google.firebase.auth.FirebaseToken;
import com.example.demo.entity.Account;
import com.example.demo.entity.request.LoginGoogleRequest;
import com.example.demo.entity.request.LoginRequest;
import com.example.demo.entity.response.LoginResponse;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.MemberRepository;
import com.example.demo.service.LoginService;
import com.example.demo.service.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.example.demo.security.JwtTokenProvider.*;
import static com.example.demo.security.JwtTokenProvider.verifyFirebaseToken;
import static com.example.demo.utils.ValidationUtils.ensureAccountActive;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final MemberRepository memberRepository;

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        if (loginRequest == null || loginRequest.getIdentifier() == null || loginRequest.getPassword() == null) {
            throw new IllegalArgumentException("Thiếu thông tin đăng nhập");
        }

        Account account = accountRepository.findByUsernameOrEmailOrPhoneNumber(loginRequest.getIdentifier())
                .orElseThrow(() -> new UsernameNotFoundException("Tài khoản không tồn tại"));

        if (!account.isStatus()) throw new IllegalArgumentException("Tài khoản đã bị khóa");
        if (!passwordEncoder.matches(loginRequest.getPassword(), account.getPassword())) throw new IllegalArgumentException("Sai mật khầu");
//        if (!account.isEmailVerified()) throw new IllegalIdentifierException("Vui lòng xác minh email");

        String token = tokenService.generateToken(account);

        return LoginResponse.builder()
                .accountId(account.getAccountId())
                .email(account.getEmail())
                .fullName(account.getFullName())
                .phoneNumber(account.getPhoneNumber())
                .accountRole(account.getAccountRole())
                .status(account.isStatus())
                .token(token)
                .build();
    }

    @Override
    public LoginResponse loginGoogle(LoginGoogleRequest loginGoogleRequest) {
        validateRequest(loginGoogleRequest);

        FirebaseToken firebaseToken = verifyFirebaseToken(loginGoogleRequest.getToken());
        String email = extractEmail(firebaseToken);

        Account account = accountRepository.findByEmail(email)
                .orElseGet(() -> registerNewAccount(firebaseToken));

        ensureAccountActive(account);

        Member member = memberRepository.findByAccount_AccountId(account.getAccountId());
        String jwt = tokenService.generateToken(account);

        return buildLoginResponse(account, member, jwt);

    }

    private Account registerNewAccount(FirebaseToken token) {
        log.debug("Creating new account for email: {}", token.getEmail());

        Member member = new Member();
        Account account = Account.builder()
                .email(token.getEmail())
                .fullName(token.getName())
                .username(token.getEmail())
                .avatar(token.getPicture())
                .emailVerified(true)
                .accountRole(UserRoleEnums.MEMBER)
                .status(true)
                .build();

        member.setAccount(account);
        memberRepository.save(member);
        return account;
    }

    private LoginResponse buildLoginResponse(Account acc, Member mem, String jwt) {
        return LoginResponse.builder()
                .accountId(acc.getAccountId())
                .memberId(mem != null ? mem.getMemberId() : null)
                .email(acc.getEmail())
                .fullName(acc.getFullName())
                .phoneNumber(acc.getPhoneNumber())
                .avatar(acc.getAvatar())
                .accountRole(acc.getAccountRole())
                .status(acc.isStatus())
                .token(jwt)
                .build();
    }

    private void validateRequest(LoginGoogleRequest loginGoogleRequest) {
        if (loginGoogleRequest == null || loginGoogleRequest.getToken() == null || loginGoogleRequest.getToken().isEmpty()) {
            throw new IllegalArgumentException("Token không được để trống");
        }
    }
}

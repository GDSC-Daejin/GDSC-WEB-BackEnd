package Gdsc.web.controller;

import Gdsc.web.config.jwt.JwtProperties;
import Gdsc.web.config.oauth.provider.GoogleUser;
import Gdsc.web.config.oauth.provider.OAuthUserInfo;
import Gdsc.web.domain.Member;
import Gdsc.web.model.RoleType;
import Gdsc.web.repository.MemberRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
public class JwtCreateController {
	
	private final MemberRepository memberRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@PostMapping("/oauth/jwt/google")
	public String jwtCreate(@RequestBody Map<String, Object> data) {
		System.out.println("jwtCreate 실행됨");
		System.out.println(data.get("profileObj"));
		OAuthUserInfo googleUser =
				new GoogleUser((Map<String, Object>)data.get("profileObj"));
		
		Member memberEntity =
				memberRepository.findByUsername(googleUser.getProvider()+"_"+googleUser.getProviderId());
		log.info("Oauth login : " + googleUser.getEmail());
		log.info("Oauth login : " + googleUser.getName());
		if(memberEntity == null) {
			Member memberRequest = Member.builder()
					.username(googleUser.getProvider()+"_"+googleUser.getProviderId())
					.password(bCryptPasswordEncoder.encode("겟인데어"))
					.email(googleUser.getEmail())
					.provider(googleUser.getProvider())
					.providerId(googleUser.getProviderId())
					.role(RoleType.GUEST)
					.build();

			memberEntity = memberRepository.save(memberRequest);
			log.info("Oauth Join : " + googleUser.getEmail());
		}
		
		String jwtToken = JWT.create()
				.withSubject(memberEntity.getUsername())
				.withExpiresAt(new Date(System.currentTimeMillis()+JwtProperties.EXPIRATION_TIME))
				.withClaim("id", memberEntity.getId())
				.withClaim("username", memberEntity.getUsername())
				.sign(Algorithm.HMAC512(JwtProperties.SECRET));
		
		return jwtToken;
	}
	
}

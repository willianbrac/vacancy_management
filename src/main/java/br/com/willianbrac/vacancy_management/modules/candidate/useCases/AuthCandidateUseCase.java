package br.com.willianbrac.vacancy_management.modules.candidate.useCases;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;

import javax.security.sasl.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.willianbrac.vacancy_management.modules.candidate.CandidateRepository;
import br.com.willianbrac.vacancy_management.modules.candidate.dto.AuthCandidateRequestDTO;
import br.com.willianbrac.vacancy_management.modules.candidate.dto.AuthCandidateResponseDTO;

@Service
public class AuthCandidateUseCase {

  @Value("${security.token.secret.candidate}")
  private String secretKey;

  @Autowired
  private CandidateRepository candidateRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public AuthCandidateResponseDTO execute(AuthCandidateRequestDTO authCandidateRequestDTO)
      throws AuthenticationException {

    // Verifica se o candidato já existe
    var candidate = this.candidateRepository.findByUsername(authCandidateRequestDTO.username())
        .orElseThrow(() -> {
          throw new UsernameNotFoundException("Username or password incorrect");
        });

    // Verifica se as senha corresponde
    var passwordMatches = this.passwordEncoder
        .matches(authCandidateRequestDTO.password(), candidate.getPassword());

    if (!passwordMatches)
      throw new AuthenticationException();

    // Monta o token
    Algorithm algorithm = Algorithm.HMAC256(secretKey);

    var expiresIn = Instant.now().plus(Duration.ofMinutes(10));

    var token = JWT.create()
        .withIssuer("javagas") // adiciona o emissor do token
        .withSubject(candidate.getId().toString()) // adiciona o id do usuário para recuperar depois
        .withClaim("roles", Arrays.asList("candidate")) // adiciona as roles do usuário
        .withExpiresAt(expiresIn) // define o tempo de expiração do token
        .sign(algorithm);

    var AuthCandidateResponse = AuthCandidateResponseDTO.builder() // monta o response com o builder
        .access_token(token)
        .expires_in(expiresIn.toEpochMilli())
        .build();

    return AuthCandidateResponse;
  }
}

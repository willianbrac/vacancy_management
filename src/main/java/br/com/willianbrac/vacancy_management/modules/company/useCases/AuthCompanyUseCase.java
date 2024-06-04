package br.com.willianbrac.vacancy_management.modules.company.useCases;

import java.time.Duration;
import java.time.Instant;

import javax.naming.AuthenticationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import br.com.willianbrac.vacancy_management.modules.company.dto.AuthCompanyDTO;
import br.com.willianbrac.vacancy_management.modules.company.repositories.CompanyRepository;

@Service
public class AuthCompanyUseCase {

  @Value("${security.token.secret}")
  private String secretKey;

  @Autowired
  private CompanyRepository companyRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  // Recebe o username e password
  public String execute(AuthCompanyDTO authCompanyDTO) throws AuthenticationException {
    // Verifica se existe uma company com o username
    var company = this.companyRepository.findByUsername(authCompanyDTO.getUsername()).orElseThrow(() -> {
      // Se não existe lança uma exceção
      throw new UsernameNotFoundException("Username or password incorrect");
    });

    // Compara as senhas
    var passwordMatches = this.passwordEncoder.matches(authCompanyDTO.getPassword(), company.getPassword());

    // Lança uma exceção se não forem iguais
    if (!passwordMatches) {
      throw new AuthenticationException();
    }

    Algorithm algorithm = Algorithm.HMAC256(secretKey);

    // Gera o JWT
    var token = JWT.create().withIssuer("javagas")
        .withExpiresAt(Instant.now().plus(Duration.ofHours(7)))
        .withSubject(company.getId().toString()).sign(algorithm);

    return token;
  }
}
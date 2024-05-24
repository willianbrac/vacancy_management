package br.com.willianbrac.vacancy_management.modules.candidate;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Entity(name = "candidate")
public class CandidateEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private String name;

  @NotBlank
  @Pattern(regexp = "\\S+", message = "username cannot have spaces")
  private String username;

  @Email(message = "invalid e-mail address")
  private String email;

  @Length(min = 8, max = 45, message = "password must be between 8 and 45 characters")
  private String password;

  private String description;

  private String curriculum;

  @CreationTimestamp
  private LocalDateTime createdAt;
}

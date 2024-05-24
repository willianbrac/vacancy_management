package br.com.willianbrac.vacancy_management.modules.company.entities;

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

@Entity(name = "company")
@Data
public class CompanyEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @NotBlank
  @Pattern(regexp = "\\S+", message = "username cannot have spaces")
  private String username;

  @Email(message = "invalid e-mail address")
  private String email;

  @Length(min = 10, max = 45, message = "password must be between 8 and 45 characters")
  private String password;

  private String website;

  private String name;

  private String description;

  @CreationTimestamp
  private LocalDateTime createdAt;
}
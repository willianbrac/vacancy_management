package br.com.willianbrac.vacancy_management.exceptions;

public class UserFoundException extends RuntimeException {
  public UserFoundException() {
    super("Username or e-mail alread exists.");
  }
}

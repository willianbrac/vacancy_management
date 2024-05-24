package br.com.willianbrac.vacancy_management.modules.company.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.willianbrac.vacancy_management.modules.company.entities.JobEntity;

public interface JobRepository extends JpaRepository<JobEntity, UUID> {}
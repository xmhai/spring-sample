package com.cl.smart.coherence.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cl.smart.coherence.application.domain.Application;

@Repository
public interface ApplicationRepository  extends JpaRepository<Application, Long> {

}

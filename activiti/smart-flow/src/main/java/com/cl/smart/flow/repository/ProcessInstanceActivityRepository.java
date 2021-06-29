package com.cl.smart.flow.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cl.smart.flow.domain.ProcessInstanceActivity;

@Repository
public interface ProcessInstanceActivityRepository extends JpaRepository<ProcessInstanceActivity, Long> {

	List<ProcessInstanceActivity> findByProcessInstanceId(String processInstanceId);
}

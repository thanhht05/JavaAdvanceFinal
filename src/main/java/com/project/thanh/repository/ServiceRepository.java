package com.project.thanh.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.thanh.domain.Service;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {
    Service findById(long id);
}

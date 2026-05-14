package com.project.thanh.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.thanh.repository.ServiceRepository;

@Service
public class ServiceService {
    @Autowired
    private ServiceRepository serviceRepository;

    public List<com.project.thanh.domain.Service> getAllServices() {
        return this.serviceRepository.findAll();
    }

    public void saveService(com.project.thanh.domain.Service service) {
        this.serviceRepository.save(service);
    }

    public com.project.thanh.domain.Service getServiceById(long id) {
        return this.serviceRepository.findById(id);
    }

}

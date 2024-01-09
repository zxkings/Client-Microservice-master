package com.example.clientservice.repository;

import com.example.clientservice.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long>
{
    Client findClientByCin(String cin);
    Client findClientByPhoneNumber(String phoneNumber);
    void deleteClientByCin(String cin);
}

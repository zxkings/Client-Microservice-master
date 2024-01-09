package com.example.clientservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientRequest
{
    private String cin;
    private String nom;
    private String prenom;
    private Date dateNaissance;
    private String phoneNumber;
    private String email;
    private String salesforceId;
    private String origine;
}

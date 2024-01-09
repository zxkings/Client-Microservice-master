package com.example.clientservice.dto;

import com.example.clientservice.enums.StatusTransfere;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Transfere
{
    private Long id;
    private String reference;
    private double montant;
    private Date dateExpiration;
    private String referenceAgent;
    private String referenceClientDonneur;
    private String referenceClientBeneficiaire;
    private StatusTransfere status;
    private String codePinTransfere;
}

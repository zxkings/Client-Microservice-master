package com.example.clientservice.service;

import com.example.clientservice.dto.ClientRequest;
import com.example.clientservice.dto.Transfere;
import com.example.clientservice.model.Client;
import com.example.clientservice.repository.ClientRepository;
import com.example.clientservice.salesforce.AuthenticationResponse;
import com.example.clientservice.salesforce.SalesforceApiConnect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ClientService
{
    private final ClientRepository clientRepository;
    private final SalesforceApiConnect salesforceApiConnect;
    private final RestTemplate restTemplate;

    @Autowired
    public ClientService(ClientRepository clientRepository, SalesforceApiConnect salesforceApiConnect, RestTemplate restTemplate)
    {
        this.clientRepository = clientRepository;
        this.salesforceApiConnect = salesforceApiConnect;
        this.restTemplate = restTemplate;
    }

    public void addClient(ClientRequest clientRequest)
    {
        Client client = Client.builder()
                .cin(clientRequest.getCin())
                .dateNaissance(clientRequest.getDateNaissance())
                .email(clientRequest.getEmail())
                .phoneNumber(clientRequest.getPhoneNumber())
                .nom(clientRequest.getNom())
                .prenom(clientRequest.getPrenom())
                .salesforceId(clientRequest.getSalesforceId())
                .build();

        clientRepository.save(client);

        //Update salesforce org
        AuthenticationResponse authenticationResponse =  salesforceApiConnect.login();

        if(!clientRequest.getOrigine().equalsIgnoreCase("org"))
            salesforceApiConnect.addClient(authenticationResponse.getAccess_token(), authenticationResponse.getInstance_url(), client);

    }

    public List<Client> getClientBeneficiaires(String clientDonneurCin)
    {
        List<Transfere> listTransfers = Arrays.asList(restTemplate.getForEntity("http://transfere-service/api/transfere/Alltransferes/" + clientDonneurCin, Transfere[].class).getBody());

        List<Client> listClientBeneficiaires = new ArrayList<>();

        for(Transfere transfere : listTransfers)
        {
            Client client = clientRepository.findClientByCin(transfere.getReferenceClientBeneficiaire());
            listClientBeneficiaires.add(client);
        }

        return listClientBeneficiaires;
    }

    public void deleteClient(String cin)
    {
        clientRepository.deleteClientByCin(cin);
    }
}

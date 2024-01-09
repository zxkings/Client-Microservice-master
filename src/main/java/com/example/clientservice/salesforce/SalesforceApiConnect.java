package com.example.clientservice.salesforce;

import com.example.clientservice.model.Client;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class SalesforceApiConnect
{
    public AuthenticationResponse login()
    {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        //add org infos
        params.add("username", "abderrahim@atlasproject.com");
        params.add("password", "atlas12345zouIten0LMJrka7emGtcuSe59");
        params.add("client_secret", "8828A98FD4FEAB128B5CE1807159D2C49E9B113061D43BBB0CB49DD000046990");
        params.add("client_id", "3MVG9DREgiBqN9WlOMOR8_txnmN3lOj47_tEdf_wJc8hNEu.lOXHgIv1njB_n3sJRGZ3kg_Bz5B753wyc8id5");
        params.add("grant_type", "password");

        String requestBody = "grant_type=password&client_id=3MVG9DREgiBqN9WlOMOR8_txnmN3lOj47_tEdf_wJc8hNEu.lOXHgIv1njB_n3sJRGZ3kg_Bz5B753wyc8id5" +
                "&client_secret=8828A98FD4FEAB128B5CE1807159D2C49E9B113061D43BBB0CB49DD000046990" +
                "&username=abderrahim@atlasproject.com" +
                "&password=atlas12345zouIten0LMJrka7emGtcuSe59";

        HttpEntity<String> request = new HttpEntity<>(requestBody, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity response = restTemplate.postForEntity("https://login.salesforce.com/services/oauth2/token", request, AuthenticationResponse.class);

        return (AuthenticationResponse) response.getBody();
    }


    @SneakyThrows
    public String addClient(String accessToken, String instanceUrl, Client client) throws JsonProcessingException
    {
        HttpHeaders headers = new HttpHeaders();
        RestTemplate restTemplate = new RestTemplate();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + accessToken);
        ObjectMapper objectMapper = new ObjectMapper();

        String transfereJSON = objectMapper.writeValueAsString(client);
        HttpEntity<String> request = new HttpEntity<>(transfereJSON, headers);
        ResponseEntity<String> salesforceTestData = restTemplate.exchange(instanceUrl + "/services/apexrest/clientService", HttpMethod.POST, request, String.class);
        System.out.println("TOKEN DETAILS :: " + salesforceTestData.getBody());

        return salesforceTestData.getBody();
    }

   /* public String updateTransfere(String accessToken, String instanceUrl, Transfere trs)
    {
        HttpHeaders headers = new HttpHeaders();
        RestTemplate restTemplate = new RestTemplate();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + accessToken);
        var transfereJSON = new JSONObject();
        transfereJSON.put("reference",trs.getReference());
        transfereJSON.put("montant",trs.getMontant());
        transfereJSON.put("dateExpiration",trs.getDateExpiration());
        transfereJSON.put("referenceAgent",trs.getReferenceAgent());
        transfereJSON.put("referenceClientDonneur",trs.getReferenceClientDonneur());
        transfereJSON.put("referenceClientBeneficiaire",trs.getReferenceClientBeneficiaire());
        transfereJSON.put("status",trs.getStatus());
        HttpEntity<String> request = new HttpEntity<String>(transfereJSON.toString(), headers);
        ResponseEntity<String> salesforceTestData = restTemplate.exchange(instanceUrl + "/services/apexrest/transferService", HttpMethod.PUT, request, String.class);
        System.out.println("TOKEN DETAILS :: " + salesforceTestData.getBody());

        return salesforceTestData.getBody();
    }
    */
}
package com.gimnasio.membresia.client;

import com.gimnasio.membresia.dto.PlanDto;
import com.gimnasio.membresia.exception.ResourceNotFoundException;
import com.gimnasio.membresia.exception.ServiceUnavailableException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/** Comunicación síncrona (HTTP/REST) con plan-service. */
@Component
public class PlanClient {

    private final RestTemplate restTemplate;
    private final String baseUrl;

    public PlanClient(RestTemplate restTemplate, @Value("${services.plan.url}") String baseUrl) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
    }

    public PlanDto obtener(Long id) {
        try {
            return restTemplate.getForObject(baseUrl + "/api/planes/{id}", PlanDto.class, id);
        } catch (HttpClientErrorException.NotFound e) {
            throw new ResourceNotFoundException("El plan con id " + id + " no existe");
        } catch (ResourceAccessException e) {
            throw new ServiceUnavailableException("El servicio de planes no está disponible");
        } catch (RestClientException e) {
            throw new ServiceUnavailableException("Respuesta inesperada del servicio de planes");
        }
    }
}

package com.gimnasio.membresia.client;

import com.gimnasio.membresia.dto.SocioDto;
import com.gimnasio.membresia.exception.ResourceNotFoundException;
import com.gimnasio.membresia.exception.ServiceUnavailableException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/** Comunicación síncrona (HTTP/REST) con socio-service. */
@Component
public class SocioClient {

    private final RestTemplate restTemplate;
    private final String baseUrl;

    public SocioClient(RestTemplate restTemplate, @Value("${services.socio.url}") String baseUrl) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
    }

    public SocioDto obtener(Long id) {
        try {
            return restTemplate.getForObject(baseUrl + "/api/socios/{id}", SocioDto.class, id);
        } catch (HttpClientErrorException.NotFound e) {
            throw new ResourceNotFoundException("El socio con id " + id + " no existe");
        } catch (ResourceAccessException e) {
            throw new ServiceUnavailableException("El servicio de socios no está disponible");
        } catch (RestClientException e) {
            throw new ServiceUnavailableException("Respuesta inesperada del servicio de socios");
        }
    }
}

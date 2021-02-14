package com.swiftqueue.service.user;

import com.swiftqueue.dto.user.ClientDTO;
import com.swiftqueue.exception.business.BusinessException;
import com.swiftqueue.exception.server.ResourceNotFoundException;

import java.util.List;

public interface ClientService {
    String TYPE = "Client";
    String UPDATE_OPERATION = "UPDATE";
    ClientDTO getById(Long clientId) throws ResourceNotFoundException;
    ClientDTO save(ClientDTO clientDTO) throws BusinessException;
    List<ClientDTO> getAll();
    List<ClientDTO> findAllBySearchPhrase(String searchPhrase);
    void update(ClientDTO clientDTO) throws BusinessException;
}

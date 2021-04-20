package com.swiftqueue.service.user;

import com.swiftqueue.dto.location.AddressDTO;
import com.swiftqueue.dto.user.ClientDTO;
import com.swiftqueue.dto.user.UserInfoDTO;
import com.swiftqueue.exception.business.BusinessException;
import com.swiftqueue.exception.server.ResourceNotFoundException;
import com.swiftqueue.model.user.Client;
import com.swiftqueue.repository.user.ClientRepository;
import com.swiftqueue.service.location.AddressService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DefaultClientService implements ClientService {

    private final ModelMapper modelMapper;
    private final AddressService addressService;
    private final UserInfoService userInfoService;
    private final ClientRepository clientRepository;

    @Autowired
    public DefaultClientService(ModelMapper modelMapper, AddressService addressService, UserInfoService userInfoService,
                                ClientRepository clientRepository) {
        this.modelMapper = modelMapper;
        this.addressService = addressService;
        this.userInfoService = userInfoService;
        this.clientRepository = clientRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public ClientDTO getById(Long clientId) throws ResourceNotFoundException {
        Client client = clientRepository.findOne(clientId);
        if (client == null)
            throw new ResourceNotFoundException(TYPE, clientId);
        ClientDTO clientDTO = modelMapper.map(client, ClientDTO.class);
        clientDTO.getUserInfo().setTimeslots(null);
        return clientDTO;
    }

    @Override
    @Transactional
    public ClientDTO save(ClientDTO clientDTO) throws BusinessException {
        UserInfoDTO userInfoDTO;
        try {
            userInfoDTO = userInfoService.getById(clientDTO.getUserInfo().getId());
        } catch (ResourceNotFoundException ex) {
            throw new BusinessException(TYPE, "save", "No user found with ID: " + clientDTO.getUserInfo().getId());
        }
        clientDTO.setUserInfo(userInfoDTO);
        if (clientDTO.getAddress() != null) {
            AddressDTO addressDTO = addressService.save(clientDTO.getAddress());
            clientDTO.setAddress(addressDTO);
        }
        Client client = modelMapper.map(clientDTO, Client.class);
        clientDTO = modelMapper.map(clientRepository.save(client), ClientDTO.class);
        clientDTO.getUserInfo().setTimeslots(null);
        return clientDTO;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClientDTO> getAll() {
        return clientRepository.findAll()
                .stream().map(client -> modelMapper.map(client, ClientDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void update(ClientDTO clientDTO) throws BusinessException {
        if (!clientRepository.exists(clientDTO.getId()))
            throw new BusinessException(TYPE, UPDATE_OPERATION, "No client found with ID: " + clientDTO);
        ClientDTO oldClient = modelMapper.map(clientRepository.findOne(clientDTO.getId()), ClientDTO.class);
        if (clientDTO.getUserInfo().getId() != oldClient.getUserInfo().getId())
            throw new BusinessException(TYPE, UPDATE_OPERATION, "Client's user cannot be changed");
        if (clientDTO.getAddress() != null) {
            AddressDTO addressDTO = addressService.save(clientDTO.getAddress());
            oldClient.setAddress(addressDTO);
        }
        oldClient.setName(clientDTO.getName());
        Client client = modelMapper.map(oldClient, Client.class);
        clientRepository.save(client);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClientDTO> findAllBySearchPhrase(String searchPhrase) {
        return clientRepository.findByNameIgnoreCaseContaining(searchPhrase)
                .stream().map(result -> modelMapper.map(result, ClientDTO.class))
                .collect(Collectors.toList());
    }
}

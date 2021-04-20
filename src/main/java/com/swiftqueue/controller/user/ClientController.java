package com.swiftqueue.controller.user;

import com.swiftqueue.dto.user.ClientDTO;
import com.swiftqueue.exception.business.BusinessException;
import com.swiftqueue.exception.server.ResourceNotFoundException;
import com.swiftqueue.service.user.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/clients")
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/{clientId}")
    public ResponseEntity<ClientDTO> getClientById(@PathVariable Long clientId) throws ResourceNotFoundException {
        return ResponseEntity.ok(clientService.getById(clientId));
    }

    @PostMapping
    @PreAuthorize("#oauth2.hasScope('write')")
    public ResponseEntity<ClientDTO> saveClient(@RequestBody @Valid ClientDTO clientDTO) throws BusinessException {
        ClientDTO savedClient = clientService.save(clientDTO);
        return ResponseEntity.created(UriComponentsBuilder
                        .fromPath("/api/v1/clients/")
                        .pathSegment(String.valueOf(savedClient.getId()))
                        .build().toUri()).build();
    }

    @GetMapping("/all")
    @PreAuthorize("#oauth2.hasScope('read')")
    public ResponseEntity<List<ClientDTO>> getAllClients() {
        return ResponseEntity.ok(clientService.getAll());
    }

    @PutMapping
    public ResponseEntity<Void> updateClient(@RequestBody @Valid ClientDTO clientDTO) throws BusinessException {
        clientService.update(clientDTO);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/search/{searchPhrase}")
    public ResponseEntity<List<ClientDTO>> searchClients(@PathVariable String searchPhrase) {
        return ResponseEntity.ok(clientService.findAllBySearchPhrase(searchPhrase));
    }
}

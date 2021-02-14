package com.swiftqueue.repository.user;

import com.swiftqueue.model.user.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    List<Client> findByNameIgnoreCaseContaining(String name);
}

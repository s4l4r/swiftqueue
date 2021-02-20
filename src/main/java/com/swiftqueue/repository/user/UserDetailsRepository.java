package com.swiftqueue.repository.user;

import com.swiftqueue.model.user.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserInfo, Long> {

    UserInfo findByUsernameAndEnabled(String userName, boolean enabled);
}

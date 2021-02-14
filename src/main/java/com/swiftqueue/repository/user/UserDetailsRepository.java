package com.swiftqueue.repository.user;

import com.swiftqueue.dto.user.UserType;
import com.swiftqueue.model.user.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDetailsRepository extends JpaRepository<UserInfo, Long> {

    UserInfo findByUsernameAndEnabled(String userName, boolean enabled);

    List<UserInfo> findAllByUserType(UserType userType);
}

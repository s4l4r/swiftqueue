package com.swiftqueue.service.user;

import com.swiftqueue.dto.location.AddressDTO;
import com.swiftqueue.dto.user.UserInfoDTO;
import com.swiftqueue.exception.business.BusinessException;
import com.swiftqueue.exception.server.ResourceNotFoundException;
import com.swiftqueue.model.user.UserInfo;
import com.swiftqueue.repository.user.UserDetailsRepository;
import com.swiftqueue.service.location.AddressService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DefaultUserInfoService implements UserInfoService {

    private final ModelMapper modelMapper;
    private final AddressService addressService;
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsRepository userDetailsRepository;

    @Autowired
    public DefaultUserInfoService(ModelMapper modelMapper, AddressService addressService, UserDetailsRepository userDetailsRepository) {
        this.modelMapper = modelMapper;
        this.addressService = addressService;
        this.userDetailsRepository = userDetailsRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    @Transactional(readOnly = true)
    public UserInfo getUserInfoByUserName(String userName) {
        return userDetailsRepository.findByUsernameAndEnabled(userName, true);
    }

    @Override
    //Uses the above method for transaction management
    public UserInfoDTO getByUsername(String username) {
        UserInfo userInfo = getUserInfoByUserName(username);
        return userInfo != null ? modelMapper.map(userInfo, UserInfoDTO.class) : null;
    }

    @Override
    @Transactional(readOnly = true)
    public UserInfoDTO getById(Long id) throws ResourceNotFoundException {
        UserInfo userInfo = userDetailsRepository.findOne(id);
        if (userInfo == null)
            throw new ResourceNotFoundException(TYPE, id);
        return modelMapper.map(userInfo, UserInfoDTO.class);
    }

    @Transactional
    public UserInfoDTO save(UserInfoDTO dto) throws BusinessException {
        if (getByUsername(dto.getUsername()) != null)
            throw new BusinessException(TYPE, "save", "A user already exists with username: " + dto.getUsername());
        if (dto.getAddress() != null) {
            AddressDTO addressDTO = addressService.save(dto.getAddress());
            dto.setAddress(addressDTO);
        }
        UserInfo userInfo = modelMapper.map(dto, UserInfo.class);
        userInfo.setPassword(passwordEncoder.encode(dto.getPassword()));
        userInfo.setRole(TYPE);
        userInfo.setEnabled(true);
        return modelMapper.map(userDetailsRepository.save(userInfo), UserInfoDTO.class);
    }

    @Override
    @Transactional
    public void updateUserInfo(UserInfoDTO userInfoDTO) throws BusinessException {
        UserInfo userInfo = userDetailsRepository.findOne(userInfoDTO.getId());
        if (userInfo == null)
            throw new BusinessException("User", "update", "No user found with ID: " + userInfoDTO.getId());
        userInfo.setFirstName(userInfoDTO.getFirstName());
        userInfo.setLastName(userInfoDTO.getLastName());
        userDetailsRepository.save(userInfo);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean exists(long id) {
        return userDetailsRepository.exists(id);
    }
}

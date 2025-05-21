package com.hcl.dmms.service;

import com.hcl.dmms.dto.UserLoginRequest;
import com.hcl.dmms.entity.Customer;

public interface UserService {
    String registerMember(Customer request);
    String login(UserLoginRequest request);
}

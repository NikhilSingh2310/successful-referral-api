package com.simplifymoney.successfulreferral.mapper;

import com.simplifymoney.successfulreferral.dto.UserDTO;
import com.simplifymoney.successfulreferral.entity.User;

public class UserMapper {

    public static UserDTO toDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getReferralCode(),
                user.isProfileComplete(),
                user.isReferralComplete()
        );
    }
}

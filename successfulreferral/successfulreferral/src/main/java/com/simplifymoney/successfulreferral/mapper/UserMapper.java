package com.simplifymoney.successfulreferral.mapper;

import com.simplifymoney.successfulreferral.dto.UserDTO;
import com.simplifymoney.successfulreferral.entity.User;

/**
 * Utility class for mapping User entity to UserDTO.
 */
public class UserMapper {

    /**
     * Converts a User entity to a UserDTO.
     * @param user The user entity to be mapped.
     * @return A UserDTO containing user details.
     */
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

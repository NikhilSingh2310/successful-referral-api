package com.simplifymoney.successfulreferral.service;

import com.simplifymoney.successfulreferral.dto.UserDTO;
import com.simplifymoney.successfulreferral.entity.User;
import com.simplifymoney.successfulreferral.mapper.UserMapper;
import com.simplifymoney.successfulreferral.repository.UserRepository;
import com.simplifymoney.successfulreferral.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private User referrer;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Creating a mock referrer user
        referrer = new User();
        referrer.setId(1L);
        referrer.setName("Referrer User");
        referrer.setEmail("referrer@example.com");
        referrer.setProfileComplete(true);
        referrer.setReferralCode(UUID.randomUUID().toString().substring(0, 8));

        // Creating a mock referred user
        user = new User();
        user.setId(2L);
        user.setName("Referred User");
        user.setEmail("user@example.com");
        user.setPassword("password");
        user.setReferralCode(UUID.randomUUID().toString().substring(0, 8));
        user.setReferrer(referrer);
    }

    @Test
    void testSignup_Success() {
        when(userRepository.findByReferralCode(referrer.getReferralCode())).thenReturn(Optional.of(referrer));
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDTO result = userService.signup(user.getName(), user.getEmail(), user.getPassword(), referrer.getReferralCode());

        assertNotNull(result);
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(referrer.getId(), user.getReferrer().getId());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testSignup_InvalidReferralCode() {
        when(userRepository.findByReferralCode("invalidCode")).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () ->
                userService.signup(user.getName(), user.getEmail(), user.getPassword(), "invalidCode")
        );

        assertEquals("Invalid referral code", exception.getMessage());
    }

    @Test
    void testCompleteProfile_Success() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        userService.completeProfile(user.getId());

        assertTrue(user.isProfileComplete());
        verify(userRepository, times(2)).save(any(User.class));
    }

    @Test
    void testCompleteProfile_ReferralSuccess() {
        user.setProfileComplete(true);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(userRepository.findByReferrer(user)).thenReturn(List.of());

        userService.completeProfile(user.getId());

        assertTrue(user.isProfileComplete());
        verify(userRepository, atLeastOnce()).save(user);
    }

    @Test
    void testFindByEmail_Found() {
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));

        Optional<UserDTO> result = userService.findByEmail(user.getEmail());

        assertTrue(result.isPresent());
        assertEquals(user.getEmail(), result.get().getEmail());
    }

    @Test
    void testFindByEmail_NotFound() {
        when(userRepository.findByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

        Optional<UserDTO> result = userService.findByEmail("nonexistent@example.com");

        assertFalse(result.isPresent());
    }

    @Test
    void testGenerateReferralReport() {
        when(userRepository.findAll()).thenReturn(List.of(user));

        PrintWriter writer = mock(PrintWriter.class);
        userService.generateReferralReport(writer);

        verify(writer, atLeastOnce()).println(anyString());

    }
}

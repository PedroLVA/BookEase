package com.bookease.bookease.services;

import com.bookease.bookease.domain.Role;
import com.bookease.bookease.domain.User;
import com.bookease.bookease.dtos.user.UserGetResponseDTO;
import com.bookease.bookease.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    //first step is to inject the necessary mocks

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User mockUser;
    private String userEmail = "example@gmail.com";

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);

        // Mock Security Context
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Authentication authentication = Mockito.mock(Authentication.class);

        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        //Here goes all the initial config
        //mock user

        User mockUser = new User();
        mockUser.setId("53736b4c-c5cf-4d17-bb5b-e1fa70a3010c");
        mockUser.setName("user");
        mockUser.setEmail(userEmail);
        mockUser.setPassword("Rockasa");
        mockUser.setPhoneNumber("+1234567890");
        mockUser.setDateOfBirth(LocalDateTime.now());
        mockUser.setRole(Role.USER);

        this.mockUser = mockUser;

    }

    @Test
    @DisplayName("Should return correct user")
    void getCurrentuser() {

        //Mock the user
        String userEmail = "example@gmail.com";
        User mockUser = this.mockUser;


        // Mock authentication to return user email
        Mockito.when(SecurityContextHolder.getContext().getAuthentication().getName())
                .thenReturn(userEmail);

        // Mock userRepository response
        Mockito.when(userRepository.findByEmail(userEmail)).thenReturn(mockUser);

        // When
        User result = userService.getCurrentuser();

        // Then
        assertNotNull(result);
        assertEquals(userEmail, result.getEmail());
    }

    @Test
    @DisplayName("Should throw exception when user not found by email")
    void getCurrentUser_UserNotFound() {
        // Mock authentication
        Mockito.when(SecurityContextHolder.getContext().getAuthentication().getName())
                .thenReturn(userEmail);

        // Mock repository to return null
        Mockito.when(userRepository.findByEmail(userEmail)).thenReturn(null);

        assertThrows(RuntimeException.class, () -> userService.getCurrentuser());
    }


    @Test
    @DisplayName("Should return the correct list of users")
    void getAllUsers() {
        User user1 = new User();
        user1.setId("1");
        user1.setRole(Role.USER);
        user1.setName("John Doe");
        user1.setEmail("john@example.com");
        user1.setPhoneNumber("+1111111111");
        user1.setDateOfBirth(LocalDateTime.now());

        User user2 = new User();
        user2.setId("2");
        user2.setRole(Role.ADMIN);
        user2.setName("Jane Doe");
        user2.setEmail("jane@example.com");
        user2.setPhoneNumber("+2222222222");
        user2.setDateOfBirth(LocalDateTime.now());

        List<User> mockUsers = List.of(user1, user2);

        Mockito.when(userRepository.findAll()).thenReturn(mockUsers);

        // When
        List<UserGetResponseDTO> result = userService.getAllUsers();

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());

        // Verify first user
        assertEquals(user1.getId(), result.get(0).id());
        assertEquals(user1.getRole(), result.get(0).role());
        assertEquals(user1.getName(), result.get(0).name());
        assertEquals(user1.getEmail(), result.get(0).email());
        assertEquals(user1.getPhoneNumber(), result.get(0).phoneNumber());
        assertEquals(user1.getDateOfBirth(), result.get(0).dateOfBirth());

        // Verify second user
        assertEquals(user2.getId(), result.get(1).id());
        assertEquals(user2.getRole(), result.get(1).role());
        assertEquals(user2.getName(), result.get(1).name());
        assertEquals(user2.getEmail(), result.get(1).email());
        assertEquals(user2.getPhoneNumber(), result.get(1).phoneNumber());
        assertEquals(user2.getDateOfBirth(), result.get(1).dateOfBirth());

        Mockito.verify(userRepository, Mockito.times(1)).findAll();
    }

    @Test
    @DisplayName("Should return the newly registered user")
    void registerNewUser() {
        User mockUser = this.mockUser;

        Mockito.when(userRepository.save(mockUser)).thenReturn(mockUser);

        User result = userService.registerNewUser(mockUser);

        assertNotNull(result);

        assertEquals(mockUser.getId(), result.getId());

    }

    @Test
    @DisplayName("Should return the correct user with the correct id")
    void getUserById() {
        User mockUser = this.mockUser;

        Mockito.when(userRepository.findById(mockUser.getId())).thenReturn(Optional.of(mockUser));

        User result = userService.getUserById(mockUser.getId());

        assertEquals(mockUser.getId(), result.getId());
    }

    @Test
    @DisplayName("Should throw exception when user not found by ID")
    void getUserById_NotFound() {
        String userId = "nonexistent-id";
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userService.getUserById(userId));
    }

    @AfterEach
    void cleanup() {
        SecurityContextHolder.clearContext();
    }
}
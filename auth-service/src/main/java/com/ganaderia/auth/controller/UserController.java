//package com.ganaderia.auth.controller;
//
//import com.ganaderia.auth.dto.response.ApiResponse;
//import com.ganaderia.auth.dto.response.UserResponse;
//import com.ganaderia.auth.entity.User;
//import com.ganaderia.auth.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@RestController
//@RequestMapping("/api/users")
//@CrossOrigin(origins = "*")
//public class UserController {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @GetMapping
//    @PreAuthorize("hasRole('ADMIN') or hasRole('OWNER')")
//    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers() {
//        List<User> users = userRepository.findAll();
//        List<UserResponse> userResponses = users.stream()
//                .map(this::createUserResponse)
//                .collect(Collectors.toList());
//
//        return ResponseEntity.ok(ApiResponse.success(userResponses, "Users retrieved successfully"));
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable Long id) {
//        return userRepository.findById(id)
//                .map(user -> ResponseEntity.ok(ApiResponse.success(createUserResponse(user))))
//                .orElse(ResponseEntity.notFound().build());
//    }
//
//    private UserResponse createUserResponse(User user) {
//        UserResponse response = new UserResponse();
//        response.setId(user.getId());
//        response.setUsername(user.getUsername());
//        response.setEmail(user.getEmail());
//        response.setFirstName(user.getFirstName());
//        response.setLastName(user.getLastName());
//        response.setPhone(user.getPhone());
//        response.setRole(user.getRole().name());
//        response.setEstablishmentId(user.getEstablishmentId());
//        response.setActive(user.getActive());
//        response.setLastLogin(user.getLastLogin());
//        response.setCreatedAt(user.getCreatedAt());
//        return response;
//    }
//}
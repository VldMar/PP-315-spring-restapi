package org.mrchv.springrestapi.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@Builder
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private int age;
    private String email;
    private String password;
    private Set<String> roles = new HashSet<>();
}

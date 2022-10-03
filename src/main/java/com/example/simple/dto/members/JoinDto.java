package com.example.simple.dto.members;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class JoinDto {
    private String username;
    private String password;
    private String name;
    private String email;
}

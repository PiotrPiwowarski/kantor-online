package com.example.kantoronline.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AccountDto {

    private long id;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String email;
}

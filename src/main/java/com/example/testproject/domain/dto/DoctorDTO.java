package com.example.testproject.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDTO {

    @Size(min = 3, max = 30, message = "Please write your first name")
    private String firstName;

    @Size(min = 3, max = 30, message = "Please write your last name")
    private String lastName;

    private String patronymic;

    private String position;

    @Size(min = 3, max = 30, message = "Please write your birthday date in yyyy-mm-dd format")
    private String birthDate;

    @NotNull(message = "Please write your phone number")
    private long phoneNumber;
}

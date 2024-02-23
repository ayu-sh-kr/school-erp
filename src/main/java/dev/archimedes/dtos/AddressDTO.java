package dev.archimedes.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * DTO for {@link dev.archimedes.entities.Address}
 */
@Getter @Setter
public class AddressDTO implements Serializable {
    String id;

    @NotBlank
    String street;

    String addressLine1;
    String addressLine2;

    @NotBlank
    String pincode;

    @NotBlank
    String city;

    @NotBlank
    String state;

    @NotBlank
    String country;
}
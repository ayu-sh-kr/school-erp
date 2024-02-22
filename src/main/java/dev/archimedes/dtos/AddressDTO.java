package dev.archimedes.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * DTO for {@link dev.archimedes.entities.Address}
 */
@Getter @Setter
public class AddressDTO implements Serializable {
    String id;

    @NotNull
    String street;

    String addressLine1;
    String addressLine2;

    @NotNull
    String pincode;

    @NotNull
    String city;

    @NotNull
    String state;

    @NotNull
    String country;
}
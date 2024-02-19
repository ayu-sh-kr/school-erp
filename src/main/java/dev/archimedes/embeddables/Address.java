package dev.archimedes.embeddables;

import jakarta.persistence.Embeddable;

@Embeddable
public class Address {
    private String street;
    private String addressLine1;
    private String addressLine2;
    private String pincode;
    private String city;
    private String state;
    private String country;
}

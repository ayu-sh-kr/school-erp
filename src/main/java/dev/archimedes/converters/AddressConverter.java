package dev.archimedes.converters;

import dev.archimedes.dtos.AddressDTO;
import dev.archimedes.entities.Address;
import dev.archimedes.repositories.AddressRepository;
import dev.archimedes.service.contract.Converter;
import dev.archimedes.service.contract.EncryptionService;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Convert {@link dev.archimedes.entities.Address}
 * Reverse convert for {@link dev.archimedes.dtos.AddressDTO}
 */
@Component("AddressConverter")
@RequiredArgsConstructor
public class AddressConverter implements Converter<Address, AddressDTO> {

    private final EncryptionService hexEncryptionService;

    private final AddressRepository addressRepository;

    /**
     * This method is used to convert an Address entity to an AddressDTO.
     * It takes an Address and an AddressDTO as parameters.
     * If the AddressDTO is null, a new AddressDTO is created.
     * The properties of the Address are then set to the corresponding properties of the AddressDTO.
     * If the Address has an id, it is encrypted and set to the id of the AddressDTO.
     * The method returns the AddressDTO.
     *
     * @param address The Address entity to be converted.
     * @param addressDTO The AddressDTO to which the properties of the Address are set.
     * @return The converted AddressDTO.
     */
    @Override
    public AddressDTO convert(Address address, AddressDTO addressDTO) {

        if(null == addressDTO){
            addressDTO = new AddressDTO();
        }

        if(null != address.getId()){
            addressDTO.setId(
                    hexEncryptionService.encrypt(String.valueOf(address.getId()))
            );
        }

        addressDTO.setStreet(address.getStreet());
        addressDTO.setAddressLine1(address.getAddressLine1());
        addressDTO.setAddressLine2(address.getAddressLine2());
        addressDTO.setPincode(address.getPincode());
        addressDTO.setCity(address.getCity());
        addressDTO.setState(address.getState());
        addressDTO.setCountry(address.getCountry());

        return addressDTO;
    }

    /**
     * This method is used to convert an AddressDTO back to an Address entity.
     * It takes an AddressDTO and an Address as parameters.
     * If the Address is null, a new Address is created.
     * The properties of the AddressDTO are then set to the corresponding properties of the Address.
     * If the AddressDTO has an id, it is decrypted and set to the id of the Address.
     * The Address is then saved to the repository and returned.
     *
     * @param addressDTO The AddressDTO to be converted.
     * @param address The Address to which the properties of the AddressDTO are set.
     * @return The converted and saved Address entity.
     */
    @Override
    public Address reverseConvert(AddressDTO addressDTO, Address address) {

        if(null == address){
            address = new Address();
        }

        if(StringUtils.isNotBlank(addressDTO.getId())){
            address.setId(
                    Integer.valueOf(hexEncryptionService.decrypt(addressDTO.getId()))
            );
        }

        address.setStreet(addressDTO.getStreet());
        address.setAddressLine1(addressDTO.getAddressLine1());
        address.setAddressLine2(addressDTO.getAddressLine2());
        address.setPincode(addressDTO.getPincode());
        address.setCity(addressDTO.getCity());
        address.setState(addressDTO.getState());
        address.setCountry(addressDTO.getCountry());

        address = addressRepository.save(address);
        return address;
    }
}

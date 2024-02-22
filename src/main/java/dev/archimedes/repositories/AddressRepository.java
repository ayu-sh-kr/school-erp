package dev.archimedes.repositories;

import dev.archimedes.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface AddressRepository extends JpaRepository<Address, Integer> {

    @Modifying
    @Transactional
    @Query("update Address a set a = ?1 where a.id = ?2")
    int updateAddressById(Address address, int id);


}

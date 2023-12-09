package boot.jpa.dao;

import boot.jpa.entity.Address;

import java.util.List;

public interface AddressDao {

    void create(Address address);

    Address find(Long id);

    List<Address> findAll();

    void update(Address address);

    void delete(Address address);
}

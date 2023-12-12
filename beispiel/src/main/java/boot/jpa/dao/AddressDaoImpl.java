package boot.jpa.dao;

import boot.jpa.entity.Address;
import org.springframework.stereotype.Repository;

@Repository
public class AddressDaoImpl extends AbstractDao<Address, Long> implements AddressDao {

    public AddressDaoImpl() {
        super(Address.class);
    }
}
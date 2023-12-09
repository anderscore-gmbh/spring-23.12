package boot.jpa.dao;

import boot.jpa.entity.Trainee;
import org.springframework.stereotype.Repository;

@Repository
public class TraineeDaoImpl extends AbstractDao<Trainee, Long> implements TraineeDao {

    public TraineeDaoImpl() {
        super(Trainee.class);
    }
}
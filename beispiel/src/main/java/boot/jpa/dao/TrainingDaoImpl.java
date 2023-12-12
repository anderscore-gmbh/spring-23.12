package boot.jpa.dao;

import boot.jpa.entity.Training;
import org.springframework.stereotype.Repository;

@Repository
public class TrainingDaoImpl extends AbstractDao<Training, Long> implements TrainingDao {

    public TrainingDaoImpl() {
        super(Training.class);
    }
}
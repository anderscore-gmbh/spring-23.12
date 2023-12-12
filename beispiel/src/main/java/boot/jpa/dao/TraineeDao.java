package boot.jpa.dao;

import boot.jpa.entity.Trainee;

import java.util.List;

public interface TraineeDao {

    void create(Trainee trainee);

    Trainee find(Long id);

    List<Trainee> findAll();

    void update(Trainee trainee);

    void delete(Trainee trainee);
}

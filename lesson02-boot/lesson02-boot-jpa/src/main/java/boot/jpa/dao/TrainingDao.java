package boot.jpa.dao;

import boot.jpa.entity.Training;

import java.util.List;

public interface TrainingDao {

    void create(Training training);

    Training find(Long id);

    List<Training> findAll();

    void update(Training training);

    void delete(Training training);
}
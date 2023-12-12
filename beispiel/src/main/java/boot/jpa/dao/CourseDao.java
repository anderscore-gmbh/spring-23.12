package boot.jpa.dao;

import boot.jpa.entity.Course;

import java.util.List;

public interface CourseDao {

    void create(Course course);

    Course find(Long id);

    List<Course> findAll();

    void update(Course course);

    void delete(Course course);
}

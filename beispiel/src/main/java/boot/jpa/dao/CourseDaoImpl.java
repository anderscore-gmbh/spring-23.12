package boot.jpa.dao;

import boot.jpa.entity.Course;
import org.springframework.stereotype.Repository;

@Repository
public class CourseDaoImpl extends AbstractDao<Course, Long> implements CourseDao {

    public CourseDaoImpl() {
        super(Course.class);
    }
}
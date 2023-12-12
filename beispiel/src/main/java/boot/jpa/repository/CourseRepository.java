package boot.jpa.repository;

import boot.jpa.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    // TODO: Load all courses having titles containing "Datenbanken"
    List<Course> findByTitleLike(String pattern);

    Page<Course> findByDurationBetween(int startDuration, int endDuration, Pageable pageable);
}
package jenya.gogacpypy.repository;

import jenya.gogacpypy.model.Student;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
}

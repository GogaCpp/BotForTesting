package jenya.gogacpypy.repository;

import jenya.gogacpypy.model.SuperAdmin;
import jenya.gogacpypy.model.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
    Optional<Teacher> findFirstByLoginAndPass(String login, String pass);

    Optional<Teacher> findFirstByLogin(String login);
}

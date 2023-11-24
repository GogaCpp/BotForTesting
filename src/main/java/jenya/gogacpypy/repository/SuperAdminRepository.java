package jenya.gogacpypy.repository;

import jenya.gogacpypy.model.Admin;
import jenya.gogacpypy.model.SuperAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SuperAdminRepository extends JpaRepository<SuperAdmin, Long> {
    Optional<SuperAdmin> findFirstByLoginAndPass(String login, String pass);

    Optional<SuperAdmin> findFirstByLogin(String login);
}

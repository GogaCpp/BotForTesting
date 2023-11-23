package jenya.gogacpypy.repository;

import jenya.gogacpypy.model.Admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    Optional<Admin> findFirstByLoginAndPass(String login,String pass);
}

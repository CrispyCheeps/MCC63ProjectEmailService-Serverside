package co.id.emailservice.serverside.repository;

import co.id.emailservice.serverside.model.EmailListName;
import co.id.emailservice.serverside.model.Konten;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KontenRepository extends JpaRepository<Konten, Long> {

    Konten findBySubject(String subject);

}

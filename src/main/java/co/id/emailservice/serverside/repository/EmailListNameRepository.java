package co.id.emailservice.serverside.repository;

import co.id.emailservice.serverside.model.EmailListName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailListNameRepository extends JpaRepository<EmailListName, Long> {

    EmailListName findByName(String name);

}

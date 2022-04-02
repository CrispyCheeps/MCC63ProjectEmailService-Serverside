package co.id.emailservice.serverside.repository;

import co.id.emailservice.serverside.model.ScheduleEmail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleEmailRepository extends JpaRepository<ScheduleEmail, Long> {

}

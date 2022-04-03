package co.id.emailservice.serverside.repository;

import co.id.emailservice.serverside.model.ScheduleEmail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleEmailRepository extends JpaRepository<ScheduleEmail, Long> {
    Optional<List<ScheduleEmail>> findByTanggalKirim(LocalDateTime tanggalKirim);
}

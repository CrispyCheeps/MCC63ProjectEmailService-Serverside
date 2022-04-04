package co.id.emailservice.serverside.repository;

import co.id.emailservice.serverside.model.ScheduleEmail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleEmailRepository extends JpaRepository<ScheduleEmail, Long> {
//    List<ScheduleEmail> findByTanggalKirim(LocalDateTime tanggalKirim);
//    List<ScheduleEmail> findByTanggalKirim(LocalDateTime tanggalKirim);

    @Query(value = "SELECT id FROM tb_schedule_email WHERE tanggal_kirim = ?1", nativeQuery = true)
    Long findEmailListNameByTanggalKirim(LocalDateTime tanggalKirim);

    @Query(value = "SELECT konten_id FROM tb_schedule_email WHERE tanggal_kirim = ?1", nativeQuery = true)
    Long findKontenByTanggalKirim(LocalDateTime tanggalKirim);

    @Query(value = "SELECT * FROM tb_schedule_email WHERE tanggal_kirim = ?1", nativeQuery = true)
    List<ScheduleEmail> findByDate (String tglKirim);

}





// query bisa: SELECT tk.id FROM tb_schedule_email ts JOIN tb_konten tk ON ts.konten_id = tk.id WHERE ts.tanggal_kirim = '2022-04-04 06:46';
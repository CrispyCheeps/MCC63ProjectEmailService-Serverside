package co.id.emailservice.serverside.repository;

import co.id.emailservice.serverside.model.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {

    // JPQL Native (Berdasarkan Database)
    @Query(value = "SELECT tp.email FROM tb_email_list_name te "
            + "JOIN tb_participant tp ON "
            + "te.id = tp.email_list_name_id WHERE te.id = ?1", nativeQuery = true)
    List<Participant> filterEmailParticipantByEmailListNameId(Long id);

    // JPQL Native (Berdasarkan Database)
    @Query(value = "SELECT tp.* FROM tb_email_list_name te "
            + "JOIN tb_participant tp ON "
            + "te.id = tp.email_list_name_id WHERE te.id = ?1", nativeQuery = true)
    List<Participant> filterParticipantByEmailListNameId(Long id);

}

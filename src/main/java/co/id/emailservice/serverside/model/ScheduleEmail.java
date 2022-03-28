package co.id.emailservice.serverside.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_scheduleEmail")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleEmail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private EmailListName emailListName;

    @ManyToOne
    private Konten konten;

    @Column(nullable = false)
    private LocalDateTime tanggalKirim;


}

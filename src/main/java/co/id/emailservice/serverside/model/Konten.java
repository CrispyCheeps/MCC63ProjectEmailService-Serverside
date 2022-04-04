package co.id.emailservice.serverside.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tb_konten")
//Kalau @Data  anotasi yg ada method2 di dlmnya ada toString

/*
    Signifikasi toString dengan FetchType lazily itu, toString itu butuh akses lgsg datanya
    sdgkan lazily gabisa lgsg.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Konten {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String subject;
    private String body;
    private String footer;
    private String attachment;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "konten")
    private List<ScheduleEmail> scheduleEmail;

    @ManyToOne
    private User user;

    @ManyToOne
    private Template template;


}

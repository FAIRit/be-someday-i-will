package pl.fairit.somedayiwill.avatar;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.fairit.somedayiwill.user.AppUser;

import javax.persistence.*;

@Entity(name = "avatars")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Avatar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @Column(name = "user")
    private AppUser user;

    @Lob
    @Column(name = "data")
    private byte[] data;

    @Column(name = "file_type")
    private String fileType;
}

package pl.fairit.somedayiwill.movie;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.fairit.somedayiwill.user.AppUser;

import javax.persistence.*;
import java.time.LocalDate;

@Entity(name = "movies")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @ManyToOne
//    @JoinColumn(name = "user_id")
    private AppUser user;

    @Column(name = "genres")
    private String genres;

    @Column(name = "title")
    private String title;

    @Column(name = "release_date")
    private LocalDate releaseDate;

    @Column(name = "poster_link")
    private String posterLink;

    @Column(name = "description")
    private String description;

    @Column(name = "runtime")
    private int runtime;
}

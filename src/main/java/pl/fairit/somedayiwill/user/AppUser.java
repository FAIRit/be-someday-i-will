package pl.fairit.somedayiwill.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;
import pl.fairit.somedayiwill.avatar.Avatar;
import pl.fairit.somedayiwill.book.Book;
import pl.fairit.somedayiwill.mailsender.NewsletterFrequency;
import pl.fairit.somedayiwill.movie.Movie;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Entity(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDate createdAt;

    @NotNull
    @Length(min = 2, message = "Name has to be at at least 2 character long")
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "email", updatable = false)
    @Email(message = "Invalid email")
    private String email;

    @JsonIgnore
    @Column(name = "password")
    private String password;

    @JsonIgnore
    @OneToOne(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private Avatar avatar;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Book> books;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Movie> movies;

    @JsonIgnore
    @Enumerated(EnumType.STRING)
    @Column(name = "newsletter_frequency")
    private NewsletterFrequency newsletterFrequency;
}

package pl.fairit.somedayiwill.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;
import pl.fairit.somedayiwill.avatar.Avatar;
import pl.fairit.somedayiwill.book.usersbooks.Book;
import pl.fairit.somedayiwill.movie.Movie;
import pl.fairit.somedayiwill.newsletter.NewsletterFrequency;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Entity(name = "app_users")
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

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDate updatedAt;

    @NotNull
    @Length(min = 2, message = "Name has to be at at least 2 character long")
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "email", updatable = false)
    @Email(message = "Invalid email")
    private String email;

    @Column(name = "password")
    private String password;

    @OneToOne(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private Avatar avatar;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Book> books;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Movie> movies;

    @Enumerated(EnumType.STRING)
    @Column(name = "newsletter_frequency")
    private NewsletterFrequency newsletterFrequency;
}

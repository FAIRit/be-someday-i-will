package pl.fairit.somedayiwill.book.usersbooks;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.fairit.somedayiwill.user.AppUser;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity(name = "books")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @ManyToOne
//    @JoinColumn(name = "user_id")
    private AppUser user;

    @Column(name = "description")
    private String description;

    @Column(name = "categories")
    private String categories;

    @Column(name = "title")
    private String title;

    @Column(name = "subtitle")
    private String subtitle;

    @Column(name = "authors")
    private String authors;

    @Column(name = "page_count")
    private Integer pageCount;

    @Column(name = "buy_link")
    private String buyLink;

    @Column(name = "image_link")
    private String imageLink;
}

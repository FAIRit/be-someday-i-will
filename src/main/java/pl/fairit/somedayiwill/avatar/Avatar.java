package pl.fairit.somedayiwill.avatar;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.fairit.somedayiwill.user.AppUser;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Avatar details")
@Entity(name = "avatars")
public class Avatar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    @ApiModelProperty(notes = "The database generated avatar ID")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @ApiModelProperty(notes = "The users's ID")
    private AppUser user;

    @Lob
    @Column(name = "data")
    @ApiModelProperty(notes = "LOB")
    private byte[] data;

    @Column(name = "file_type")
    @ApiModelProperty(notes = "The avatar file type")
    private String fileType;
}

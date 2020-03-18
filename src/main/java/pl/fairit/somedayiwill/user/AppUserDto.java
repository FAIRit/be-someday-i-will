package pl.fairit.somedayiwill.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel
public class AppUserDto {
    @ApiModelProperty(notes = "The user's name")
    private String name;

    @ApiModelProperty(notes = "The user's email address")
    private String email;
}

package pl.fairit.somedayiwill.security.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel
public class SignupResponse {
    @ApiModelProperty(notes = "Message")
    private String message;
}

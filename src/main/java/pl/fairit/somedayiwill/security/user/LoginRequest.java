package pl.fairit.somedayiwill.security.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@ApiModel
public class LoginRequest {
    @NotNull
    @Email
    @ApiModelProperty(notes = "The user's email address", example = "john@doe.com")
    private String email;

    @NotNull
    @ApiModelProperty(notes = "The user's password", example = "Password1")
    private String password;
}

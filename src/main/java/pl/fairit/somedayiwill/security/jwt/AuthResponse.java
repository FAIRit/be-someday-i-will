package pl.fairit.somedayiwill.security.jwt;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    @ApiModelProperty(notes = "The access token")
    private String accessToken;
    @ApiModelProperty(notes = "The token type")
    private String tokenType = "Bearer";

    public AuthResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}

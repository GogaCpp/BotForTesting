package jenya.gogacpypy.Utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JWTResponse {

    private String accessToken;
    private String refreshToken;

}

package jenya.gogacpypy.Utils;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JWTRequest {
    private String login;
    private String password;
}

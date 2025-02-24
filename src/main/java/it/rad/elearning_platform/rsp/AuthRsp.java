package it.rad.elearning_platform.rsp;

import it.rad.elearning_platform.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AuthRsp {
    String token;
    Long userId;
}

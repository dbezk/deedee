package ua.project.deedee.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OnlineUserDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String avatar;

}

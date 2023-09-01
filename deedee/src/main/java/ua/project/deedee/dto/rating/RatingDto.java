package ua.project.deedee.dto.rating;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RatingDto {

    private int ratingPosition;
    private String firstName;
    private String lastName;
    private String avatar;
    private int rating;
    private boolean isRequester;

}

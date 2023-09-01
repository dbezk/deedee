package ua.project.deedee.entity;

import jakarta.persistence.MappedSuperclass;
import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@MappedSuperclass
public class BasicEntityCharacteristic {

    Integer power;
    Integer health;
    Integer speed;

}

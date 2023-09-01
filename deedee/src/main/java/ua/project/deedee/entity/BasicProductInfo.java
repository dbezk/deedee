package ua.project.deedee.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@MappedSuperclass
public class BasicProductInfo {

    String title;
    String description;
    @Nullable
    Integer money;
    @Nullable
    Integer vipMoney;

}
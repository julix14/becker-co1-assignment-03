package dataclasses;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Friend {
    @Setter
    @Getter
    private String name;

    @Setter
    @Getter
    private String phoneNumber;

}

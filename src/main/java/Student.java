import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student implements Serializable {
    int id;
    private String name;
    private String city;
    private String email;
    private int level;
}

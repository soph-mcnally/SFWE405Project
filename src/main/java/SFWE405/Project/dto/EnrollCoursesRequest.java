package SFWE405.Project.dto;

import lombok.Data;

import java.util.List;

@Data
public class EnrollCoursesRequest {
    private List<Long> courseIds;
}

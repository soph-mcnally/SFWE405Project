package SFWE405.Project.dto;

import lombok.Data;

import java.util.List;


@Data
public class EnrollCoursesResponse {
    private List<Long> enrolledCourseIds;
    private List<String> errors;
}

package SFWE405.Project.dto;

import lombok.Data;

@Data
public class AvailableCourseResponse {
    private Long courseId;
    private String courseCode;
    private String courseName;
    private String courseType;
    private Integer unitsAmount;
    private Boolean upperDivision;
    private String semester;
}

package SFWE405.Project.dto;

import lombok.Data;

/**
 * @author Jeriah Garcia
 *
 * Data Transfer Object (DTO) used to send available course information
 * from the backend to the frontend. This file stores course details such as
 * course ID, course code, course name, course type, unit amount,
 * upper division status, and semester information.
 */

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

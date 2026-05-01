package SFWE405.Project.dto;

import lombok.Data;

import java.util.List;

/**
 * @author Jeriah Garcia
 *
 * Data Transfer Object (DTO) used to send enrollment results
 * from the backend to the frontend. This file stores successfully
 * enrolled course IDs along with any enrollment error messages.
 */

@Data
public class EnrollCoursesResponse {
    private List<Long> enrolledCourseIds;
    private List<String> errors;
}

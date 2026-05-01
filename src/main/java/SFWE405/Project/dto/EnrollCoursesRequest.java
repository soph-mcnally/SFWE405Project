package SFWE405.Project.dto;

import lombok.Data;

import java.util.List;

/**
 * @author Jeriah Garcia
 *
 * Data Transfer Object (DTO) used to receive course enrollment requests
 * from the frontend. This file stores a list of course IDs selected
 * by a student for enrollment.
 */

@Data
public class EnrollCoursesRequest {
    private List<Long> courseIds;
}

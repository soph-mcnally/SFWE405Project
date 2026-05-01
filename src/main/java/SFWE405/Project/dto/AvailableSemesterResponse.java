package SFWE405.Project.dto;

import lombok.Data;

/**
 * @author Jeriah Garcia
 *
 * Data Transfer Object (DTO) used to transfer available semester information
 * from the backend to the frontend. This file stores semester details such as
 * the semester ID, season, and academic year.
 */
@Data
public class AvailableSemesterResponse {
    private Long id;
    private String season;
    private Integer semesterYear;
}

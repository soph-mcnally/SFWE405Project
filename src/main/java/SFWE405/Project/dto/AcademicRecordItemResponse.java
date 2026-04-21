package SFWE405.Project.dto;

import lombok.Data;

/**
 * @author Brandon Sisco
 *
 * DTO representing a single academic record item for a student.
 *
 * This object is used to return enrollment and course information
 * without exposing the full entity graph.
 */
@Data
public class AcademicRecordItemResponse {
    private Long enrollmentId;
    private String courseCode;
    private String courseName;
    private String courseType;
    private Integer unitsAmount;
    private String semester;
    private String grade;
    private String status;
}

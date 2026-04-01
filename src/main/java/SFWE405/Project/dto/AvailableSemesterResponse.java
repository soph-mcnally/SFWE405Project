package SFWE405.Project.dto;

import lombok.Data;

@Data
public class AvailableSemesterResponse {
    private Long id;
    private String season;
    private Integer semesterYear;
}

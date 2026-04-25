package SFWE405.Project.dto;

import java.util.List;
import lombok.Data;

@Data
public class AcademicRecordPageResponse {
    private List<AcademicRecordItemResponse> content;
    private int currentPage;
    private int totalPages;
    private long totalElements;
    private boolean first;
    private boolean last;
}

/*
 * CourseAssignment.java
 * Last Update: 2026-04-08
 * 
 * Primary Author: @TravisPotter 
 * 
 * 
 * Representing a Faculty that is assigned to teachg a course.
 */

package SFWE405.Project.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class CourseAssignment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courseAssignmentId;

    //Many CourseAssignments to One Faculty (People)
    @ManyToOne(optional = false)
    @JoinColumn(name = "person_id", nullable = false)
    private People faculty; // Faculty ID

    //Many CourseAssignments to One Course
    @ManyToOne(optional = false)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    //Contructors
    public CourseAssignment() {}
    
    public CourseAssignment(People faculty, Course course) {
        this.faculty = faculty;
        this.course = course;
    }

}

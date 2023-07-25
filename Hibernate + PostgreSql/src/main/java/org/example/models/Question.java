package org.example.models;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tbl_questions")
@Data
@NoArgsConstructor
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "question_type", nullable = false)
    private String questionType;

    @Column(name = "text", nullable = false)
    private String text;

    @OneToMany(mappedBy = "question")
    private List<QuestionResponse> questionResponses;
}

package org.example.models;

import lombok.*;

import javax.persistence.*;

@Entity(name = "tbl_question_responses")
@Data
@NoArgsConstructor
public class QuestionResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "is_true", nullable = false)
    private boolean isTrue;

    @Column(name = "text")
    private String text;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;
}

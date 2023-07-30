package org.example.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "tbl_question_responses")
@Data
@NoArgsConstructor
public class QuestionResponse {
    public QuestionResponse(boolean isTrue, String text, Question question) {
        this.isTrue = isTrue;
        this.text = text;
        this.question = question;
    }

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

    @Override
    public String toString() {
        return text;
    }
}

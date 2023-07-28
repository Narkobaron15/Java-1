package org.example.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

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
    @Fetch(FetchMode.JOIN)
    private List<QuestionResponse> questionResponses;

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder()
                .append(id)
                .append(". (")
                .append(questionType)
                .append(") ")
                .append(text)
                .append("\nAnswers: ");

        for (int i = 0; i < questionResponses.size(); i++) {
            builder.append(i + 1)
                    .append(". ")
                    .append(questionResponses.get(i).getText())
                    .append(" ");
        }

        return builder.toString();
    }
}

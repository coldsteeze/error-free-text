package korobkin.nikita.error_free_text.entity;

import jakarta.persistence.*;
import korobkin.nikita.error_free_text.entity.enums.TaskLanguage;
import korobkin.nikita.error_free_text.entity.enums.TaskStatus;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tasks")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String text;

    @Enumerated(EnumType.STRING)
    private TaskLanguage language;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    private String correctedText;

    private String errorMessage;

    @CreationTimestamp
    private LocalDateTime createdAt;
}

package likelion.mysql.Board;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.attoparser.dom.Text;

@Entity
@Getter
@Setter
@Table
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    @Column
    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column
    private String name;

}

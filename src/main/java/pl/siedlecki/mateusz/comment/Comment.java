package pl.siedlecki.mateusz.comment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pl.siedlecki.mateusz.EnumDatabase;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Comment {
    private int id;
    private String userName;
    private LocalDateTime created;
    private String content;
    private int aboveId;
    private EnumDatabase above;


    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", created=" + created +
                ", content='" + content + '\'' +
                ", aboveId=" + aboveId +
                ", above=" + above +
                '}';
    }
}

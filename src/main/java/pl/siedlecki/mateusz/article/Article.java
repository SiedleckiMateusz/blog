package pl.siedlecki.mateusz.article;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@Builder
public class Article {
    private int id;
    private LocalDateTime created;
    private LocalDateTime added;
    private LocalDateTime updated;
    private String subject;
    private String content;

    @Override
    public String toString() {
        return "id: " + id + ", utowrzono: " + showTime(added) +
                ", dodano: " + showTime(created) +
                ", ostatnia aktualizacja:" + showTime(updated) +
                "\n" + subject +
                "\n" + content;
    }

    public String toStringShortVersion(){
        return ("id: "+id+", Data dodania: "+showTime(added)+", Temat: "+subject);
    }

    public String showTime(LocalDateTime localDateTime){
        return localDateTime.format(DateTimeFormatter.ofPattern("DD-MM-YYYY HH:mm:ss"));
    }
}

package pl.siedlecki.mateusz.article;


import pl.siedlecki.mateusz.myDatabase.MyDatabase;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static pl.siedlecki.mateusz.Utils.hasText;

public class ArticleRepository {
    MyDatabase myDatabase;

    public ArticleRepository(MyDatabase myDatabase){
       this.myDatabase = myDatabase;
    }

    public Map<ArticleField, String> add(String subject, String content){
        Map<ArticleField,String> errors = validate(subject,content);

        if (errors.isEmpty()){
            myDatabase.insertArticle(Article.builder()
                    .subject(subject)
                    .content(content)
                    .updated(LocalDateTime.now())
                    .added(LocalDateTime.now())
                    .created(LocalDateTime.now())
                    .build());
        }

        return errors;
    }

    public Map<ArticleField, String> validate(String subject, String content){
        Map<ArticleField,String> errors = new HashMap<>();
        if (!hasText(subject)){
            errors.put(ArticleField.SUBJECT,"IS REQUIRED");
        }
        if (!hasText(content)){
            errors.put(ArticleField.CONTENT, "IS REQUIRED");
        }

        return errors;
    }

    public boolean remove(int id){
        return myDatabase.removeArticle(id);
    }

    public Map<ArticleField, String> update(Article article){
        Map<ArticleField,String> errors = validate(article.getSubject(),article.getContent());
        if (errors.isEmpty()){
            article.setUpdated(LocalDateTime.now());
            myDatabase.editArticle(article);
        }
        return errors;
    }
}

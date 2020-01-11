package pl.siedlecki.mateusz.myDatabase;

import org.junit.jupiter.api.Test;
import pl.siedlecki.mateusz.article.Article;
import pl.siedlecki.mateusz.article.ArticleField;
import pl.siedlecki.mateusz.article.ArticleRepository;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class MyDatabaseTest {
    MyDatabase myDatabase = new MyDatabase();
    ArticleRepository articleRepository = new ArticleRepository(myDatabase);


    @Test
    public void createNewArticle(){
        Map<ArticleField, String> result = articleRepository.add(
                "MySecondArticle",
                "To jest mój pierwszy artykuł.Nie wiem co napisać");

        assertTrue(result.isEmpty());

    }

    @Test
    public void showMyArticles(){
        List<Article> myArticle = myDatabase.selectArticles();
        System.out.println(myArticle.size());
        myArticle.forEach(System.out::println);

        assertFalse(myArticle.isEmpty());

        myDatabase.closeConnection();
    }

    @Test
    public void removeArticle(){
        boolean remove = myDatabase.removeArticle(1);
        showMyArticles();
    }

    @Test
    public void editArticle(){
        Map<ArticleField, String> result = articleRepository.add(
                "MyArticleToEdit",
                "Ten artykuł będzie edytowany");

        assertTrue(result.isEmpty());

        List<Article> myArticles = myDatabase.selectArticles();
        myArticles.forEach(System.out::println);
        System.out.println("********************************************************");
        Article articlee = myArticles.get(3);

        articlee.setContent("BBBBBBBBBBBBBBBBBBBB");

        articleRepository.update(articlee);

        myArticles = myDatabase.selectArticles();

        myArticles.forEach(System.out::println);
    }



}
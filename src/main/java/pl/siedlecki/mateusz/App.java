package pl.siedlecki.mateusz;

import pl.siedlecki.mateusz.article.Article;
import pl.siedlecki.mateusz.comment.Comment;
import pl.siedlecki.mateusz.myDatabase.MyDatabase;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class App 
{

    Scanner scanner = new Scanner(System.in);
    List<Article> articles;
    List<Comment> comments;
    MyDatabase myDatabase = new MyDatabase();

    public static void main( String[] args ) {
        App app = new App();
        app.menu();
    }

    public void updateArticlesAndComments(){
        articles = myDatabase.selectArticles();
        comments = myDatabase.selectComments();
    }

    public void showMyArticles(){
        System.out.println("******* Mój Blog *********");
        System.out.println("Moje artykuły: ");
        updateArticlesAndComments();
        articles.sort(Comparator.comparing(Article::getAdded));
        articles.forEach(article -> System.out.println(article.toStringShortVersion()));
    }

    public void chooseArticle(){
        showMyArticles();
        System.out.println();
        System.out.println();
        System.out.print("Podaj id artykułu: ");
        int id = scanner.nextInt();
        Optional<Article> optionalArticle = articles.stream().filter(article1 -> id==article1.getId()).findFirst();
        if (optionalArticle.isPresent()){
            articleMenu(optionalArticle.get());
        }else {
            System.out.println("Nie ma artykułu o podanym id");
        }
    }

    public void menu(){
        showMyArticles();
        System.out.println();
        System.out.println();
        System.out.println("1. Wyswietl artykuł");
        System.out.println("0. Zakończ program");
        String value = scanner.nextLine();
        switch (value){
            case "1":
                chooseArticle();
                break;
            case "0":
                System.out.println("Do widzenia");
                break;
            default:
                System.out.println("Nie ma takiej opicji. Spróbuj ponownie");
                menu();
        }
    }

    private void articleMenu(Article article) {
        System.out.println(article.toString());
        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("1. Dodaj komentarz");
        System.out.println("0. Powrót");
        String value = scanner.nextLine();
        switch (value){
            case "1":
                addComment();
                break;
            case "0":
                break;
            default:
                System.out.println("Nie ma takiej opcji. Spróbuj ponownie");
                articleMenu(article);
        }
    }

    private void addComment() {

    }

    public void showCommentForArticle(int articleId){

    }


}

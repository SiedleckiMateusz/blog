package pl.siedlecki.mateusz.myDatabase;

import pl.siedlecki.mateusz.EnumDatabase;
import pl.siedlecki.mateusz.article.Article;
import pl.siedlecki.mateusz.comment.Comment;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class MyDatabase {
    public static final String DRIVER = "org.sqlite.JDBC";
    public static final String DB_URL = "jdbc:sqlite:biblioteka.db";

    private Connection conn;
    private Statement stat;

    public MyDatabase() {
        try {
            Class.forName(MyDatabase.DRIVER);
        } catch (ClassNotFoundException e) {
            System.err.println("Brak sterownika JDBC");
            e.printStackTrace();
        }

        try {
            conn = DriverManager.getConnection(DB_URL);
            stat = conn.createStatement();
        } catch (SQLException e) {
            System.err.println("Problem z otwarciem polaczenia");
            e.printStackTrace();
        }

        createTables();
    }

    public boolean createTables()  {
        String createArticle = "CREATE TABLE IF NOT EXISTS article " +
                "(id_article INTEGER PRIMARY KEY AUTOINCREMENT," +
                " created varchar(255)," +
                " added varchar(255)," +
                " updated varchar(255)," +
                " subject varchar(255)," +
                " content varchar(255))";
        String createComment = "CREATE TABLE IF NOT EXISTS comment" +
                " (id_comment INTEGER PRIMARY KEY AUTOINCREMENT," +
                " username varchar(255)," +
                " created varchar(255)," +
                " content varchar(255)," +
                " above_id int," +
                " aboveObject varchar(255))";

        try {
            stat.execute(createArticle);
            stat.execute(createComment);
        } catch (SQLException e) {
            System.err.println("Blad przy tworzeniu tabeli");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean insertArticle(Article article) {
        try {
            PreparedStatement prepStmt = conn.prepareStatement(
                    "insert into article values (NULL, ?, ?, ?, ?, ?);");
            prepStmt.setString(1, article.getCreated().toString());
            prepStmt.setString(2, article.getAdded().toString());
            prepStmt.setString(3, article.getUpdated().toString());
            prepStmt.setString(4, article.getSubject());
            prepStmt.setString(5, article.getContent());
            prepStmt.execute();
        } catch (SQLException e) {
            System.err.println("Blad przy wstawianiu artykułu");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean insertComment(Comment comment) {
        try {
            PreparedStatement prepStmt = conn.prepareStatement(
                    "insert into comment values (NULL, ?, ?, ?, ?, ?);");
            prepStmt.setString(1, comment.getUserName());
            prepStmt.setString(2, comment.getCreated().toString());
            prepStmt.setString(3, comment.getContent());
            prepStmt.setInt(4, comment.getAboveId());
            prepStmt.setString(5, comment.getAbove().toString());
            prepStmt.execute();
        } catch (SQLException e) {
            System.err.println("Blad przy dodaniu komentarza");
            return false;
        }
        return true;
    }

    public List<Article> selectArticles() {
        List<Article> articles = new LinkedList<>();
        try {
            ResultSet result = stat.executeQuery("SELECT * FROM article");

            while(result.next()) {
                Article article = Article.builder()
                        .id(result.getInt("id_article"))
                        .created(LocalDateTime.parse(result.getString("created")))
                        .added(LocalDateTime.parse(result.getString("added")))
                        .updated(LocalDateTime.parse(result.getString("updated")))
                        .subject(result.getString("subject"))
                        .content(result.getString("content"))
                        .build();
                articles.add(article);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return articles;
    }

    public List<Comment> selectComments() {
        List<Comment> comments = new LinkedList<Comment>();
        try {
            ResultSet result = stat.executeQuery("SELECT * FROM comment");
            while(result.next()) {
                Comment comment = Comment.builder()
                        .id(result.getInt("id_comment"))
                        .userName(result.getString("username"))
                        .created(LocalDateTime.parse(result.getString("created")))
                        .content(result.getString("content"))
                        .aboveId(result.getInt("above_id"))
                        .above(EnumDatabase.valueOf(result.getString("aboveObject")))
                        .build();

                comments.add(comment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comments;
    }


    private boolean remove(String tableName, int id){
        String sql = "DELETE FROM "+tableName+" WHERE id_"+tableName+" = "+id;
        try {
            PreparedStatement prepStmt = conn.prepareStatement(sql);
            prepStmt.execute();
        } catch (SQLException e) {
            System.err.println("Blad przy usuwaniu");
            return false;
        }
        return true;
    }

    public boolean removeArticle(int id){
        return remove("article",id);
    }

    public boolean removeComment(int id){
        return remove("comment",id);
    }



    public boolean editArticle(Article article){
        try {
            PreparedStatement prepStmt = conn.prepareStatement(
                    "UPDATE article " +
                            "SET created = ?, added = ?, updated = ?, subject = ?, content = ? " +
                            "WHERE id_article = ?;");
            prepStmt.setString(1, article.getCreated().toString());
            prepStmt.setString(2, article.getAdded().toString());
            prepStmt.setString(3, article.getUpdated().toString());
            prepStmt.setString(4, article.getSubject());
            prepStmt.setString(5, article.getContent());
            prepStmt.setInt(6,article.getId());
            prepStmt.execute();
        } catch (SQLException e) {
            System.err.println("Blad przy edycji artykułu");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void closeConnection() {
        try {
            conn.close();
        } catch (SQLException e) {
            System.err.println("Problem z zamknieciem polaczenia");
            e.printStackTrace();
        }
    }
}

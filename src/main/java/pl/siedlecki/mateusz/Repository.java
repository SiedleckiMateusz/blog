package pl.siedlecki.mateusz;

public interface Repository<T> {

    boolean add(T t);
    void edit(T t);
    void remove(Long id);

}

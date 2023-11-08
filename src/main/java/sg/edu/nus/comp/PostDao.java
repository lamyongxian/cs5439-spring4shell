package sg.edu.nus.comp;

import java.util.List;

public interface PostDao {
    public int add(Post post);
    public int edit(Post post, int id);
    public int delete(int id);
    public Post find(int id);
    public Post find(int id, PostOwner owner);
    public List<Post> findAll();
    public List<Post> findAll(PostOwner owner);
}

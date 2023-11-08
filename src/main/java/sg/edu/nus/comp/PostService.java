package sg.edu.nus.comp;

import java.util.Date;
import java.util.List;

public interface PostService {
	
	public Post viewPost(int id);
	public Post viewPost(int id, PostOwner owner);
	public List<Post> listPosts(PostOwner owner);
	public List<Post> listPosts();
	public Post createOrEdit(int id, String title, String content, String imagePath, Date publishTo, Post.Status status, PostOwner owner);
	public Post createOrEdit(int id, String title, String content);
	public Post createOrEdit(Post post);
	public int deletePost(int id);

}

package sg.edu.nus.comp;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("postService")
public class PostServiceImpl implements PostService {
	
	@Autowired
	private PostDao dao;
	
	public Post createOrEdit(Post post) {
		
		Post currentPost = dao.find(post != null ? post.getId() : 0);
		Post newPost = null;
		
		if (currentPost != null && currentPost.getId() > 0) { //existing
			dao.edit(post, currentPost.getId());
			newPost = dao.find(currentPost.getId());
		} else { //new
			int intNewID = dao.add(post);
			if(intNewID > 0) {
				newPost = dao.find(intNewID);
			}
		}
		
		return newPost;
	}

	public Post createOrEdit(int id, String title, String content, String imagePath, Date publishTo, Post.Status status,
			PostOwner owner) {
		
		Post newPost = new Post(id, title, content, imagePath, publishTo, status);
		
		if (owner != null && owner.getId() > 0) { //replace owner
			newPost.setOwner(owner);
		} 
		
		return this.createOrEdit(newPost);
	}

	public Post createOrEdit(int id, String title, String content) {
		return this.createOrEdit(id, title, content, null, null, Post.Status.draft, null);
	}

	public List<Post> listPosts(PostOwner owner) {
		List<Post> posts = dao.findAll(owner);
		return posts;
	}
	
	public List<Post> listPosts() {
		List<Post> posts = dao.findAll();
		return posts;
	}

	public Post viewPost(int id) {
		Post post = dao.find(id);
		return post;
	}
	
	public Post viewPost(int id, PostOwner owner) {
		Post post = dao.find(id, owner);
		return post;
	}

	public int deletePost(int id) {
		return dao.delete(id);
	}


}

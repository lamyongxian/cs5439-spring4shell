package sg.edu.nus.comp;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier("postDao")
public class PostDaoImpl implements PostDao {

	@Autowired
	private JdbcTemplate jdbc;
	
	public int add(final Post post) {
		final PostOwner owner = post.getOwner();
		GeneratedKeyHolder holder = new GeneratedKeyHolder();
		jdbc.update(new PreparedStatementCreator() {
		    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
		        PreparedStatement statement = con.prepareStatement("INSERT INTO post (title, content, imagepath, publishtodate, status, owner) VALUES(?,?,?,?,?,?);", 
		        		Statement.RETURN_GENERATED_KEYS);
		        statement.setString(1, post.getTitle());
		        statement.setString(2, post.getContent());
		        statement.setString(3, post.getImagePath());
		        
		        String strPublishToDate = null;
		        if(post.getPublishToDate() != null) {
			        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			        strPublishToDate = sdf.format(post.getPublishToDate());
		        }
		        
		        statement.setString(4, strPublishToDate);
		        statement.setString(5, post.getStatus().name());
		        statement.setObject(6, owner != null ? owner.getId() : null);
		        return statement;
		    }
		}, holder);
		int pkPost = holder.getKey().intValue();		
		
		return pkPost;
	}

	public int edit(Post post, int id) {
		PostOwner owner = post.getOwner();
		Post.Status status = post.getStatus();
		int intAffected = jdbc.update("UPDATE post SET title = ?, content = ?, imagepath = IFNULL(?, imagepath), "
				+ "publishtodate = IFNULL(?, publishtodate), status = IFNULL(?, status), owner = IFNULL(?, owner) WHERE id = ?;",
					post.getTitle(), post.getContent(), post.getImagePath(), post.getPublishToDate(), status != null ? status.name() : null, owner != null ? owner.getId() : null, id);
		return intAffected;
	}

	public int delete(int id) {
		return jdbc.update("DELETE FROM post WHERE id = ?;", id);
	}

	public Post find(int id) {
		List<Post> posts = jdbc.query("SELECT *, u.id as uid FROM post p LEFT JOIN user u ON (p.owner = u.id) WHERE p.id = ? LIMIT 1;", new Object[] {id}, new PostRowMapper());
		
		if (posts != null && !posts.isEmpty()) {
			return posts.get(0);
		}
		return null;
	}
	
	public Post find(int id, PostOwner owner) {
		List<Post> posts = jdbc.query("SELECT *, u.id as uid FROM post p LEFT JOIN user u ON (p.owner = u.id) WHERE p.id = ? AND owner = ? LIMIT 1;", new Object[] {id, owner.getId()}, new PostRowMapper());
		
		if (posts != null && !posts.isEmpty()) {
			return posts.get(0);
		}
		return null;
	}

	public List<Post> findAll() {
		List<Post> posts = jdbc.query("SELECT *, u.id as uid FROM post p LEFT JOIN user u ON (p.owner = u.id);", new PostRowMapper());
		return posts;
	}
	public List<Post> findAll(PostOwner owner) throws NullPointerException {
		List<Post> posts = jdbc.query("SELECT *, u.id as uid FROM post p LEFT JOIN user u ON (p.owner = u.id) WHERE owner = ?;",new Object[] {owner.getId()}, new PostRowMapper());
		return posts;
	}

}



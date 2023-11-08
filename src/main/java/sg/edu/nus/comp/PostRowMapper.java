package sg.edu.nus.comp;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class PostRowMapper implements RowMapper
{
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		Post post = new Post();
		post.setId(rs.getInt("id"));
		post.setTitle(rs.getString("title"));
		post.setImagePath(rs.getString("imagepath"));
		post.setContent(rs.getString("content"));
		post.setPublishToDate(rs.getTimestamp("publishtodate")); //sql.timestamp to util.date
		post.setStatus(Post.Status.valueOf(rs.getString("status") != null ? rs.getString("status") : "none"));
		
		User user = new User();
		user.setId(rs.getInt("uid"));
		user.setName(rs.getString("name"));
		post.setOwner(user);
		
		return post;
	}

}

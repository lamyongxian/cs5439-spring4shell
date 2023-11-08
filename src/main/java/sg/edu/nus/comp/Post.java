package sg.edu.nus.comp;

import java.util.Date;
import java.util.EnumSet;
import java.util.regex.Pattern;

import org.springframework.web.multipart.MultipartFile;

import sg.edu.nus.comp.User.UserRole;

public class Post implements WithFile{
	
	public enum Status {
		none,
		draft,
		ready,
		reject,
		publish,
		archive
	}
	
	public Post(int id, String title, String imagePath, String content, Date publishToDate, Status status) {
		super();
		this.id = id;
		this.title = this.stripXSS(title);
		this.imagePath = imagePath;
		this.content = this.stripXSS(content);
		this.publishToDate = publishToDate;
		this.status = status;
	}

	public Post() {
		super();
	}

	private int id;
	private String title;
	private String imagePath;
	private String content;
	private Date publishToDate;
	private Status status;
	private PostOwner owner;
	
	private MultipartFile file;
	
	public PostOwner getOwner() {
		return owner;
	}

	public void setOwner(PostOwner owner) {
		this.owner = owner;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = this.stripXSS(title);
	}
	
	public String getImagePath() {
		return imagePath;
	}
	
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	
	public String getContent() {
		return content;
	}
	
	public void setContent(String content) {
		this.content = this.stripXSS(content);
	}
	
	public Date getPublishToDate() {
		return publishToDate;
	}
	
	public void setPublishToDate(Date publishToDate) {
		this.publishToDate = publishToDate;
	}
	
	public Status getStatus() {
		return status;
	}
	
	public void setStatus(Status status) {
		this.status = status;
	}
	
	@Override
	public String toString() {
		return "Post [id=" + id + ", title=" + title + ", imagePath=" + imagePath + ", content=" + content
				+ ", publishToDate=" + publishToDate + ", status=" + status + ", owner=" + (owner != null ? owner.getId() : 0) + "]";
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}
	
	public static Status[] getSelectableStatus(User user) {
		Post.Status statuses[];
		if(user.getRole() == UserRole.admin) {
			statuses = Post.Status.values();
		} else { //For non-admin, limit selectable post statuses
			statuses = EnumSet.of(Status.draft, Status.ready).toArray(new Status[2]);
		}
		return statuses;
	}
	
	//TODO: XSS Sanitation using OWASP ESAPI
	//Source: https://stackoverflow.com/questions/24077596/spring-mvc-escape-request-body
	private String stripXSS(String value) {
        if (value != null) {

            // Avoid null characters
            value = value.replaceAll("", "");

            // Avoid anything between script tags
            Pattern scriptPattern = Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll("");

            // Avoid anything in a src='...' type of expression
            scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\'(.*?)\\\'", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");

            scriptPattern = Pattern.compile("src[\r\n]*=[\r\n]*\\\"(.*?)\\\"", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");

            // Remove any lonesome </script> tag
            scriptPattern = Pattern.compile("</script>", Pattern.CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll("");

            // Remove any lonesome <script ...> tag
            scriptPattern = Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");

            // Avoid eval(...) expressions
            scriptPattern = Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");

            // Avoid expression(...) expressions
            scriptPattern = Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");

            // Avoid javascript:... expressions
            scriptPattern = Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll("");

            // Avoid vbscript:... expressions
            scriptPattern = Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE);
            value = scriptPattern.matcher(value).replaceAll("");

            // Avoid onload= expressions
            scriptPattern = Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
            value = scriptPattern.matcher(value).replaceAll("");
            
            System.out.println("XSS Sanitized Content: " + value);
        }
        
        return value;
    }
}

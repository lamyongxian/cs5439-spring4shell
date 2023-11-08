package sg.edu.nus.comp;

import org.springframework.web.multipart.MultipartFile;

public interface WithFile {
	
	public MultipartFile getFile();
	public void setFile(MultipartFile file);

}

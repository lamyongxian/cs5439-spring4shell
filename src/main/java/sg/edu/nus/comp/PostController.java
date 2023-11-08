package sg.edu.nus.comp;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jakarta.activation.FileTypeMap;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.security.access.*;

import sg.edu.nus.comp.User.UserRole;

@Controller
public class PostController {
	
	@Autowired
	PostService postService;
	
	@Autowired
	private ServletContext context; 
	
    @RequestMapping(value = "/posts", method = RequestMethod.GET)
    public ModelAndView showPosts(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        ModelAndView mv = new ModelAndView("posts");
        
        List<Post> liPosts = null; 
        
        /***************** ACCESS CHECK ****************/
        User owner = (User) session.getAttribute("loginUser");
        
        if (owner != null) {
	        if(owner.getRole() == UserRole.admin) { 
	        	liPosts = postService.listPosts();
	        } else { //Non-admin, list only posts owned by this login user
	        	liPosts = postService.listPosts(owner);
	        	mv.addObject("strDisplayUsers", "none");
	        }
        } else {
        	throw new AccessDeniedException("403 returned");
        }
        /***********************************************/

        mv.addObject("posts", liPosts);
        return mv;
    }
    
    
    @RequestMapping(value = "/view_post", method = RequestMethod.GET)
    public ModelAndView viewPost(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        ModelAndView mv = null;
        try {

	        int intPost = Integer.parseInt(request.getParameter("id"));
	        
	        /***************** ACCESS CHECK ****************/
	        User owner = (User) session.getAttribute("loginUser");
	        
	        if (owner != null) {
				if(owner.getRole() == UserRole.admin) { //login user is admin
					System.out.println("viewPost: Admin access.");
					//Do nothing, skip further checks
				} else if(postService.viewPost(intPost, owner) != null) { //login user is owner of post
					System.out.println("viewPost: Owner access.");
					//Do nothing, skip further checks
				} else {
					throw new AccessDeniedException("403 returned");
				}
	        } else {
	        	throw new AccessDeniedException("403 returned");
	        }
	        /***********************************************/
			
			Post post = postService.viewPost(intPost);
			mv = new ModelAndView("view_post");
			mv.addObject("post", post);
			mv.addObject("strDisplayMsg", "none");
	        
		} catch (NumberFormatException ex) {
			System.out.println("PostController edit error: " + ex.getMessage());
		}
        return mv;
    }
    
    @RequestMapping(value = "/new_post", method = RequestMethod.GET)
	public ModelAndView newPost(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		
		ModelAndView mv = null;
		try {

			/***************** ACCESS CHECK ****************/
			User owner = (User) session.getAttribute("loginUser");
			
			if (owner != null) {
				if(owner.getRole() == UserRole.admin) { //login user is admin
					System.out.println("newPost: Admin access.");
					//Do nothing, skip further checks
				} else if (owner.getRole() == UserRole.editor) {
					System.out.println("newPost: Editor access.");
					//Do nothing, skip further checks
				} else {
					throw new AccessDeniedException("403 returned");
				}
			} else {
				throw new AccessDeniedException("403 returned");
			}
			/***********************************************/
			
			Post post = new Post();	
			post.setStatus(Post.Status.none);
			mv = new ModelAndView("edit_post");
			mv.addObject("post", post);
			mv.addObject("statuses", Post.getSelectableStatus(owner));
			mv.addObject("strMsg", "Click 'Save' to create new post.");
			mv.addObject("strDisplayMsg", "show");
			
		} catch (NumberFormatException ex) {
			System.out.println("PostController edit error: " + ex.getMessage());
		}
		return mv;
	}
	
	@RequestMapping(value = "/edit_post", method = RequestMethod.GET)
	public ModelAndView editPost(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		
		ModelAndView mv = null;
		try {

			int intPost = Integer.parseInt(request.getParameter("id"));

			/***************** ACCESS CHECK ****************/
			User owner = (User) session.getAttribute("loginUser");
			
			if (owner != null) {
				if(owner.getRole() == UserRole.admin) { //login user is admin
					System.out.println("editPost: Admin access.");
					//Do nothing, skip further checks
				} else if(postService.viewPost(intPost, owner) != null) { //login user is owner of post
					System.out.println("editPost: Owner access.");
					//Do nothing, skip further checks
				} else {
					throw new AccessDeniedException("403 returned");
				}
			} else {
				throw new AccessDeniedException("403 returned");
			}
			/***********************************************/
				
			Post post = postService.viewPost(intPost);
			mv = new ModelAndView("edit_post");
			mv.addObject("post", post);
			
			mv.addObject("statuses", Post.getSelectableStatus(owner));
			
			mv.addObject("strDisplayMsg", "none");
			System.out.println("Edit Post: " + post.toString());
			
		} catch (NumberFormatException ex) {
			System.out.println("PostController edit error: " + ex.getMessage());
		}
		return mv;
	}
	
	@RequestMapping(value = "/save_post", method = RequestMethod.POST)
	public ModelAndView savePost(HttpServletRequest request, HttpServletResponse response, HttpSession session,
			@ModelAttribute("post") Post post, BindingResult result) {
				
		ModelAndView mv = null;
		
		/***************** ACCESS CHECK ****************/
		User owner = (User) session.getAttribute("loginUser");
		
		if (owner != null) {
			if (post.getId() <= 0 && owner != null) { //new post, must be logged in
				//Do nothing, skip further checks
			} else if(owner.getRole() == UserRole.admin) { //login user is admin
				System.out.println("editPost: Admin access.");
				//Do nothing, skip further checks
			} else if(postService.viewPost(post.getId(), owner) != null) { //login user is owner of post
				System.out.println("editPost: Owner access.");
				//Do nothing, skip further checks
			} else {
				throw new AccessDeniedException("403 returned");
			}
		} else {
			throw new AccessDeniedException("403 returned");
		}
		/***********************************************/
		
		MultipartFile fileImage = post.getFile();
		
		/****************** HANDLE FILE ****************/
		if(fileImage != null && !fileImage.isEmpty()) {
			String strFilename = fileImage.getOriginalFilename();
			String strFilepath = context.getRealPath("/upload/");
			System.out.println("Upload Location: " + strFilepath);
			
			post.setImagePath("/upload/" + strFilename);
			File file = new File(strFilepath);
			file.mkdir();
			
			try {
				byte byteImage[] = fileImage.getBytes();
				String cleanFilename = strFilename.replaceAll("\\.\\.", "")
						.replaceAll("/", "")
						.replaceAll("\\\\", "");
				BufferedOutputStream bOut=new BufferedOutputStream(
						new FileOutputStream(strFilepath + FilenameUtils.getName(cleanFilename)));
				bOut.write(byteImage);
				bOut.flush();
				bOut.close();
			} catch (IOException e) {
				System.out.println("File IO Error: " + e.getMessage());
			}
			
		}
		/***********************************************/
		
		mv = new ModelAndView("edit_post");
		mv.addObject("statuses", Post.getSelectableStatus(owner));
		
		mv.addObject("strMsg", "Saved!");
		mv.addObject("strDisplayMsg", "show");
		
		if(post.getOwner() == null) { //Set Owner if null, e.g. new post
			post.setOwner(owner);
		}
		
		System.out.println("Post Post: " + post.toString());
		
		Post.Status[] validStatuses = Post.getSelectableStatus(owner);
		if (post.getStatus() == null) { //No change to status if null
			//Do nothing, skip to createOrEdit
		} else if (Arrays.asList(validStatuses).contains(post.getStatus())) { //Check if selected status is allowed for current login user
			//Do nothing, skip to createOrEdit
		} else {
			throw new AccessDeniedException("403 returned");
		} 
		//TODO: Encrypt valid status for dropdownlist 'value' instead (Harden Security)
		
		post = postService.createOrEdit(post);
		
		mv.addObject("post", post);
		
		return mv;
		
	}
	
	@RequestMapping(value = "/delete_post", method = RequestMethod.GET)
	public ModelAndView deletePost(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
				
		ModelAndView mv = null;

		int intPost = Integer.parseInt(request.getParameter("id"));

		/***************** ACCESS CHECK ****************/
		User owner = (User) session.getAttribute("loginUser");
		
		if (owner != null) {
			if(owner.getRole() == UserRole.admin) { //login user is admin
				System.out.println("editPost: Admin access.");
				//Do nothing, skip further checks
			} else if(postService.viewPost(intPost, owner) != null) { //login user is owner of post
				System.out.println("editPost: Owner access.");
				//Do nothing, skip further checks
			} else {
				throw new AccessDeniedException("403 returned");
			}
		} else {
			throw new AccessDeniedException("403 returned");
		}
		/***********************************************/
       
		//TODO: Clean up file from `imgpath` on post deletion.
		
		int intAffected = postService.deletePost(intPost);
		
		if (intAffected > 0) { //If a row deleted successfully
			mv = new ModelAndView("redirect:/posts");
		} else {
			mv = new ModelAndView("edit_post");
			mv.addObject("strMsg", "Error deleting!");
			mv.addObject("strDisplayMsg", "show");
		}
		
		return mv;
		
	}
	
	@RequestMapping(value = "/upload/{filename}.{ext}", method = RequestMethod.GET)
	public ResponseEntity<byte[]> getImage(@PathVariable("filename")String filename, @PathVariable("ext")String ext, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		
		String strFilepath = context.getRealPath("/upload/");
		String cleanExt = ext.replaceAll("\\.", "")
				.replaceAll("/", "")
				.replaceAll("\\\\", "");
		String cleanFilename = filename.replaceAll("\\.", "")
				.replaceAll("/", "")
				.replaceAll("\\\\", "");
	    File fileImage = new File(strFilepath + FilenameUtils.getName(cleanFilename + "." + cleanExt));

	    return ResponseEntity.ok()
	    		.contentType(MediaType.valueOf(FileTypeMap.getDefaultFileTypeMap().getContentType(fileImage)))
	    		.body(Files.readAllBytes(fileImage.toPath()));
	
	}

}

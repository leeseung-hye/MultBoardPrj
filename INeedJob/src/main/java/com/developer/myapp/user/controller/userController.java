package com.developer.myapp.user.controller;

import java.nio.charset.Charset;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.developer.myapp.user.model.User;
import com.developer.myapp.user.model.UserUploadFile;
import com.developer.myapp.user.service.IUserService;

@Controller
public class userController { // 회占쏙옙占쏙옙占쏙옙 占쏙옙占  占쏙옙청占쏙옙 처占쏙옙占싹깍옙 占쏙옙占쏙옙 占쏙옙트占싼뤄옙 (占쌘들러)
   static final Logger logger = LoggerFactory.getLogger(userController.class);

   @Autowired
   IUserService UserService;
   
   // 회占쏙옙占쏙옙占쏙옙 (占쏙옙占쏙옙占쏙옙)
   @RequestMapping(value="/user/empinsert", method=RequestMethod.GET)
   public String empJoinForm() {
      return "/user/empinsert";
   }
   
   @RequestMapping(value="/user/empinsert", method=RequestMethod.POST)
   public String empinsert(User user, Model model, HttpSession session) {
      logger.info("/empinsert:"+user.toString());
      System.out.println("占쏙옙占쏙옙 占쏙옙占싸듸옙");
     try{
        MultipartFile mfile = user.getFile();
       
     if(mfile!=null && !mfile.isEmpty()) { 
     logger.info("/empinsert:"+user.getUserFileId());
     UserUploadFile file = new UserUploadFile();
      file.setUserId(user.getUserId());
       file.setUserFileName(mfile.getOriginalFilename());
       file.setUserFileSize(mfile.getSize());
       file.setFileContentType(mfile.getContentType());
       file.setUserFileData(mfile.getBytes());
      logger.info("/empinsert : " + user.toString());
             UserService.insertUser(user, file);
      
        }else {
            UserService.insertUser(user);

        }
     }catch (Exception e) {
        e.printStackTrace();
    }
      session.invalidate();
      return "redirect:/";
   }
   // 회占쏙옙占쏙옙占쏙옙 (占쏙옙占쏙옙曼占쏙옙)
   @RequestMapping(value="/user/jhinsert", method=RequestMethod.GET)
   public String jhJoinForm() {
      return "/user/jhinsert";
   }
   
   @RequestMapping(value="/user/jhinsert", method=RequestMethod.POST)
   public String jhinsert(User user, Model model, HttpSession session, MultipartFile file) {
   
      UserService.insertUser(user);
      // userService.uploadFile(file);
      session.invalidate();
      return "redirect:/";
   }
   
   
   // 회占쏙옙 占쏙옙占쏙옙 占쏙옙 占쏙옙회
   @RequestMapping(value="/user/details/{userId}", method=RequestMethod.GET)
   public String detailPage(@PathVariable String userId, Model model) {
      
      User user = UserService.selectUser(userId);
      model.addAttribute("user",user);

      return "user/details";
   }
   
   @RequestMapping(value="/user/login", method=RequestMethod.GET)
   public String login() {
      return "user/login";
   }
   
   @RequestMapping(value="/user/login", method=RequestMethod.POST)
      public String login(String userId, String userPw, HttpSession session, Model model) {
         User user = UserService.selectUser(userId);
         if(user != null) {
            String dbPassword = user.getUserPw();
            
            if(dbPassword == null) { // 占쏙옙橘占싫ｏ옙占  占쏙옙占쌕댐옙 占쏙옙占쏙옙 占쏙옙占싱듸옙 占쏙옙占쌕댐옙 占쏙옙
               model.addAttribute("message", "NOT_VALID_USER");
               
            }else { // DB占쏙옙 占쏙옙橘占싫  占쏙옙占쏙옙 占쏙옙
               if(dbPassword.equals(userPw)) { // 占쏙옙占싱듸옙 占쌍곤옙, DB占쏙옙 占쏙옙橘占싫ｏ옙占  占쌉뤄옙占쏙옙 占쏙옙橘占싫ｏ옙占  占쏙옙占쏙옙 占쏙옙
                  session.setAttribute("userId", userId);
                  session.setAttribute("userPw", user.getUserPw());
                 session.setAttribute("name", user.getUserName());               
                 session.setAttribute("email", user.getUserEmail());
                 session.setAttribute("approval", user.getUserApproval());
                  
                  return "redirect:/";

               }else { // 占쏙옙占싱듸옙 占쌍곤옙, DB占쏙옙 占쏙옙橘占싫ｏ옙占  占쌉뤄옙占쏙옙 占쏙옙橘占싫ｏ옙占  占쏙옙占쏙옙 占쏙옙占쏙옙 占쏙옙
                  model.addAttribute("message", "WRONG_PASSWORD");
               }
            }
            }else { // 占쌉뤄옙占쏙옙 占쏙옙占쏙옙占쏙옙 DB占쏙옙 占쏙옙占쏙옙 占쏙옙
               model.addAttribute("message", "USER_NOT_FOUND");

            }
            session.invalidate();
            return "redirect:/";

      }

   
   @RequestMapping(value="/user/logout", method=RequestMethod.GET)
   public String logout(HttpSession session, HttpServletRequest request) {
      session.invalidate(); // 占싸그아울옙
      return "redirect:/";
   }
   
   @RequestMapping(value="/user/list", method=RequestMethod.GET)
   public String updateArticle(Model model) { // 占쌉시깍옙 占쏙옙占쏙옙, 占쌉시글울옙 占승댐옙 占쏙옙호 占쏙옙占쏙옙占쏙옙占쏙옙 占쏙옙占쏙옙
      List<User> userList = UserService.selectAllUsers();
      model.addAttribute("userList", userList);
      return "user/list";
      
   }
   
   // 회占쏙옙 占쏙옙占쏙옙 占쏙옙占쏙옙
   @RequestMapping(value="/user/mypage/{userId}", method=RequestMethod.GET)
   public String updateMypage(@PathVariable String userId, HttpSession session, Model model) {
      if(userId != null && !userId.equals("")) {
         User user = UserService.selectUser(userId);
            Integer fileId = UserService.selectFileId(userId);
            model.addAttribute("fileId", fileId);
         model.addAttribute("user", user);
         model.addAttribute("message", "UPDATE_USER_INFO");
         return "user/mypage";

      }else { // user Id占쏙옙 占쏙옙占실울옙 占쏙옙占쏙옙 占쏙옙 (占싸깍옙占쏙옙 占쏙옙占쏙옙 占십억옙占쏙옙 占쏙옙)
         model.addAttribute("message", "NOT_LOGIN_USER");
         return "user/mypage";
      }         
   }
   @RequestMapping(value="/user/mypage", method=RequestMethod.POST)
   public String updateMypage(User user, HttpSession session, Model model) {
      try{
         UserService.updateMypage(user);
         model.addAttribute("message", "UPDATE_USER_INFO");
         model.addAttribute("user", user);
         session.setAttribute("userPw", user.getUserPw());
         session.setAttribute("name", user.getUserName());               
         session.setAttribute("email", user.getUserEmail()); 
//         session.setAttribute("company", user.getUserCompany()); 
         return "/home";
         
      }catch(Exception e){
         model.addAttribute("message", e.getMessage());
         e.printStackTrace();
         return "/home";
      }
   }
   
   // 占쏙옙占쏙옙占쏙옙 占쏙옙占쏙옙占쏙옙占쏙옙 회占쏙옙 占쏙옙占쏙옙 占쏙옙占쏙옙
   @RequestMapping(value="/user/update/{userId}", method=RequestMethod.GET)
   public String updateUser(@PathVariable String userId, HttpSession session, Model model) {
      if(userId != null && !userId.equals("")) {
         User user = UserService.selectUser(userId);
           Integer fileId = UserService.selectFileId(userId);
           model.addAttribute("fileId", fileId);
         model.addAttribute("user", user);
         model.addAttribute("message", "UPDATE_USER_INFO");
         return "user/update";

      }else { // user Id占쏙옙 占쏙옙占실울옙 占쏙옙占쏙옙 占쏙옙 (占싸깍옙占쏙옙 占쏙옙占쏙옙 占십억옙占쏙옙 占쏙옙)
         model.addAttribute("message", "NOT_LOGIN_USER");
         return "user/update";
      }
   }
   @RequestMapping(value="/user/update", method=RequestMethod.POST)
   public String updateUser(User user, HttpSession session, Model model) {
      try{
         UserService.updateUser(user);
         model.addAttribute("message", "UPDATED_USER_INFO");
         model.addAttribute("user", user);
         session.setAttribute("userPw", user.getUserPw());
         session.setAttribute("name", user.getUserName());               
         session.setAttribute("email", user.getUserEmail()); 
         return "user/details"; // 占쏙옙占쏙옙
         
      }catch(Exception e){
         model.addAttribute("message", e.getMessage());
         e.printStackTrace();
         return "user/details"; // 占쏙옙占 
      }
   }
    // 회占쏙옙 占쏙옙占쏙옙 占쏙옙占쏙옙
   @RequestMapping(value="/user/delete/{userId}", method=RequestMethod.GET) 
   public String withdraw(@PathVariable String userId, HttpSession session, Model model) {
      if(userId != null && !userId.equals("")) {
            User user = UserService.selectUser(userId);
            model.addAttribute("user", user);
            model.addAttribute("message", "DELETE_USER_INFO");
            
            return "user/withdraw";
            
         }else { // user Id占쏙옙 占쏙옙占실울옙 占쏙옙占쏙옙 占쏙옙 (占싸깍옙占쏙옙 占쏙옙占쏙옙 占십억옙占쏙옙 占쏙옙)
            model.addAttribute("message", "NOT_LOGIN_USER");
            return "user/details/"+userId;
     }
   }
   @RequestMapping(value="/user/delete", method=RequestMethod.POST)
   public String deleteUser(@RequestParam String userId,@RequestParam String userPw, Model model, HttpSession session) {
      
      //1.占싸깍옙占쏙옙처占쏙옙 占쏙옙占싱듸옙, 占쏙옙橘占싫  체크
      //2.占쏙옙占싱듸옙 占쏙옙橘占싫  占쏙옙치占싹몌옙 占쏙옙占쏙옙 占십깍옙화 占쏙옙 占쏙옙占쏙옙 占쏙옙 홈占쏙옙占쏙옙 -> "redirect:/"
      //3.占쏙옙치占쏙옙占쏙옙 占쏙옙占쏙옙占쏙옙 占쌕쏙옙 占쏙옙占쏙옙占쏙옙占쏙옙占쏙옙占쏙옙 -> return "redirect:/user/delete/"+userId;
      User user = UserService.selectUser(userId); //占쏙옙占싱디에댐옙占쏙옙 占쏙옙체 占쏙옙占쏙옙占쏙옙 占쏙옙
      if(user.getUserPw().equals(userPw)) { //占쏙옙占쏙옙 占쌉뤄옙占쏙옙 占쏙옙橘占싫ｏ옙占  占승는곤옙
            //占쏙옙占쏙옙占쏙옙 占쌔억옙占싹는듸옙 占쏙옙占쏙옙占쏙옙占쏙옙占쏙옙 占쌨소드가 占쏙옙占쏙옙 占쏙옙占쏙옙
         
            UserService.deleteUser(user); //占싱뤄옙 占쏙옙占쏙옙 占싫곤옙占쏙옙
            session.invalidate();   //占쏙옙占쏙옙占십깍옙화 = 占싸그아울옙   
            
            return "/";
            
         }else { // user Id占쏙옙 占쏙옙占실울옙 占쏙옙占쏙옙 占쏙옙 (占싸깍옙占쏙옙 占쏙옙占쏙옙 占십억옙占쏙옙 占쏙옙)
            model.addAttribute("message", "WRONG_PASSWORD");
            return "redirect:/user/delete/"+userId;
     }
   }
   
   @RequestMapping("/file/{fileId}")
   public ResponseEntity<byte[]> getFile(@PathVariable int fileId) {
      UserUploadFile file = UserService.getFile(fileId);
      final HttpHeaders headers = new HttpHeaders();
      String[] mtypes = file.getFileContentType().split("/");
      headers.setContentType(new MediaType(mtypes[0], mtypes[1]));
      headers.setContentLength(file.getUserFileSize());
      headers.setContentDispositionFormData("attachment", file.getUserFileName(), Charset.forName("UTF-8"));
      return new ResponseEntity<byte[]>(file.getUserFileData(), headers, HttpStatus.OK);
   }
}
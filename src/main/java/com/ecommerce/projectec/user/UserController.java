package com.ecommerce.projectec.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RequestMapping("user")
@RequiredArgsConstructor
@Controller
public class UserController {
    private final UserService userService;
    //password encoder
    /*
     * @Author 강병관
     * 
     * DB에 동일한 이메일이 있는지 확인하는 코드
     * 
    @RequestMapping("dupCheck")
    @ResponseBody
    public HashMap<String, Object> dupCheck(@RequestBody String userEmail) {
        userEmail = userEmail.replaceAll("\"", "");
        HashMap<String, Object> user = userService.userSelectByEmail(userEmail);
        System.out.println(userEmail);
        return user;
    }*/

    
    
    /*
     * @Author 강병관
     *
     * 이메일, 닉네임, 패스워드를 DB에 저장.
     * 겹치는 데이터가 있으면 실패페이지, 성공하면 성공페이지로 이동.
     * 브라우저 단에서 유효성 검사 반드시 해야함.
     *
     */
    @RequestMapping("signup")
    public String signup(@RequestParam("USER_EMAIL") String userEmail,
                         @RequestParam("USER_NAME") String userName,
                         @RequestParam("USER_PWD") String userPwd,
                         @RequestParam("MARKET_CHK") String marketChk,
                         @RequestParam("PRIVAC_CHK") String privacChk,
                         Model model) {

        if (userService.userSelectByEmail(userEmail) != null) {
            return "redirect:/join";
        }

        try {
            HashMap<String, Object> user = userService.userSelectByEmail(userEmail);
            HashMap<String, Object> newUser = new HashMap<>();
            newUser.put("USER_EMAIL", userEmail);
            newUser.put("USER_NAME", userName);
            newUser.put("USER_PWD", userPwd);
            userService.userInsert(newUser);
        }
        catch(Exception e) {
            e.printStackTrace();
            return "redirect:/error";
        }

        model.addAttribute("USER_EMAIL", userEmail);

        return "user/joinConfirm";
    }
    @RequestMapping("login")
    public String login(@RequestParam("USER_EMAIL") String userEmail,
                        @RequestParam("USER_PWD") String userPwd,
                        HttpSession httpSession) {
        HashMap<String, Object> user = userService.userSelectByEmail(userEmail);

        if (user == null) {
            return "redirect:/login";
        }
        // important: 비밀번호 인코딩 비교로 교체 필요
        if (!(userPwd.equals(user.get("USER_PWD")))) {
            return "redirect:/login";
        }

        httpSession.setAttribute("USER_EMAIL" , user.get("USER_EMAIL"));
        httpSession.setAttribute("USER_NAME", user.get("USER_NAME"));
        return "redirect:/";
    }

    @RequestMapping("logout")
    public String logout(HttpSession httpSession) {
        httpSession.invalidate();
        // important: 이거 빼면 어떻게 되는지 물어보기
        return "redirect:/";
    }
}

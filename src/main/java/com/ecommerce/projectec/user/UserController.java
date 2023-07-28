package com.ecommerce.projectec.user;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.ecommerce.projectec.term.TermService;
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
    private final TermService termService;
    //password encoder
    
    /*
     * @Author 강병관
     *
     * 이메일, 닉네임, 패스워드를 DB에 저장.
     * 겹치는 데이터가 있으면 실패페이지, 성공하면 성공페이지로 이동.
     * 브라우저 단에서 유효성 검사 반드시 해야함.
     * param 개수가 적기 때문에 하드코딩 되어 있는 부분이 많아서 향후 늘어날 것을 대비해 리팩토링 필요.
     */
    @RequestMapping("signup")
    public String signup(@RequestParam("USER_EMAIL") String userEmail,
                         @RequestParam("USER_NAME") String userName,
                         @RequestParam("USER_PWD") String userPwd,
                         @RequestParam(value = "MARKET_CHK", required = false) String marketChk,
                         @RequestParam(value = "PRIVAC_CHK", required = false) String privacChk,
                         Model model) {

        if (userService.userSelectByEmail(userEmail) != null) {
            return "redirect:/join";
        }

        // important: 향후 아래와 트랜잭션 처리로 바꿔줘야 함. 기본키 이메일로 변경하는 것 고려.
        try {
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

        try {
            long userIdNo = (long) userService.userSelectByEmail(userEmail).get("USER_ID_NO");
            List<HashMap<String, Object>> terms = new ArrayList<>();
            addTerm(terms, userIdNo, "MARKET_CHK", marketChk);
            addTerm(terms, userIdNo, "PRIVAC_CHK", privacChk);
            System.out.println(terms);
            termService.insertAll(terms);
        }
        catch(Exception e) {
            e.printStackTrace();
            return "redirect:/error";
        }

        model.addAttribute("USER_EMAIL", userEmail);
        return "user/joinConfirm";
    }

    private void addTerm(List<HashMap<String, Object>> terms, long userIdNo, String termName, String termChk) {
        HashMap<String, Object> term = new HashMap<>();
        term.put("USER_ID_NO", userIdNo);
        term.put("TERM_NAME", termName);
        if (termChk == null)
            term.put("TERM_FLAG", 0);
        else
            term.put("TERM_FLAG", 1);
        terms.add(term);
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

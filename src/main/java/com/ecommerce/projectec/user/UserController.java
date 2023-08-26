package com.ecommerce.projectec.user;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.ecommerce.projectec.term.TermService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

@RequestMapping("user")
@RequiredArgsConstructor
@Controller
public class UserController {
    private final UserService userService;
    private final TermService termService;
    private static final Pattern emailPattern
            = Pattern.compile("^(?=.{1,254}@)[A-Za-z0-9._%+-]{1,64}@[A-Za-z0-9.-]{1,190}\\.[A-Za-z]{2,}$");
    private static final Pattern pwdPattern
            = Pattern.compile("^.*(?=^.{8,20}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$");
    private static final int nameMaxLen = 10;
    private static final int nameMinLen = 2;
    //password encoder
    
    /*
     * @Author 강병관
     *
     * 이메일, 닉네임, 패스워드를 DB에 저장.
     * 유효성 검사를 마친 후 이 메소드를 호출하면 되도록 설계
     * param 개수가 적기 때문에 하드코딩 되어 있는 부분이 많아서 향후 늘어날 것을 대비해 리팩토링 필요.
     */
    /*
    @RequestMapping("signup")
    public String signup(@RequestParam("USER_EMAIL") String userEmail,
                         @RequestParam("USER_NAME") String userName,
                         @RequestParam("USER_PWD") String userPwd,
                         @RequestParam(value = "MARKET_CHK", required = false) String marketChk,
                         @RequestParam(value = "PRIVAC_CHK", required = false) String privacChk,
                         Model model) {

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
    */
    @GetMapping("signin")
    public String signin() {
        return "user/signin";
    }
    @GetMapping("join")
    public String join() {
        return "user/join";
    }

    @GetMapping("joinConfirm")
    public String joinConfirm(@RequestParam("USER_EMAIL") String userEmail, Model model) {
        model.addAttribute("USER_EMAIL", userEmail);
        return "user/joinConfirm";
    }

    @GetMapping("findPw")
    public String findPw() {
        return "user/findPw";
    }

    @PostMapping("login")
    public String login(@RequestParam("USER_EMAIL") String userEmail,
                        @RequestParam("USER_PWD") String userPwd,
                        HttpSession httpSession,
                        Model model) {
        HashMap<String, Object> user = userService.userSelectByEmail(userEmail);

        if (user == null) {
            model.addAttribute("errorMessage", "이메일이 잘못 입력되었습니다.");
            return "user/signin";
        }
        // important: 비밀번호 인코딩 비교로 교체 필요
        if (!(userPwd.equals(user.get("USER_PWD")))) {
            model.addAttribute("errorMessage", "비밀번호가 잘못 입력되었습니다.");
            return "user/signin";
        }

        httpSession.setAttribute("USER_EMAIL" , user.get("USER_EMAIL"));
        httpSession.setAttribute("USER_NAME", user.get("USER_NAME"));
        httpSession.setAttribute("USER_ID_NO", user.get("USER_ID_NO"));
        return "redirect:/";
    }

    @RequestMapping("logout")
    public String logout(HttpSession httpSession) {
        httpSession.invalidate();
        return "redirect:/";
    }

    @ResponseBody
    @RequestMapping("validate-email")
    public boolean validateEmail(@RequestBody HashMap<String, Object> user) {
        String userEmail = (String) user.get("USER_EMAIL");
        return isEmailValid(userEmail);
    }

    @ResponseBody
    @RequestMapping("validate-name")
    public boolean validateName(@RequestBody HashMap<String, Object> user) {
        String userName = (String) user.get("USER_NAME");
        return isNameValid(userName);
    }

    /*
     * @author 강병관
     *
     * 회원가입 검증 메소드
     *
     */
    @ResponseBody
    @RequestMapping("validate-signup")
    public boolean validateSignup(@RequestBody HashMap<String, Object> user) {
        String userEmail = (String) user.get("USER_EMAIL");
        String userName = (String) user.get("USER_NAME");
        String userPwd = (String) user.get("USER_PWD");
        boolean marketChk = (boolean) user.get("MARKET_CHK");
        boolean privacChk = (boolean) user.get("PRIVAC_CHK");

        // 비밀번호 정규식 기준: 숫자, 영문, 특수문자 허용 8~20 글자. 섞어서 안써도 됨.
        if (userName.length() < nameMinLen || userName.length() > nameMaxLen
                || !emailPattern.matcher(userEmail).matches()
                || !pwdPattern.matcher(userPwd).matches()
                || userService.userSelectByEmail(userEmail) != null
                || userService.userSelectByName(userName) != null) {
            return false;
        }

        // 검증 성공 후 USER DB에 데이터 삽입
        try {
            userService.userInsert(user);
            long userIdNo = (long) userService.userSelectByEmail(userEmail).get("USER_ID_NO");
            List<HashMap<String, Object>> terms = new ArrayList<>();
            addTerm(terms, userIdNo, "MARKET_CHK", marketChk);
            addTerm(terms, userIdNo, "PRIVAC_CHK", privacChk);
            System.out.println(terms);
            termService.insertAll(terms);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(user);
        return true;
    }

    /*
     * @author 강병관
     * 비밀번호를 찾기위해 이메일을 받고 유효성 검사한뒤 id값 넘겨주는 코드
     * 보안상 매우 위험하지만 일단 작동이 되는 것을 확인하기 위해 구현함.
     * 향후 보안 대책 필요
     */
    @RequestMapping("find-email")
    public String findEmailToFindPw(@RequestParam("USER_EMAIL") String userEmail,
                                    Model model) {
        HashMap<String, Object> user = userService.userSelectByEmail(userEmail);
        if (user == null)
            return "redirect:/user/findPw";
        long userId = (long) user.get("USER_ID_NO");
        model.addAttribute("USER_ID_NO", userId);
        return "user/changePw";
    }

    /*
     * @author 강병관
     * find-email에서 넘겨받은 id를 가지고 비밀번호 업데이트하는 메소드
     * 그냥 보안 0이다 완전히 위험하다 일단 기능구현을 위해 추가함.
     * 서버에서 세션 만들어서 할당한뒤 쓰는 방법이 있지않을까?
     */
    @RequestMapping("change-pw")
    public String changePw(@RequestParam("USER_ID") long userId,
                           @RequestParam("newpw") String newPw,
                           @RequestParam("newpw2") String newPw2) {
        if (!pwdPattern.matcher(newPw).matches() || !newPw.equals(newPw2)) {
            return "redirect:/user/findPw";
        }
        HashMap<String, Object> user = new HashMap<>();
        user.put("USER_ID_NO", userId);
        user.put("USER_PWD", newPw);
        userService.userUpdateById(user);
        return "redirect:/";
    }

    private void addTerm(List<HashMap<String, Object>> terms, long userIdNo, String termName, boolean termFlag) {
        HashMap<String, Object> term = new HashMap<>();
        term.put("USER_ID_NO", userIdNo);
        term.put("TERM_NAME", termName);
        term.put("TERM_FLAG", termFlag);
        terms.add(term);
    }

    private boolean isEmailValid(String userEmail) {
        return userEmail != null && emailPattern.matcher(userEmail).matches()
                && userService.userSelectByEmail(userEmail) == null;
    }

    private boolean isNameValid(String userName) {
        return userName != null && userName.length() >= 2 && userService.userSelectByName(userName) == null;
    }



}

package vn.Congduongnt.com.web_trac_nghiem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import javax.validation.Valid;
import java.util.List;

@Controller
public class  TrungController {

    @Autowired
    UserService userService;

    @Autowired
    ResultService rService;

    @GetMapping("/signup")
    public String showSignUp(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/signup")
    public String signUp(@Valid @ModelAttribute User user,BindingResult bindingResult,Model model) {
        if (userService.userExists(user.getId())){
            bindingResult.addError(new FieldError("user","id","Tài khoản đã tồn tại."));
        }
        if (userService.userExistss(user.getEmail())){
            bindingResult.addError(new FieldError("user","email","Email đã tồn tại."));
        }
        if (user.getPassWord() != null && user.getRePassWord() != null){
            if (!user.getPassWord().equals(user.getRePassWord())){
                bindingResult.addError(new FieldError("user","rePassWord","Mật khẩu phải trùng nhau"));
            }
        }
        if (bindingResult.hasErrors()){
            return "register";
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        user.setPassWord(passwordEncoder.encode(user.getPassWord()));
        user.setRePassWord(user.getPassWord());
        model.addAttribute("message","Đăng kí thành công !");
        userService.save(user);
        return "login";
    }


    @GetMapping(value = "/view/{userName}")
    public String showMemberView(@PathVariable String userName, Model model){
        User user = userService.findById(userName);
        model.addAttribute("users",user);
        return "trung/view";
    }

    @PostMapping("/edit")
    public String editView(@Valid @ModelAttribute("users") User user,BindingResult bindingResult,Model model){
        if (bindingResult.hasErrors()){
            return "trung/view";
        }else
        userService.save(user);
        model.addAttribute("message","Cập Nhật Thành Công !");
        model.addAttribute("users",user);
        return "trung/view";
    }

    @GetMapping(value = "/editMember/{id}")
    public String showMemberEdit(@PathVariable("id") String userName,Model model){
        User user = userService.findById(userName);
        model.addAttribute("users",user);
        model.addAttribute("userName",user.getId());
        return "trung/editMember";
    }

    @PostMapping("/editMember")
    public String editMember(@Valid @ModelAttribute("users") User users , BindingResult bindingResult, Model model){
        if (bindingResult.hasErrors()){
            model.addAttribute("userName",users.getId());
            return "trung/editMember";
        }else {
            userService.save(users);
            model.addAttribute("message","Cập Nhật Thành Công !");
            model.addAttribute("userName",users.getId());
            return "trung/editMember";
        }
    }

    @GetMapping(value = "/editPass/{id}")
    public String showMemberEditPass(@PathVariable("id") String user,Model model){
        User users = userService.findById(user);
        model.addAttribute("users",users);
        return "trung/editPass";
    }
    @PostMapping("/editPass")
    public String editPass(@Valid @ModelAttribute("users") User users , BindingResult bindingResult, Model model,
                           @RequestParam("oldPass") String oldPass, RedirectAttributes redirectAttributes){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String oldPass1 = passwordEncoder.encode(oldPass);
        String oldPass2 = userService.findByPass(users.getId());
        if (!oldPass1.equals(oldPass2)){
            model.addAttribute("messages","Mật Khẩu Không Đúng !");
        }
        if (users.getPassWord() != null && users.getRePassWord() != null){
            if (!users.getPassWord().equals(users.getRePassWord())){
                bindingResult.addError(new FieldError("users","rePassWord","Mật khẩu phải trùng nhau"));
            }
        }
        if (bindingResult.hasErrors()){
            model.addAttribute("userName",users.getId());
            return "trung/editPass";
        }else {
            users.setPassWord(passwordEncoder.encode(users.getPassWord()));
            users.setRePassWord(passwordEncoder.encode(users.getRePassWord()));
            userService.save(users);
            redirectAttributes.addAttribute("userName", users.getId());
            redirectAttributes.addAttribute("id", users.getId()).
                    addFlashAttribute("message","Cập nhật thành công ! ");
            return "redirect:/editMember/{id}";
//            model.addAttribute("message","Cập Nhật Thành Công !");
//            model.addAttribute("userName",users.getId());
//            return "trung/editPass";
        }
    }
    @Autowired
    private ExamService examService;
    @ModelAttribute("exams")
    public Iterable<Exam> showAll(){
        return examService.findAll();
    }
    @GetMapping(value = "/history/{id}")
    public String showHistory(@PathVariable("id") String id, Model model){
        List<Result> results = rService.findByHistory(id);
        model.addAttribute("results",results);
        model.addAttribute("sum",rService.findSum(id));
        model.addAttribute("avg",rService.findAvg(id));
        return "trung/history";
    }
}

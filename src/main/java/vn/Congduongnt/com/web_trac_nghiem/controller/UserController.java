package vn.Congduongnt.com.web_trac_nghiem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.text.ParseException;

@Controller
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/user/list")
    public String showList(Model model, @PageableDefault(value = 5) Pageable pageable) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            String userName = ((UserDetails) principal).getUsername();
            model.addAttribute("userName", userName);
        }
        Page<User> users = userService.findAll(pageable);
        model.addAttribute("message",null);
        model.addAttribute("users", users);
        model.addAttribute("us", "admin");
        return "user/list";
    }

    @GetMapping("/user/create")
    public String showCreateForm(Model model) {
        model.addAttribute("user", new User());
        return "user/create";
    }

    @PostMapping("/user/create")
    public String saveUser(@Validated @ModelAttribute("user") User user, BindingResult bindingResult) throws ParseException {
        if (bindingResult.hasFieldErrors()) {
            return "user/create";
        } else {
            userService.save(user);
            return "redirect:/user/list";
        }
    }

    @GetMapping("/user/editMember/{id}")
    public String editMember(@PathVariable("id") String id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "user/editMember";
    }

    @PostMapping("/user/update")
    public String update(@Validated User user, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasFieldErrors()) {
            return "user/editMember";
        } else {
            User user1 = userService.findById(user.getId());
            user1.setFullName(user.getFullName());
            user1.setEmail(user.getEmail());
            user1.setAddress(user.getAddress());
            user1.setPhoneNumber(user.getPhoneNumber());
            userService.save(user1);
            redirectAttributes.addFlashAttribute("message", "Cập Nhật Thành Công !");
            return "redirect:/user/list";
        }
    }

    @GetMapping("/user/delete/{id}")
    public String delete(@PathVariable("id") String id) {
        User user = userService.findById(id);
        userService.delete(user);
        return "redirect:/user/list";
    }

    @GetMapping("/user/editPass/{id}")
    public String editPass(@PathVariable("id") String id, Model model) {
        User user = userService.findById(id);
        model.addAttribute("user", user);
        return "user/editPass";
    }

    @PostMapping("/user/updatePass")
    public String updatePass(@Valid @ModelAttribute User user, BindingResult bindingResult, Model model,
                             @RequestParam("oldPass") String oldPass,RedirectAttributes redirectAttributes) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String oldPass1 = passwordEncoder.encode(oldPass);
        String oldPass2 = userService.findByPass(user.getId());
        if (!oldPass1.equals(oldPass2)){
            model.addAttribute("messages","Mật Khẩu Không Đúng !");
        }
        if (user.getPassWord() != null && user.getRePassWord() != null) {
            if (!user.getPassWord().equals(user.getRePassWord())) {
                bindingResult.addError(new FieldError("user", "rePassWord", "Mật khẩu phải trùng nhau"));
            }
        }
        if (bindingResult.hasFieldErrors()) {
            return "user/editPass";
        }
        User user1 = userService.findById(user.getId());
        user1.setPassWord(passwordEncoder.encode(user.getPassWord()));
        user1.setRePassWord(passwordEncoder.encode(user.getRePassWord()));
        userService.save(user1);
        redirectAttributes.addFlashAttribute("message", "Cập Nhật Thành Công !");
        return "redirect:/user/list";
    }
}

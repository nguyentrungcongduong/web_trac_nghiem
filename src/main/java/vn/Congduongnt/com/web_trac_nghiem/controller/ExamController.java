package vn.Congduongnt.com.web_trac_nghiem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class ExamController {
    @Autowired
    UserService userService;

    @Autowired
    ResultService rService;

    @Autowired
    private ExamService examService;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private ClassesService classesService;

    @Autowired
    private QuestionService questionService;

    @ModelAttribute("subjects")
    public Iterable<Subject> subjects() {
        return subjectService.findAll();
    }

    @ModelAttribute("classes")
    public Iterable<Classes> classes() {
        return classesService.findAll();
    }

    @ModelAttribute("questions")
    public Iterable<Question> questions() {
        return questionService.findAll();
    }


    @GetMapping("/exam/list")
    public String showList(@RequestParam("subjectId") Optional<Integer> subjectId,
                           @RequestParam("keyword") Optional<String> name,
                           Model model, @PageableDefault(value = 5) Pageable pageable) {
        Page<Exam> exams;
        model.addAttribute("subjects", subjectService.findAll());
        if (!name.isPresent()) {
            if (subjectId.isPresent()) {
                exams = examService.findAllBySubject(subjectId.get(), pageable);
                model.addAttribute("exams", exams);
                model.addAttribute("subjectId", subjectId.get());
                return "exam/listExam";
            }
            exams = examService.findAll(pageable);
        } else {
            exams = examService.findAllByNameExamContaining(name.get(), pageable);
        }

        model.addAttribute("exams", exams);
        return "exam/listExam";
    }

    @GetMapping("/exam/create")
    public String showCreateForm(@RequestParam(value = "subjectId", required = false) Optional<Integer> subjectId,
                                  Model model,
                                 @PageableDefault(value = 100) Pageable pageable) {
        if (subjectId.isPresent()) {
            Page<Question> questions1 = questionService.findAllBySubject(subjectId, pageable);
            model.addAttribute("question1", questions1);
            model.addAttribute("exam", new Exam());
            model.addAttribute("subjectId", subjectId.get());
            return "exam/createExam";
        }
        model.addAttribute("subjects", subjectService.findAll());
        model.addAttribute("exam", new Exam());
        return "exam/createExam";

    }

    @PostMapping("/exam/create")
    public String saveExam(@Validated @ModelAttribute("exam") Exam exam, BindingResult bindingResult, RedirectAttributes re) {
        if (bindingResult.hasFieldErrors()) {
            return "exam/createExam";
        } else {
            re.addFlashAttribute("message", "Tạo đề thi thành công!");
            examService.save(exam);
            return "redirect:/exam/list";
        }
    }

    @GetMapping("/exam/editExam/{id}")
    public String editMember(@PathVariable("id") Integer id, Model model) {
        Exam exam = examService.findById(id);
        model.addAttribute("exam", exam);
        return "exam/editExam";
    }

    @PostMapping("/exam/update")
    public String update(@Validated Exam exam, BindingResult bindingResult, RedirectAttributes re) {
        if (bindingResult.hasFieldErrors()) {
            return "exam/editExam";
        } else {
            re.addFlashAttribute("message", "Chỉnh sửa đề thi thành công!");
            examService.save(exam);
            return "redirect:/exam/list";
        }
    }

    @GetMapping("/exam/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        Exam exam = examService.findById(id);
        examService.delete(exam);
        return "redirect:/exam/list";
    }

    @GetMapping("/listExamSubject/{id}")
    public String listExamSubject(@PathVariable int id, Model model, @PageableDefault(value = 10) Pageable pageable) {
        Page<Exam> exams;
        String userName = "null";
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            userName = ((UserDetails) principal).getUsername();
            model.addAttribute("userName", userName);
        }
        String newUser = userService.findByNewUser();
        List<Result> results = null;
        int normal = 0;
        if (userName.equals("null")) {
            normal++;
        } else {
            results = rService.findByHistory(userName);
        }
        exams = examService.findAllBySubject(id, pageable);
        List<Integer> arrayNumber = new ArrayList<>();
        int dem = 0;
        if (results != null) {
            for (Exam exam : exams) {
                for (Result result : results) {
                    if (result.getQuestions() == null) {
                        continue;
                    }
                    if (exam.getId() == result.getQuestions().getId()) {
                        dem++;
                    }
                }
                arrayNumber.add(dem);
                dem = 0;

            }
        }
        List<Result> sList = rService.getTopFive();
        model.addAttribute("sList", sList);
        int total = userService.findByTotalUser();
        model.addAttribute("total", total);
        model.addAttribute("newUser", newUser);

        model.addAttribute("exams", exams);
        model.addAttribute("normal", normal);
        model.addAttribute("arrayNumber", arrayNumber);
        model.addAttribute("listIdExam", results);
        return "listExamSubject";
    }
}

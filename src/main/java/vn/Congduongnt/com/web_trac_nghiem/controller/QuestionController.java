package vn.Congduongnt.com.web_trac_nghiem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import javax.validation.Valid;
import java.util.Optional;


@Controller
public class QuestionController {
    @Autowired
    private SubjectService subjectService;
    @Autowired
    private QuestionService questionService;

    @Autowired
    private ExamService examService;

    @ModelAttribute("exams")
    public Iterable<Exam> exams(){
        return examService.findAll();
    }

    @GetMapping("/question/list")
    public String showList(@RequestParam("examId") Optional<Integer> examId, Model model, @PageableDefault(value = 5) Pageable pageable){
        Page<Question> questions;
        model.addAttribute("exams", examService.findAll());
        if (examId.isPresent()){
            questions = questionService.findAllByExams(examId.get(), pageable);
            model.addAttribute("questions", questions);
            model.addAttribute("examId", examId.get());
            return "question/listQuestion";
        }
        questions = questionService.findAll(pageable);
        model.addAttribute("questions", questions);
        return "question/listQuestion";
    }

    @GetMapping("/question/editQuest/{id}")
    public String editMember(@PathVariable("id") Integer id, Model model)  {
        Question question = questionService.findById(id);
        model.addAttribute("question", question);
        return "question/editQuestion";
    }

    @PostMapping("/question/update")
    public String update(Question question){
        questionService.save(question);
        return "redirect:/question/list";
    }

    @GetMapping("/question/delete/{id}")
    public String delete(@PathVariable("id") Integer id){
        Question question = questionService.findById(id);
        questionService.delete(question);
        return "redirect:/question/list";
    }
    @GetMapping("/question/create")
    public String create(Model model) {
//        ModelAndView modelAndView = new ModelAndView("question/createQuestion");
        model.addAttribute("question", new Question());
        model.addAttribute("subjects", subjectService.findAll());
        return "question/createQuestion";
    }

    @PostMapping("/question/create")
    public String save(@Valid @ModelAttribute Question question, BindingResult bindingResult, Model model) {
//        if (questionService.existById(id) {
//            bindingResult.addError(new FieldError("question", "id", "Cau hoi đã tồn tại"));
//        }

        if (bindingResult.hasFieldErrors()) {
            return "question/createQuestion";
        }
        questionService.save(question);
        return "redirect:/question/list";
    }
}


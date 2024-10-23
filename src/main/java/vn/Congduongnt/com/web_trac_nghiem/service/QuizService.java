package vn.Congduongnt.com.web_trac_nghiem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class QuizService {

    @Autowired
    Question question;
    @Autowired
    QuestionForm qForm;
    @Autowired
    QuestionRepository qRepo;
    @Autowired
    Result result;
    @Autowired
    ResultRepository rRepo;

    public QuestionForm getQuestions() {
        List<Question> allQues = qRepo.findAll();
        List<Question> qList = new ArrayList<Question>();

        Random random = new Random();

        for (int i = 0; i < 10; i++) {
            int rand = random.nextInt(allQues.size());
            qList.add(allQues.get(rand));
            allQues.remove(rand);
        }

        qForm.setQuestions(qList);

        return qForm;
    }

    public QuestionForm getQuestionss(int id) {
        List<Question> allQues = qRepo.findAllId(id);
        List<Question> qList = new ArrayList<Question>();
        int fag = 10;
        Random random = new Random();
        if (allQues.size() < 10) {
            fag = allQues.size();
        }
        for (int i = 0; i < fag; i++) {
            int rand = random.nextInt(allQues.size());
            qList.add(allQues.get(rand));
            allQues.remove(rand);
        }

        qForm.setQuestions(qList);

        return qForm;
    }

    public int getResult(QuestionForm qForm) {
        int correct = 0;

        for (Question q : qForm.getQuestions())
            if (q.getAns() == q.getChose())
                correct++;
        return correct;
    }

    public void saveScore(Result result) {

//        Result saveResult = new Result();
//        saveResult.setUsername(result.getUsername());
//        saveResult.setTotalCorrect(result.getTotalCorrect());
//        rRepo.save(saveResult);
        result.getUsers().setResult(null);
        result.getQuestions().setUsers(null);
        rRepo.save(result);

    }
    public void saveScore2(Result result) {
//        Result saveResult = new Result();
//        saveResult.setUsername(result.getUsername());
//        saveResult.setTotalCorrect(result.getTotalCorrect());
//        rRepo.save(saveResult);
        //result.getQuestions().setUsers(null);
        result.getUsers().setResult(null);
        rRepo.save(result);

    }

    public List<Result> getTopScore() {
        List<Result> sList = rRepo.findAll(Sort.by(Sort.Direction.DESC, "totalCorrect"));
        return sList;
    }
    public List<Result> getTopScoreByExam(int idExam) {
        List<Result> sList = rRepo.findTopByExam(idExam);
        return sList;
    }
}

package com.project.decider.record;

import com.project.decider.dao.Dao;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by macos on 31.05.16.
 */

@Service("quizService")
@Transactional
public class QuizService {

    @Autowired
    private Dao dao;

    public Quiz saveQuiz(Quiz quiz) {
        return dao.save(quiz);
    }

    public List<Quiz> getAllQuizzesByUserName(String userName){
        DetachedCriteria query = DetachedCriteria.forClass(Quiz.class);
        query.add(Restrictions.eq("userName", userName));

        return dao.getAllBy(query);
    }

    public List<Quiz> getQuizByNameRecordId(String userName, Long recordId){
        DetachedCriteria query = DetachedCriteria.forClass(Quiz.class);
        query.add(Restrictions.and(
                Restrictions.eq("userName", userName),
                Restrictions.eq("recordId", recordId)
                )
        );

        return dao.getAllBy(query);
    }

    public List<Quiz> getQuizzesRangeByName(String userName, Long fromId){
        DetachedCriteria query = DetachedCriteria.forClass(Quiz.class);
        Criterion criterion = Restrictions.and(
                Restrictions.eq("userName", userName),
                Restrictions.le("recordId", fromId));

        return dao.getByCriterion(Quiz.class, criterion, 9);
    }

    public List<Quiz> getQuizzesRange(Long fromId, Long toId){
        DetachedCriteria query = DetachedCriteria.forClass(Quiz.class);
        query.add(Restrictions.and(
                Restrictions.le("recordId", fromId),
                Restrictions.ge("recordId", toId)
        ));

        return dao.getAllBy(query);
    }

    public List<Quiz> getQuizByRecordId(Long recordId){
        DetachedCriteria query = DetachedCriteria.forClass(Quiz.class);
        query.add(Restrictions.eq("recordId", recordId));

        return dao.getAllBy(query);
    }

    public void setDao(Dao dao) {
        this.dao = dao;
    }
}

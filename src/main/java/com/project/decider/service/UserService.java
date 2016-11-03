package com.project.decider.service;

import com.project.decider.dao.Dao;
import com.project.decider.model.User;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Lobster on 30.04.16.
 */

@Service("userService")
@Transactional
public class UserService{

    @Autowired
    private Dao dao;

    public User saveUser(User user) {
       return dao.save(user);
    }

    public User getUser(String userName, String userPassword){
        DetachedCriteria query = DetachedCriteria.forClass(User.class);
        query.add(Restrictions.and(
                Restrictions.eq("username", userName),
                Restrictions.eq("password",userPassword))
        );

        return dao.getBy(query);
    }

    public User getUserByName(String userName){
        DetachedCriteria query = DetachedCriteria.forClass(User.class);
        query.add(
                Restrictions.eq("username", userName)
        );

        return dao.getBy(query);
    }

    public User getUserByVerificationCode(Integer verificationCode){
        DetachedCriteria query = DetachedCriteria.forClass(User.class);
        query.add(
                Restrictions.eq("verificationCode", verificationCode)
        );

        return dao.getBy(query);
    }

    public User getUserByEmail(String email){
        DetachedCriteria query = DetachedCriteria.forClass(User.class);
        query.add(
                Restrictions.eq("email", email)
        );

        return dao.getBy(query);
    }

    public User setBackground(String userName, String background){
        DetachedCriteria query = DetachedCriteria.forClass(User.class);
        query.add(Restrictions.eq("username", userName));
        User user = dao.getBy(query);
        if (user==null){
            System.out.println("NULL");
        } else {
            user.setBackground(background);
        }
        return dao.save(user);
    }

    public User setAvatar(String userName, String avatar){
        DetachedCriteria query = DetachedCriteria.forClass(User.class);
        query.add(Restrictions.eq("username", userName));
        User user = dao.getBy(query);
        user.setAvatar(avatar);
        return dao.save(user);
    }

    public List<User> getUsersByTemplate(String template, int maxCountResult){
        return dao.getByCriterionWithOrderBy(User.class, Restrictions.like("username", "%" + template + "%"), maxCountResult, "username");
    }

    public List<User> getUsersByTemplateFromUsername(String template, String fromUsername, int maxCountResult) {
        Criterion criterion = Restrictions.and(
                Restrictions.like("username", "%" + template + "%"),
                Restrictions.lt("username", fromUsername)
        );
        return dao.getByCriterionWithOrderBy(User.class, criterion, maxCountResult, "username");
    }

    public void setDao(Dao dao) {
        this.dao = dao;
    }
}

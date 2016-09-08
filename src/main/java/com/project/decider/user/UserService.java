package com.project.decider.user;

import com.project.decider.dao.Dao;
import org.hibernate.Criteria;
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
                Restrictions.eq("name", userName),
                Restrictions.eq("password",userPassword))
        );

        return dao.getBy(query);
    }

    public User getUserByName(String userName){
        DetachedCriteria query = DetachedCriteria.forClass(User.class);
        query.add(
                Restrictions.eq("name", userName)
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
        query.add(Restrictions.eq("name", userName));
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
        query.add(Restrictions.eq("name", userName));
        User user = dao.getBy(query);
        user.setAvatar(avatar);
        return dao.save(user);
    }

    public List<User> getUsersByTemplate(String template, int maxCountResult){
        return dao.getByCriterion(User.class, Restrictions.like("name", "%"+template+"%"), maxCountResult);
    }

    public List<User> getUsersByTemplateFromId(String template, Long userId, int maxCountResult){
        Criterion criterion = Restrictions.and(
                Restrictions.like("name", "%"+template+"%"),
                Restrictions.gt("id", userId)
        );
        return dao.getByCriterion(User.class, criterion, maxCountResult);
    }

    public void setDao(Dao dao) {
        this.dao = dao;
    }
}

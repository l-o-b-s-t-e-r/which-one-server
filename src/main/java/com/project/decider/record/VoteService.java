package com.project.decider.record;

import com.project.decider.dao.Dao;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by macos on 22.05.16.
 */

@Service("voteService")
@Transactional
public class VoteService {

    @Autowired
    private Dao dao;

    public Vote createRecord(Vote vote) {
        return dao.save(vote);
    }

    public List<Vote> getAllRecordsByRecordIdOption(Long recordId, String option){
        DetachedCriteria query = DetachedCriteria.forClass(Vote.class);
        query.add(Restrictions.and(
                Restrictions.eq("recordId", recordId),
                Restrictions.eq("pointName", option))
        );

        return dao.getAllBy(query);
    }

    public List<Record> getAllVotesByRecordId(Long recordId){
        DetachedCriteria query = DetachedCriteria.forClass(Vote.class);
        query.add(Restrictions.eq("recordId", recordId));

        return dao.getAllBy(query);
    }

    public void setDao(Dao dao) {
        this.dao = dao;
    }
}

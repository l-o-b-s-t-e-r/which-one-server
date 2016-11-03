package com.project.decider.service;

import com.project.decider.dao.Dao;
import com.project.decider.model.Vote;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by macos on 22.05.16.
 */

@Service("voteService")
@Transactional
public class VoteService {

    @Autowired
    private Dao dao;

    public Vote getVoteByRecordIdUsername(Long recordId, String username) {
        DetachedCriteria query = DetachedCriteria.forClass(Vote.class)
                .add(Restrictions.and(
                        Restrictions.eq("voteId.option.optionId.record.recordId", recordId),
                        Restrictions.eq("voteId.user.username", username)
                ));

        return dao.getBy(query);
    }

    public Vote saveVote(Vote vote) {
        return dao.save(vote);
    }

    public void setDao(Dao dao) {
        this.dao = dao;
    }
}

package com.project.decider.service;

import com.project.decider.dao.Dao;
import com.project.decider.model.Option;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Lobster on 31.10.16.
 */

@Service("optionService")
@Transactional
public class OptionService {

    @Autowired
    private Dao dao;

    public Option getOptionByNameRecordId(String optionName, Long recordId) {
        DetachedCriteria query = DetachedCriteria.forClass(Option.class)
                .add(Restrictions.and(
                        Restrictions.eq("optionId.optionName", optionName),
                        Restrictions.eq("optionId.record.recordId", recordId)
                ));

        return dao.getBy(query);
    }

    public Option increaseVoteCount(Option option) {
        option.setVoteCount(option.getVoteCount() + 1);
        return dao.save(option);
    }

    public void setDao(Dao dao) {
        this.dao = dao;
    }
}

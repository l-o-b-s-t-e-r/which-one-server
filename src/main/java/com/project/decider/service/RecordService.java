package com.project.decider.service;

import com.project.decider.dao.Dao;
import com.project.decider.model.Record;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Lobster on 22.05.16.
 */

@Service("recordService")
@Transactional
public class RecordService {

    @Autowired
    private Dao dao;

    public Record saveRecord(Record record) {
        return dao.save(record);
    }

    public Record getRecordById(Long recordId) {
        DetachedCriteria query = DetachedCriteria.forClass(Record.class);
        query.add(Restrictions.eq("recordId", recordId));

        return dao.getBy(query);
    }

    public Long getLastRecordId() {
        DetachedCriteria query = DetachedCriteria.forClass(Record.class)
                .setProjection(Projections.max("recordId"));

        return dao.getBy(query);
    }

    public Long getLastRecordIdByName(String username) {
        DetachedCriteria query = DetachedCriteria.forClass(Record.class)
                .setProjection(Projections.max("recordId"))
                .add(Restrictions.eq("username", username));

        return dao.getBy(query);
    }

    public List<Record> getRecordsRange(Long fromId, Integer count) {
        Criterion criterion = Restrictions.lt("recordId", fromId);

        return dao.getByCriterionWithOrderBy(Record.class, criterion, count, "recordId");
    }

    public List<Record> getRecordsRangeByName(String username, Long fromId, Integer count, Boolean includeFromId) {
        Criterion criterion;
        if (includeFromId) {
            criterion = Restrictions.and(
                    Restrictions.eq("username", username),
                    Restrictions.le("recordId", fromId));
        } else {
            criterion = Restrictions.and(
                    Restrictions.eq("username", username),
                    Restrictions.lt("recordId", fromId));
        }

        return dao.getByCriterionWithOrderBy(Record.class, criterion, count, "recordId");
    }

    public void setDao(Dao dao) {
        this.dao = dao;
    }
}

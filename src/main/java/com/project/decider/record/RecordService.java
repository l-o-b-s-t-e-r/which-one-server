package com.project.decider.record;

import com.project.decider.dao.Dao;
import org.hibernate.Criteria;
import org.hibernate.criterion.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by macos on 22.05.16.
 */

@Service("recordService")
@Transactional
public class RecordService {

    @Autowired
    private Dao dao;

    public Record saveRecord(Record record) {
        return dao.save(record);
    }

    public List<Record> getAllRecordsByUserName(String userName){
        DetachedCriteria query = DetachedCriteria.forClass(Record.class);
        query.add(Restrictions.eq("userName", userName));

        return dao.getAllBy(query);
    }

    public List<Record> getRecordById(Long recordId){
        DetachedCriteria query = DetachedCriteria.forClass(Record.class);
        query.add(Restrictions.eq("recordId", recordId));

        return dao.getAllBy(query);
    }

    public List<Record> getRecordByNameRecordId(String userName, Long recordId){
        DetachedCriteria query = DetachedCriteria.forClass(Record.class);
        query.add(Restrictions.and(
                            Restrictions.eq("userName", userName),
                            Restrictions.eq("recordId", recordId)
                                )
                );

        return dao.getAllBy(query);
    }

    public List<Record> getRecordsRangeByName(String userName, Long fromId){
        DetachedCriteria query = DetachedCriteria.forClass(Record.class);

        Criterion criterion = Restrictions.and(
                                Restrictions.eq("userName", userName),
                                Restrictions.le("recordId", fromId));


        return dao.getByCriterion(Record.class, criterion, 9);
    }

    public Long getNextRecordId(String userName, Long id){
        DetachedCriteria query = DetachedCriteria.forClass(Record.class)
                .setProjection(Projections.max("recordId"))
                .add(Restrictions.and(
                        Restrictions.eq("userName", userName),
                        Restrictions.lt("recordId", id)
                ));

        return dao.getBy(query);
    }

    public List<Record> getRecordsRange(Long fromId, Long toId){
        DetachedCriteria query = DetachedCriteria.forClass(Record.class);
        query.add(Restrictions.and(
                Restrictions.le("recordId", fromId),
                Restrictions.ge("recordId", toId)
        ));

        return dao.getAllBy(query);
    }

    public Long getLastRecordId(){
        DetachedCriteria query = DetachedCriteria.forClass(Record.class)
                .setProjection(Projections.max("recordId"));

        return dao.getBy(query);
    }

    public Long getLastRecordByName(String userName){
        DetachedCriteria query = DetachedCriteria.forClass(Record.class)
                .setProjection(Projections.max("recordId"))
                .add(Restrictions.eq("userName", userName));

        return dao.getBy(query);
    }

    public void setDao(Dao dao) {
        this.dao = dao;
    }
}

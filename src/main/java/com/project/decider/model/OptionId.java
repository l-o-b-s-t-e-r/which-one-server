package com.project.decider.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * Created by Lobster on 29.10.16.
 */
@Embeddable
public class OptionId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "recordId")
    private Record record;

    @Column
    private String optionName;

    public OptionId() {

    }

    public OptionId(String optionName, Record record) {
        this.optionName = optionName;
        this.record = record;
    }

    public Record getRecord() {
        return record;
    }

    public void setRecord(Record record) {
        this.record = record;
    }

    public String getOptionName() {
        return optionName;
    }

    public void setOptionName(String optionName) {
        this.optionName = optionName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OptionId optionId = (OptionId) o;

        if (record != null ? !record.equals(optionId.record) : optionId.record != null) return false;
        return optionName != null ? optionName.equals(optionId.optionName) : optionId.optionName == null;

    }

    @Override
    public int hashCode() {
        int result = record != null ? record.hashCode() : 0;
        result = 31 * result + (optionName != null ? optionName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "OptionId{" +
                "optionName='" + optionName + '\'' +
                '}';
    }
}

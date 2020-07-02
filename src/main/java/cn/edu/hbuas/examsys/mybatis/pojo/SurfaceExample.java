package cn.edu.hbuas.examsys.mybatis.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class SurfaceExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public SurfaceExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        protected void addCriterionForJDBCDate(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value.getTime()), property);
        }

        protected void addCriterionForJDBCDate(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Date> dateList = new ArrayList<java.sql.Date>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                dateList.add(new java.sql.Date(iter.next().getTime()));
            }
            addCriterion(condition, dateList, property);
        }

        protected void addCriterionForJDBCDate(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Date(value1.getTime()), new java.sql.Date(value2.getTime()), property);
        }

        protected void addCriterionForJDBCTime(String condition, Date value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Time(value.getTime()), property);
        }

        protected void addCriterionForJDBCTime(String condition, List<Date> values, String property) {
            if (values == null || values.size() == 0) {
                throw new RuntimeException("Value list for " + property + " cannot be null or empty");
            }
            List<java.sql.Time> timeList = new ArrayList<java.sql.Time>();
            Iterator<Date> iter = values.iterator();
            while (iter.hasNext()) {
                timeList.add(new java.sql.Time(iter.next().getTime()));
            }
            addCriterion(condition, timeList, property);
        }

        protected void addCriterionForJDBCTime(String condition, Date value1, Date value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            addCriterion(condition, new java.sql.Time(value1.getTime()), new java.sql.Time(value2.getTime()), property);
        }

        public Criteria andSuidIsNull() {
            addCriterion("suid is null");
            return (Criteria) this;
        }

        public Criteria andSuidIsNotNull() {
            addCriterion("suid is not null");
            return (Criteria) this;
        }

        public Criteria andSuidEqualTo(Integer value) {
            addCriterion("suid =", value, "suid");
            return (Criteria) this;
        }

        public Criteria andSuidNotEqualTo(Integer value) {
            addCriterion("suid <>", value, "suid");
            return (Criteria) this;
        }

        public Criteria andSuidGreaterThan(Integer value) {
            addCriterion("suid >", value, "suid");
            return (Criteria) this;
        }

        public Criteria andSuidGreaterThanOrEqualTo(Integer value) {
            addCriterion("suid >=", value, "suid");
            return (Criteria) this;
        }

        public Criteria andSuidLessThan(Integer value) {
            addCriterion("suid <", value, "suid");
            return (Criteria) this;
        }

        public Criteria andSuidLessThanOrEqualTo(Integer value) {
            addCriterion("suid <=", value, "suid");
            return (Criteria) this;
        }

        public Criteria andSuidIn(List<Integer> values) {
            addCriterion("suid in", values, "suid");
            return (Criteria) this;
        }

        public Criteria andSuidNotIn(List<Integer> values) {
            addCriterion("suid not in", values, "suid");
            return (Criteria) this;
        }

        public Criteria andSuidBetween(Integer value1, Integer value2) {
            addCriterion("suid between", value1, value2, "suid");
            return (Criteria) this;
        }

        public Criteria andSuidNotBetween(Integer value1, Integer value2) {
            addCriterion("suid not between", value1, value2, "suid");
            return (Criteria) this;
        }

        public Criteria andTeacherIsNull() {
            addCriterion("teacher is null");
            return (Criteria) this;
        }

        public Criteria andTeacherIsNotNull() {
            addCriterion("teacher is not null");
            return (Criteria) this;
        }

        public Criteria andTeacherEqualTo(String value) {
            addCriterion("teacher =", value, "teacher");
            return (Criteria) this;
        }

        public Criteria andTeacherNotEqualTo(String value) {
            addCriterion("teacher <>", value, "teacher");
            return (Criteria) this;
        }

        public Criteria andTeacherGreaterThan(String value) {
            addCriterion("teacher >", value, "teacher");
            return (Criteria) this;
        }

        public Criteria andTeacherGreaterThanOrEqualTo(String value) {
            addCriterion("teacher >=", value, "teacher");
            return (Criteria) this;
        }

        public Criteria andTeacherLessThan(String value) {
            addCriterion("teacher <", value, "teacher");
            return (Criteria) this;
        }

        public Criteria andTeacherLessThanOrEqualTo(String value) {
            addCriterion("teacher <=", value, "teacher");
            return (Criteria) this;
        }

        public Criteria andTeacherLike(String value) {
            addCriterion("teacher like", value, "teacher");
            return (Criteria) this;
        }

        public Criteria andTeacherNotLike(String value) {
            addCriterion("teacher not like", value, "teacher");
            return (Criteria) this;
        }

        public Criteria andTeacherIn(List<String> values) {
            addCriterion("teacher in", values, "teacher");
            return (Criteria) this;
        }

        public Criteria andTeacherNotIn(List<String> values) {
            addCriterion("teacher not in", values, "teacher");
            return (Criteria) this;
        }

        public Criteria andTeacherBetween(String value1, String value2) {
            addCriterion("teacher between", value1, value2, "teacher");
            return (Criteria) this;
        }

        public Criteria andTeacherNotBetween(String value1, String value2) {
            addCriterion("teacher not between", value1, value2, "teacher");
            return (Criteria) this;
        }

        public Criteria andEidIsNull() {
            addCriterion("eid is null");
            return (Criteria) this;
        }

        public Criteria andEidIsNotNull() {
            addCriterion("eid is not null");
            return (Criteria) this;
        }

        public Criteria andEidEqualTo(Integer value) {
            addCriterion("eid =", value, "eid");
            return (Criteria) this;
        }

        public Criteria andEidNotEqualTo(Integer value) {
            addCriterion("eid <>", value, "eid");
            return (Criteria) this;
        }

        public Criteria andEidGreaterThan(Integer value) {
            addCriterion("eid >", value, "eid");
            return (Criteria) this;
        }

        public Criteria andEidGreaterThanOrEqualTo(Integer value) {
            addCriterion("eid >=", value, "eid");
            return (Criteria) this;
        }

        public Criteria andEidLessThan(Integer value) {
            addCriterion("eid <", value, "eid");
            return (Criteria) this;
        }

        public Criteria andEidLessThanOrEqualTo(Integer value) {
            addCriterion("eid <=", value, "eid");
            return (Criteria) this;
        }

        public Criteria andEidIn(List<Integer> values) {
            addCriterion("eid in", values, "eid");
            return (Criteria) this;
        }

        public Criteria andEidNotIn(List<Integer> values) {
            addCriterion("eid not in", values, "eid");
            return (Criteria) this;
        }

        public Criteria andEidBetween(Integer value1, Integer value2) {
            addCriterion("eid between", value1, value2, "eid");
            return (Criteria) this;
        }

        public Criteria andEidNotBetween(Integer value1, Integer value2) {
            addCriterion("eid not between", value1, value2, "eid");
            return (Criteria) this;
        }

        public Criteria andTidIsNull() {
            addCriterion("tid is null");
            return (Criteria) this;
        }

        public Criteria andTidIsNotNull() {
            addCriterion("tid is not null");
            return (Criteria) this;
        }

        public Criteria andTidEqualTo(Integer value) {
            addCriterion("tid =", value, "tid");
            return (Criteria) this;
        }

        public Criteria andTidNotEqualTo(Integer value) {
            addCriterion("tid <>", value, "tid");
            return (Criteria) this;
        }

        public Criteria andTidGreaterThan(Integer value) {
            addCriterion("tid >", value, "tid");
            return (Criteria) this;
        }

        public Criteria andTidGreaterThanOrEqualTo(Integer value) {
            addCriterion("tid >=", value, "tid");
            return (Criteria) this;
        }

        public Criteria andTidLessThan(Integer value) {
            addCriterion("tid <", value, "tid");
            return (Criteria) this;
        }

        public Criteria andTidLessThanOrEqualTo(Integer value) {
            addCriterion("tid <=", value, "tid");
            return (Criteria) this;
        }

        public Criteria andTidIn(List<Integer> values) {
            addCriterion("tid in", values, "tid");
            return (Criteria) this;
        }

        public Criteria andTidNotIn(List<Integer> values) {
            addCriterion("tid not in", values, "tid");
            return (Criteria) this;
        }

        public Criteria andTidBetween(Integer value1, Integer value2) {
            addCriterion("tid between", value1, value2, "tid");
            return (Criteria) this;
        }

        public Criteria andTidNotBetween(Integer value1, Integer value2) {
            addCriterion("tid not between", value1, value2, "tid");
            return (Criteria) this;
        }

        public Criteria andRidIsNull() {
            addCriterion("rid is null");
            return (Criteria) this;
        }

        public Criteria andRidIsNotNull() {
            addCriterion("rid is not null");
            return (Criteria) this;
        }

        public Criteria andRidEqualTo(Integer value) {
            addCriterion("rid =", value, "rid");
            return (Criteria) this;
        }

        public Criteria andRidNotEqualTo(Integer value) {
            addCriterion("rid <>", value, "rid");
            return (Criteria) this;
        }

        public Criteria andRidGreaterThan(Integer value) {
            addCriterion("rid >", value, "rid");
            return (Criteria) this;
        }

        public Criteria andRidGreaterThanOrEqualTo(Integer value) {
            addCriterion("rid >=", value, "rid");
            return (Criteria) this;
        }

        public Criteria andRidLessThan(Integer value) {
            addCriterion("rid <", value, "rid");
            return (Criteria) this;
        }

        public Criteria andRidLessThanOrEqualTo(Integer value) {
            addCriterion("rid <=", value, "rid");
            return (Criteria) this;
        }

        public Criteria andRidIn(List<Integer> values) {
            addCriterion("rid in", values, "rid");
            return (Criteria) this;
        }

        public Criteria andRidNotIn(List<Integer> values) {
            addCriterion("rid not in", values, "rid");
            return (Criteria) this;
        }

        public Criteria andRidBetween(Integer value1, Integer value2) {
            addCriterion("rid between", value1, value2, "rid");
            return (Criteria) this;
        }

        public Criteria andRidNotBetween(Integer value1, Integer value2) {
            addCriterion("rid not between", value1, value2, "rid");
            return (Criteria) this;
        }

        public Criteria andTesttimeIsNull() {
            addCriterion("testtime is null");
            return (Criteria) this;
        }

        public Criteria andTesttimeIsNotNull() {
            addCriterion("testtime is not null");
            return (Criteria) this;
        }

        public Criteria andTesttimeEqualTo(Date value) {
            addCriterionForJDBCDate("testtime =", value, "testtime");
            return (Criteria) this;
        }

        public Criteria andTesttimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("testtime <>", value, "testtime");
            return (Criteria) this;
        }

        public Criteria andTesttimeGreaterThan(Date value) {
            addCriterionForJDBCDate("testtime >", value, "testtime");
            return (Criteria) this;
        }

        public Criteria andTesttimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("testtime >=", value, "testtime");
            return (Criteria) this;
        }

        public Criteria andTesttimeLessThan(Date value) {
            addCriterionForJDBCDate("testtime <", value, "testtime");
            return (Criteria) this;
        }

        public Criteria andTesttimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("testtime <=", value, "testtime");
            return (Criteria) this;
        }

        public Criteria andTesttimeIn(List<Date> values) {
            addCriterionForJDBCDate("testtime in", values, "testtime");
            return (Criteria) this;
        }

        public Criteria andTesttimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("testtime not in", values, "testtime");
            return (Criteria) this;
        }

        public Criteria andTesttimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("testtime between", value1, value2, "testtime");
            return (Criteria) this;
        }

        public Criteria andTesttimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("testtime not between", value1, value2, "testtime");
            return (Criteria) this;
        }

        public Criteria andBeginIsNull() {
            addCriterion("begin is null");
            return (Criteria) this;
        }

        public Criteria andBeginIsNotNull() {
            addCriterion("begin is not null");
            return (Criteria) this;
        }

        public Criteria andBeginEqualTo(Date value) {
            addCriterionForJDBCTime("begin =", value, "begin");
            return (Criteria) this;
        }

        public Criteria andBeginNotEqualTo(Date value) {
            addCriterionForJDBCTime("begin <>", value, "begin");
            return (Criteria) this;
        }

        public Criteria andBeginGreaterThan(Date value) {
            addCriterionForJDBCTime("begin >", value, "begin");
            return (Criteria) this;
        }

        public Criteria andBeginGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCTime("begin >=", value, "begin");
            return (Criteria) this;
        }

        public Criteria andBeginLessThan(Date value) {
            addCriterionForJDBCTime("begin <", value, "begin");
            return (Criteria) this;
        }

        public Criteria andBeginLessThanOrEqualTo(Date value) {
            addCriterionForJDBCTime("begin <=", value, "begin");
            return (Criteria) this;
        }

        public Criteria andBeginIn(List<Date> values) {
            addCriterionForJDBCTime("begin in", values, "begin");
            return (Criteria) this;
        }

        public Criteria andBeginNotIn(List<Date> values) {
            addCriterionForJDBCTime("begin not in", values, "begin");
            return (Criteria) this;
        }

        public Criteria andBeginBetween(Date value1, Date value2) {
            addCriterionForJDBCTime("begin between", value1, value2, "begin");
            return (Criteria) this;
        }

        public Criteria andBeginNotBetween(Date value1, Date value2) {
            addCriterionForJDBCTime("begin not between", value1, value2, "begin");
            return (Criteria) this;
        }

        public Criteria andEndIsNull() {
            addCriterion("end is null");
            return (Criteria) this;
        }

        public Criteria andEndIsNotNull() {
            addCriterion("end is not null");
            return (Criteria) this;
        }

        public Criteria andEndEqualTo(Date value) {
            addCriterionForJDBCTime("end =", value, "end");
            return (Criteria) this;
        }

        public Criteria andEndNotEqualTo(Date value) {
            addCriterionForJDBCTime("end <>", value, "end");
            return (Criteria) this;
        }

        public Criteria andEndGreaterThan(Date value) {
            addCriterionForJDBCTime("end >", value, "end");
            return (Criteria) this;
        }

        public Criteria andEndGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCTime("end >=", value, "end");
            return (Criteria) this;
        }

        public Criteria andEndLessThan(Date value) {
            addCriterionForJDBCTime("end <", value, "end");
            return (Criteria) this;
        }

        public Criteria andEndLessThanOrEqualTo(Date value) {
            addCriterionForJDBCTime("end <=", value, "end");
            return (Criteria) this;
        }

        public Criteria andEndIn(List<Date> values) {
            addCriterionForJDBCTime("end in", values, "end");
            return (Criteria) this;
        }

        public Criteria andEndNotIn(List<Date> values) {
            addCriterionForJDBCTime("end not in", values, "end");
            return (Criteria) this;
        }

        public Criteria andEndBetween(Date value1, Date value2) {
            addCriterionForJDBCTime("end between", value1, value2, "end");
            return (Criteria) this;
        }

        public Criteria andEndNotBetween(Date value1, Date value2) {
            addCriterionForJDBCTime("end not between", value1, value2, "end");
            return (Criteria) this;
        }

        public Criteria andWorknumberIsNull() {
            addCriterion("worknumber is null");
            return (Criteria) this;
        }

        public Criteria andWorknumberIsNotNull() {
            addCriterion("worknumber is not null");
            return (Criteria) this;
        }

        public Criteria andWorknumberEqualTo(String value) {
            addCriterion("worknumber =", value, "worknumber");
            return (Criteria) this;
        }

        public Criteria andWorknumberNotEqualTo(String value) {
            addCriterion("worknumber <>", value, "worknumber");
            return (Criteria) this;
        }

        public Criteria andWorknumberGreaterThan(String value) {
            addCriterion("worknumber >", value, "worknumber");
            return (Criteria) this;
        }

        public Criteria andWorknumberGreaterThanOrEqualTo(String value) {
            addCriterion("worknumber >=", value, "worknumber");
            return (Criteria) this;
        }

        public Criteria andWorknumberLessThan(String value) {
            addCriterion("worknumber <", value, "worknumber");
            return (Criteria) this;
        }

        public Criteria andWorknumberLessThanOrEqualTo(String value) {
            addCriterion("worknumber <=", value, "worknumber");
            return (Criteria) this;
        }

        public Criteria andWorknumberLike(String value) {
            addCriterion("worknumber like", value, "worknumber");
            return (Criteria) this;
        }

        public Criteria andWorknumberNotLike(String value) {
            addCriterion("worknumber not like", value, "worknumber");
            return (Criteria) this;
        }

        public Criteria andWorknumberIn(List<String> values) {
            addCriterion("worknumber in", values, "worknumber");
            return (Criteria) this;
        }

        public Criteria andWorknumberNotIn(List<String> values) {
            addCriterion("worknumber not in", values, "worknumber");
            return (Criteria) this;
        }

        public Criteria andWorknumberBetween(String value1, String value2) {
            addCriterion("worknumber between", value1, value2, "worknumber");
            return (Criteria) this;
        }

        public Criteria andWorknumberNotBetween(String value1, String value2) {
            addCriterion("worknumber not between", value1, value2, "worknumber");
            return (Criteria) this;
        }

        public Criteria andNumberIsNull() {
            addCriterion("number is null");
            return (Criteria) this;
        }

        public Criteria andNumberIsNotNull() {
            addCriterion("number is not null");
            return (Criteria) this;
        }

        public Criteria andNumberEqualTo(Integer value) {
            addCriterion("number =", value, "number");
            return (Criteria) this;
        }

        public Criteria andNumberNotEqualTo(Integer value) {
            addCriterion("number <>", value, "number");
            return (Criteria) this;
        }

        public Criteria andNumberGreaterThan(Integer value) {
            addCriterion("number >", value, "number");
            return (Criteria) this;
        }

        public Criteria andNumberGreaterThanOrEqualTo(Integer value) {
            addCriterion("number >=", value, "number");
            return (Criteria) this;
        }

        public Criteria andNumberLessThan(Integer value) {
            addCriterion("number <", value, "number");
            return (Criteria) this;
        }

        public Criteria andNumberLessThanOrEqualTo(Integer value) {
            addCriterion("number <=", value, "number");
            return (Criteria) this;
        }

        public Criteria andNumberIn(List<Integer> values) {
            addCriterion("number in", values, "number");
            return (Criteria) this;
        }

        public Criteria andNumberNotIn(List<Integer> values) {
            addCriterion("number not in", values, "number");
            return (Criteria) this;
        }

        public Criteria andNumberBetween(Integer value1, Integer value2) {
            addCriterion("number between", value1, value2, "number");
            return (Criteria) this;
        }

        public Criteria andNumberNotBetween(Integer value1, Integer value2) {
            addCriterion("number not between", value1, value2, "number");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}
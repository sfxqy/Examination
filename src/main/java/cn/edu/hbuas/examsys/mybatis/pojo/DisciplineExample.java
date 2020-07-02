package cn.edu.hbuas.examsys.mybatis.pojo;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class DisciplineExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public DisciplineExample() {
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

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andDisidIsNull() {
            addCriterion("disid is null");
            return (Criteria) this;
        }

        public Criteria andDisidIsNotNull() {
            addCriterion("disid is not null");
            return (Criteria) this;
        }

        public Criteria andDisidEqualTo(Integer value) {
            addCriterion("disid =", value, "disid");
            return (Criteria) this;
        }

        public Criteria andDisidNotEqualTo(Integer value) {
            addCriterion("disid <>", value, "disid");
            return (Criteria) this;
        }

        public Criteria andDisidGreaterThan(Integer value) {
            addCriterion("disid >", value, "disid");
            return (Criteria) this;
        }

        public Criteria andDisidGreaterThanOrEqualTo(Integer value) {
            addCriterion("disid >=", value, "disid");
            return (Criteria) this;
        }

        public Criteria andDisidLessThan(Integer value) {
            addCriterion("disid <", value, "disid");
            return (Criteria) this;
        }

        public Criteria andDisidLessThanOrEqualTo(Integer value) {
            addCriterion("disid <=", value, "disid");
            return (Criteria) this;
        }

        public Criteria andDisidIn(List<Integer> values) {
            addCriterion("disid in", values, "disid");
            return (Criteria) this;
        }

        public Criteria andDisidNotIn(List<Integer> values) {
            addCriterion("disid not in", values, "disid");
            return (Criteria) this;
        }

        public Criteria andDisidBetween(Integer value1, Integer value2) {
            addCriterion("disid between", value1, value2, "disid");
            return (Criteria) this;
        }

        public Criteria andDisidNotBetween(Integer value1, Integer value2) {
            addCriterion("disid not between", value1, value2, "disid");
            return (Criteria) this;
        }

        public Criteria andSidIsNull() {
            addCriterion("sid is null");
            return (Criteria) this;
        }

        public Criteria andSidIsNotNull() {
            addCriterion("sid is not null");
            return (Criteria) this;
        }

        public Criteria andSidEqualTo(String value) {
            addCriterion("sid =", value, "sid");
            return (Criteria) this;
        }

        public Criteria andSidNotEqualTo(String value) {
            addCriterion("sid <>", value, "sid");
            return (Criteria) this;
        }

        public Criteria andSidGreaterThan(String value) {
            addCriterion("sid >", value, "sid");
            return (Criteria) this;
        }

        public Criteria andSidGreaterThanOrEqualTo(String value) {
            addCriterion("sid >=", value, "sid");
            return (Criteria) this;
        }

        public Criteria andSidLessThan(String value) {
            addCriterion("sid <", value, "sid");
            return (Criteria) this;
        }

        public Criteria andSidLessThanOrEqualTo(String value) {
            addCriterion("sid <=", value, "sid");
            return (Criteria) this;
        }

        public Criteria andSidLike(String value) {
            addCriterion("sid like", value, "sid");
            return (Criteria) this;
        }

        public Criteria andSidNotLike(String value) {
            addCriterion("sid not like", value, "sid");
            return (Criteria) this;
        }

        public Criteria andSidIn(List<String> values) {
            addCriterion("sid in", values, "sid");
            return (Criteria) this;
        }

        public Criteria andSidNotIn(List<String> values) {
            addCriterion("sid not in", values, "sid");
            return (Criteria) this;
        }

        public Criteria andSidBetween(String value1, String value2) {
            addCriterion("sid between", value1, value2, "sid");
            return (Criteria) this;
        }

        public Criteria andSidNotBetween(String value1, String value2) {
            addCriterion("sid not between", value1, value2, "sid");
            return (Criteria) this;
        }

        public Criteria andPlaceIsNull() {
            addCriterion("place is null");
            return (Criteria) this;
        }

        public Criteria andPlaceIsNotNull() {
            addCriterion("place is not null");
            return (Criteria) this;
        }

        public Criteria andPlaceEqualTo(String value) {
            addCriterion("place =", value, "place");
            return (Criteria) this;
        }

        public Criteria andPlaceNotEqualTo(String value) {
            addCriterion("place <>", value, "place");
            return (Criteria) this;
        }

        public Criteria andPlaceGreaterThan(String value) {
            addCriterion("place >", value, "place");
            return (Criteria) this;
        }

        public Criteria andPlaceGreaterThanOrEqualTo(String value) {
            addCriterion("place >=", value, "place");
            return (Criteria) this;
        }

        public Criteria andPlaceLessThan(String value) {
            addCriterion("place <", value, "place");
            return (Criteria) this;
        }

        public Criteria andPlaceLessThanOrEqualTo(String value) {
            addCriterion("place <=", value, "place");
            return (Criteria) this;
        }

        public Criteria andPlaceLike(String value) {
            addCriterion("place like", value, "place");
            return (Criteria) this;
        }

        public Criteria andPlaceNotLike(String value) {
            addCriterion("place not like", value, "place");
            return (Criteria) this;
        }

        public Criteria andPlaceIn(List<String> values) {
            addCriterion("place in", values, "place");
            return (Criteria) this;
        }

        public Criteria andPlaceNotIn(List<String> values) {
            addCriterion("place not in", values, "place");
            return (Criteria) this;
        }

        public Criteria andPlaceBetween(String value1, String value2) {
            addCriterion("place between", value1, value2, "place");
            return (Criteria) this;
        }

        public Criteria andPlaceNotBetween(String value1, String value2) {
            addCriterion("place not between", value1, value2, "place");
            return (Criteria) this;
        }

        public Criteria andSubjectsIsNull() {
            addCriterion("subjects is null");
            return (Criteria) this;
        }

        public Criteria andSubjectsIsNotNull() {
            addCriterion("subjects is not null");
            return (Criteria) this;
        }

        public Criteria andSubjectsEqualTo(String value) {
            addCriterion("subjects =", value, "subjects");
            return (Criteria) this;
        }

        public Criteria andSubjectsNotEqualTo(String value) {
            addCriterion("subjects <>", value, "subjects");
            return (Criteria) this;
        }

        public Criteria andSubjectsGreaterThan(String value) {
            addCriterion("subjects >", value, "subjects");
            return (Criteria) this;
        }

        public Criteria andSubjectsGreaterThanOrEqualTo(String value) {
            addCriterion("subjects >=", value, "subjects");
            return (Criteria) this;
        }

        public Criteria andSubjectsLessThan(String value) {
            addCriterion("subjects <", value, "subjects");
            return (Criteria) this;
        }

        public Criteria andSubjectsLessThanOrEqualTo(String value) {
            addCriterion("subjects <=", value, "subjects");
            return (Criteria) this;
        }

        public Criteria andSubjectsLike(String value) {
            addCriterion("subjects like", value, "subjects");
            return (Criteria) this;
        }

        public Criteria andSubjectsNotLike(String value) {
            addCriterion("subjects not like", value, "subjects");
            return (Criteria) this;
        }

        public Criteria andSubjectsIn(List<String> values) {
            addCriterion("subjects in", values, "subjects");
            return (Criteria) this;
        }

        public Criteria andSubjectsNotIn(List<String> values) {
            addCriterion("subjects not in", values, "subjects");
            return (Criteria) this;
        }

        public Criteria andSubjectsBetween(String value1, String value2) {
            addCriterion("subjects between", value1, value2, "subjects");
            return (Criteria) this;
        }

        public Criteria andSubjectsNotBetween(String value1, String value2) {
            addCriterion("subjects not between", value1, value2, "subjects");
            return (Criteria) this;
        }

        public Criteria andDateofIsNull() {
            addCriterion("dateof is null");
            return (Criteria) this;
        }

        public Criteria andDateofIsNotNull() {
            addCriterion("dateof is not null");
            return (Criteria) this;
        }

        public Criteria andDateofEqualTo(String value) {
            addCriterion("dateof =", value, "dateof");
            return (Criteria) this;
        }

        public Criteria andDateofNotEqualTo(String value) {
            addCriterion("dateof <>", value, "dateof");
            return (Criteria) this;
        }

        public Criteria andDateofGreaterThan(String value) {
            addCriterion("dateof >", value, "dateof");
            return (Criteria) this;
        }

        public Criteria andDateofGreaterThanOrEqualTo(String value) {
            addCriterion("dateof >=", value, "dateof");
            return (Criteria) this;
        }

        public Criteria andDateofLessThan(String value) {
            addCriterion("dateof <", value, "dateof");
            return (Criteria) this;
        }

        public Criteria andDateofLessThanOrEqualTo(String value) {
            addCriterion("dateof <=", value, "dateof");
            return (Criteria) this;
        }

        public Criteria andDateofLike(String value) {
            addCriterion("dateof like", value, "dateof");
            return (Criteria) this;
        }

        public Criteria andDateofNotLike(String value) {
            addCriterion("dateof not like", value, "dateof");
            return (Criteria) this;
        }

        public Criteria andDateofIn(List<String> values) {
            addCriterion("dateof in", values, "dateof");
            return (Criteria) this;
        }

        public Criteria andDateofNotIn(List<String> values) {
            addCriterion("dateof not in", values, "dateof");
            return (Criteria) this;
        }

        public Criteria andDateofBetween(String value1, String value2) {
            addCriterion("dateof between", value1, value2, "dateof");
            return (Criteria) this;
        }

        public Criteria andDateofNotBetween(String value1, String value2) {
            addCriterion("dateof not between", value1, value2, "dateof");
            return (Criteria) this;
        }

        public Criteria andAddtimeIsNull() {
            addCriterion("addtime is null");
            return (Criteria) this;
        }

        public Criteria andAddtimeIsNotNull() {
            addCriterion("addtime is not null");
            return (Criteria) this;
        }

        public Criteria andAddtimeEqualTo(Date value) {
            addCriterionForJDBCDate("addtime =", value, "addtime");
            return (Criteria) this;
        }

        public Criteria andAddtimeNotEqualTo(Date value) {
            addCriterionForJDBCDate("addtime <>", value, "addtime");
            return (Criteria) this;
        }

        public Criteria andAddtimeGreaterThan(Date value) {
            addCriterionForJDBCDate("addtime >", value, "addtime");
            return (Criteria) this;
        }

        public Criteria andAddtimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("addtime >=", value, "addtime");
            return (Criteria) this;
        }

        public Criteria andAddtimeLessThan(Date value) {
            addCriterionForJDBCDate("addtime <", value, "addtime");
            return (Criteria) this;
        }

        public Criteria andAddtimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCDate("addtime <=", value, "addtime");
            return (Criteria) this;
        }

        public Criteria andAddtimeIn(List<Date> values) {
            addCriterionForJDBCDate("addtime in", values, "addtime");
            return (Criteria) this;
        }

        public Criteria andAddtimeNotIn(List<Date> values) {
            addCriterionForJDBCDate("addtime not in", values, "addtime");
            return (Criteria) this;
        }

        public Criteria andAddtimeBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("addtime between", value1, value2, "addtime");
            return (Criteria) this;
        }

        public Criteria andAddtimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCDate("addtime not between", value1, value2, "addtime");
            return (Criteria) this;
        }

        public Criteria andReasonIsNull() {
            addCriterion("reason is null");
            return (Criteria) this;
        }

        public Criteria andReasonIsNotNull() {
            addCriterion("reason is not null");
            return (Criteria) this;
        }

        public Criteria andReasonEqualTo(Integer value) {
            addCriterion("reason =", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonNotEqualTo(Integer value) {
            addCriterion("reason <>", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonGreaterThan(Integer value) {
            addCriterion("reason >", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonGreaterThanOrEqualTo(Integer value) {
            addCriterion("reason >=", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonLessThan(Integer value) {
            addCriterion("reason <", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonLessThanOrEqualTo(Integer value) {
            addCriterion("reason <=", value, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonIn(List<Integer> values) {
            addCriterion("reason in", values, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonNotIn(List<Integer> values) {
            addCriterion("reason not in", values, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonBetween(Integer value1, Integer value2) {
            addCriterion("reason between", value1, value2, "reason");
            return (Criteria) this;
        }

        public Criteria andReasonNotBetween(Integer value1, Integer value2) {
            addCriterion("reason not between", value1, value2, "reason");
            return (Criteria) this;
        }

        public Criteria andNoteIsNull() {
            addCriterion("note is null");
            return (Criteria) this;
        }

        public Criteria andNoteIsNotNull() {
            addCriterion("note is not null");
            return (Criteria) this;
        }

        public Criteria andNoteEqualTo(String value) {
            addCriterion("note =", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteNotEqualTo(String value) {
            addCriterion("note <>", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteGreaterThan(String value) {
            addCriterion("note >", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteGreaterThanOrEqualTo(String value) {
            addCriterion("note >=", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteLessThan(String value) {
            addCriterion("note <", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteLessThanOrEqualTo(String value) {
            addCriterion("note <=", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteLike(String value) {
            addCriterion("note like", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteNotLike(String value) {
            addCriterion("note not like", value, "note");
            return (Criteria) this;
        }

        public Criteria andNoteIn(List<String> values) {
            addCriterion("note in", values, "note");
            return (Criteria) this;
        }

        public Criteria andNoteNotIn(List<String> values) {
            addCriterion("note not in", values, "note");
            return (Criteria) this;
        }

        public Criteria andNoteBetween(String value1, String value2) {
            addCriterion("note between", value1, value2, "note");
            return (Criteria) this;
        }

        public Criteria andNoteNotBetween(String value1, String value2) {
            addCriterion("note not between", value1, value2, "note");
            return (Criteria) this;
        }

        public Criteria andTotaltimeIsNull() {
            addCriterion("totaltime is null");
            return (Criteria) this;
        }

        public Criteria andTotaltimeIsNotNull() {
            addCriterion("totaltime is not null");
            return (Criteria) this;
        }

        public Criteria andTotaltimeEqualTo(Integer value) {
            addCriterion("totaltime =", value, "totaltime");
            return (Criteria) this;
        }

        public Criteria andTotaltimeNotEqualTo(Integer value) {
            addCriterion("totaltime <>", value, "totaltime");
            return (Criteria) this;
        }

        public Criteria andTotaltimeGreaterThan(Integer value) {
            addCriterion("totaltime >", value, "totaltime");
            return (Criteria) this;
        }

        public Criteria andTotaltimeGreaterThanOrEqualTo(Integer value) {
            addCriterion("totaltime >=", value, "totaltime");
            return (Criteria) this;
        }

        public Criteria andTotaltimeLessThan(Integer value) {
            addCriterion("totaltime <", value, "totaltime");
            return (Criteria) this;
        }

        public Criteria andTotaltimeLessThanOrEqualTo(Integer value) {
            addCriterion("totaltime <=", value, "totaltime");
            return (Criteria) this;
        }

        public Criteria andTotaltimeIn(List<Integer> values) {
            addCriterion("totaltime in", values, "totaltime");
            return (Criteria) this;
        }

        public Criteria andTotaltimeNotIn(List<Integer> values) {
            addCriterion("totaltime not in", values, "totaltime");
            return (Criteria) this;
        }

        public Criteria andTotaltimeBetween(Integer value1, Integer value2) {
            addCriterion("totaltime between", value1, value2, "totaltime");
            return (Criteria) this;
        }

        public Criteria andTotaltimeNotBetween(Integer value1, Integer value2) {
            addCriterion("totaltime not between", value1, value2, "totaltime");
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
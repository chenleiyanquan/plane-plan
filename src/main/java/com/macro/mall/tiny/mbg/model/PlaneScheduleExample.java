package com.macro.mall.tiny.mbg.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class PlaneScheduleExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PlaneScheduleExample() {
        oredCriteria = new ArrayList<>();
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
            criteria = new ArrayList<>();
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
            List<java.sql.Time> timeList = new ArrayList<>();
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

        public Criteria andCallsignIsNull() {
            addCriterion("callsign is null");
            return (Criteria) this;
        }

        public Criteria andCallsignIsNotNull() {
            addCriterion("callsign is not null");
            return (Criteria) this;
        }

        public Criteria andCallsignEqualTo(String value) {
            addCriterion("callsign =", value, "callsign");
            return (Criteria) this;
        }

        public Criteria andCallsignNotEqualTo(String value) {
            addCriterion("callsign <>", value, "callsign");
            return (Criteria) this;
        }

        public Criteria andCallsignGreaterThan(String value) {
            addCriterion("callsign >", value, "callsign");
            return (Criteria) this;
        }

        public Criteria andCallsignGreaterThanOrEqualTo(String value) {
            addCriterion("callsign >=", value, "callsign");
            return (Criteria) this;
        }

        public Criteria andCallsignLessThan(String value) {
            addCriterion("callsign <", value, "callsign");
            return (Criteria) this;
        }

        public Criteria andCallsignLessThanOrEqualTo(String value) {
            addCriterion("callsign <=", value, "callsign");
            return (Criteria) this;
        }

        public Criteria andCallsignLike(String value) {
            addCriterion("callsign like", value, "callsign");
            return (Criteria) this;
        }

        public Criteria andCallsignNotLike(String value) {
            addCriterion("callsign not like", value, "callsign");
            return (Criteria) this;
        }

        public Criteria andCallsignIn(List<String> values) {
            addCriterion("callsign in", values, "callsign");
            return (Criteria) this;
        }

        public Criteria andCallsignNotIn(List<String> values) {
            addCriterion("callsign not in", values, "callsign");
            return (Criteria) this;
        }

        public Criteria andCallsignBetween(String value1, String value2) {
            addCriterion("callsign between", value1, value2, "callsign");
            return (Criteria) this;
        }

        public Criteria andCallsignNotBetween(String value1, String value2) {
            addCriterion("callsign not between", value1, value2, "callsign");
            return (Criteria) this;
        }

        public Criteria andRoutingIsNull() {
            addCriterion("routing is null");
            return (Criteria) this;
        }

        public Criteria andRoutingIsNotNull() {
            addCriterion("routing is not null");
            return (Criteria) this;
        }

        public Criteria andRoutingEqualTo(String value) {
            addCriterion("routing =", value, "routing");
            return (Criteria) this;
        }

        public Criteria andRoutingNotEqualTo(String value) {
            addCriterion("routing <>", value, "routing");
            return (Criteria) this;
        }

        public Criteria andRoutingGreaterThan(String value) {
            addCriterion("routing >", value, "routing");
            return (Criteria) this;
        }

        public Criteria andRoutingGreaterThanOrEqualTo(String value) {
            addCriterion("routing >=", value, "routing");
            return (Criteria) this;
        }

        public Criteria andRoutingLessThan(String value) {
            addCriterion("routing <", value, "routing");
            return (Criteria) this;
        }

        public Criteria andRoutingLessThanOrEqualTo(String value) {
            addCriterion("routing <=", value, "routing");
            return (Criteria) this;
        }

        public Criteria andRoutingLike(String value) {
            addCriterion("routing like", value, "routing");
            return (Criteria) this;
        }

        public Criteria andRoutingNotLike(String value) {
            addCriterion("routing not like", value, "routing");
            return (Criteria) this;
        }

        public Criteria andRoutingIn(List<String> values) {
            addCriterion("routing in", values, "routing");
            return (Criteria) this;
        }

        public Criteria andRoutingNotIn(List<String> values) {
            addCriterion("routing not in", values, "routing");
            return (Criteria) this;
        }

        public Criteria andRoutingBetween(String value1, String value2) {
            addCriterion("routing between", value1, value2, "routing");
            return (Criteria) this;
        }

        public Criteria andRoutingNotBetween(String value1, String value2) {
            addCriterion("routing not between", value1, value2, "routing");
            return (Criteria) this;
        }

        public Criteria andRegistrationIsNull() {
            addCriterion("registration is null");
            return (Criteria) this;
        }

        public Criteria andRegistrationIsNotNull() {
            addCriterion("registration is not null");
            return (Criteria) this;
        }

        public Criteria andRegistrationEqualTo(String value) {
            addCriterion("registration =", value, "registration");
            return (Criteria) this;
        }

        public Criteria andRegistrationNotEqualTo(String value) {
            addCriterion("registration <>", value, "registration");
            return (Criteria) this;
        }

        public Criteria andRegistrationGreaterThan(String value) {
            addCriterion("registration >", value, "registration");
            return (Criteria) this;
        }

        public Criteria andRegistrationGreaterThanOrEqualTo(String value) {
            addCriterion("registration >=", value, "registration");
            return (Criteria) this;
        }

        public Criteria andRegistrationLessThan(String value) {
            addCriterion("registration <", value, "registration");
            return (Criteria) this;
        }

        public Criteria andRegistrationLessThanOrEqualTo(String value) {
            addCriterion("registration <=", value, "registration");
            return (Criteria) this;
        }

        public Criteria andRegistrationLike(String value) {
            addCriterion("registration like", value, "registration");
            return (Criteria) this;
        }

        public Criteria andRegistrationNotLike(String value) {
            addCriterion("registration not like", value, "registration");
            return (Criteria) this;
        }

        public Criteria andRegistrationIn(List<String> values) {
            addCriterion("registration in", values, "registration");
            return (Criteria) this;
        }

        public Criteria andRegistrationNotIn(List<String> values) {
            addCriterion("registration not in", values, "registration");
            return (Criteria) this;
        }

        public Criteria andRegistrationBetween(String value1, String value2) {
            addCriterion("registration between", value1, value2, "registration");
            return (Criteria) this;
        }

        public Criteria andRegistrationNotBetween(String value1, String value2) {
            addCriterion("registration not between", value1, value2, "registration");
            return (Criteria) this;
        }

        public Criteria andDeptimeIsNull() {
            addCriterion("deptime is null");
            return (Criteria) this;
        }

        public Criteria andDeptimeIsNotNull() {
            addCriterion("deptime is not null");
            return (Criteria) this;
        }

        public Criteria andDeptimeEqualTo(Date value) {
            addCriterionForJDBCTime("deptime =", value, "deptime");
            return (Criteria) this;
        }

        public Criteria andDeptimeNotEqualTo(Date value) {
            addCriterionForJDBCTime("deptime <>", value, "deptime");
            return (Criteria) this;
        }

        public Criteria andDeptimeGreaterThan(Date value) {
            addCriterionForJDBCTime("deptime >", value, "deptime");
            return (Criteria) this;
        }

        public Criteria andDeptimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCTime("deptime >=", value, "deptime");
            return (Criteria) this;
        }

        public Criteria andDeptimeLessThan(Date value) {
            addCriterionForJDBCTime("deptime <", value, "deptime");
            return (Criteria) this;
        }

        public Criteria andDeptimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCTime("deptime <=", value, "deptime");
            return (Criteria) this;
        }

        public Criteria andDeptimeIn(List<Date> values) {
            addCriterionForJDBCTime("deptime in", values, "deptime");
            return (Criteria) this;
        }

        public Criteria andDeptimeNotIn(List<Date> values) {
            addCriterionForJDBCTime("deptime not in", values, "deptime");
            return (Criteria) this;
        }

        public Criteria andDeptimeBetween(Date value1, Date value2) {
            addCriterionForJDBCTime("deptime between", value1, value2, "deptime");
            return (Criteria) this;
        }

        public Criteria andDeptimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCTime("deptime not between", value1, value2, "deptime");
            return (Criteria) this;
        }

        public Criteria andArrtimeIsNull() {
            addCriterion("arrtime is null");
            return (Criteria) this;
        }

        public Criteria andArrtimeIsNotNull() {
            addCriterion("arrtime is not null");
            return (Criteria) this;
        }

        public Criteria andArrtimeEqualTo(Date value) {
            addCriterionForJDBCTime("arrtime =", value, "arrtime");
            return (Criteria) this;
        }

        public Criteria andArrtimeNotEqualTo(Date value) {
            addCriterionForJDBCTime("arrtime <>", value, "arrtime");
            return (Criteria) this;
        }

        public Criteria andArrtimeGreaterThan(Date value) {
            addCriterionForJDBCTime("arrtime >", value, "arrtime");
            return (Criteria) this;
        }

        public Criteria andArrtimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCTime("arrtime >=", value, "arrtime");
            return (Criteria) this;
        }

        public Criteria andArrtimeLessThan(Date value) {
            addCriterionForJDBCTime("arrtime <", value, "arrtime");
            return (Criteria) this;
        }

        public Criteria andArrtimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCTime("arrtime <=", value, "arrtime");
            return (Criteria) this;
        }

        public Criteria andArrtimeIn(List<Date> values) {
            addCriterionForJDBCTime("arrtime in", values, "arrtime");
            return (Criteria) this;
        }

        public Criteria andArrtimeNotIn(List<Date> values) {
            addCriterionForJDBCTime("arrtime not in", values, "arrtime");
            return (Criteria) this;
        }

        public Criteria andArrtimeBetween(Date value1, Date value2) {
            addCriterionForJDBCTime("arrtime between", value1, value2, "arrtime");
            return (Criteria) this;
        }

        public Criteria andArrtimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCTime("arrtime not between", value1, value2, "arrtime");
            return (Criteria) this;
        }

        public Criteria andOriginIsNull() {
            addCriterion("origin is null");
            return (Criteria) this;
        }

        public Criteria andOriginIsNotNull() {
            addCriterion("origin is not null");
            return (Criteria) this;
        }

        public Criteria andOriginEqualTo(String value) {
            addCriterion("origin =", value, "origin");
            return (Criteria) this;
        }

        public Criteria andOriginNotEqualTo(String value) {
            addCriterion("origin <>", value, "origin");
            return (Criteria) this;
        }

        public Criteria andOriginGreaterThan(String value) {
            addCriterion("origin >", value, "origin");
            return (Criteria) this;
        }

        public Criteria andOriginGreaterThanOrEqualTo(String value) {
            addCriterion("origin >=", value, "origin");
            return (Criteria) this;
        }

        public Criteria andOriginLessThan(String value) {
            addCriterion("origin <", value, "origin");
            return (Criteria) this;
        }

        public Criteria andOriginLessThanOrEqualTo(String value) {
            addCriterion("origin <=", value, "origin");
            return (Criteria) this;
        }

        public Criteria andOriginLike(String value) {
            addCriterion("origin like", value, "origin");
            return (Criteria) this;
        }

        public Criteria andOriginNotLike(String value) {
            addCriterion("origin not like", value, "origin");
            return (Criteria) this;
        }

        public Criteria andOriginIn(List<String> values) {
            addCriterion("origin in", values, "origin");
            return (Criteria) this;
        }

        public Criteria andOriginNotIn(List<String> values) {
            addCriterion("origin not in", values, "origin");
            return (Criteria) this;
        }

        public Criteria andOriginBetween(String value1, String value2) {
            addCriterion("origin between", value1, value2, "origin");
            return (Criteria) this;
        }

        public Criteria andOriginNotBetween(String value1, String value2) {
            addCriterion("origin not between", value1, value2, "origin");
            return (Criteria) this;
        }

        public Criteria andDestinationIsNull() {
            addCriterion("destination is null");
            return (Criteria) this;
        }

        public Criteria andDestinationIsNotNull() {
            addCriterion("destination is not null");
            return (Criteria) this;
        }

        public Criteria andDestinationEqualTo(String value) {
            addCriterion("destination =", value, "destination");
            return (Criteria) this;
        }

        public Criteria andDestinationNotEqualTo(String value) {
            addCriterion("destination <>", value, "destination");
            return (Criteria) this;
        }

        public Criteria andDestinationGreaterThan(String value) {
            addCriterion("destination >", value, "destination");
            return (Criteria) this;
        }

        public Criteria andDestinationGreaterThanOrEqualTo(String value) {
            addCriterion("destination >=", value, "destination");
            return (Criteria) this;
        }

        public Criteria andDestinationLessThan(String value) {
            addCriterion("destination <", value, "destination");
            return (Criteria) this;
        }

        public Criteria andDestinationLessThanOrEqualTo(String value) {
            addCriterion("destination <=", value, "destination");
            return (Criteria) this;
        }

        public Criteria andDestinationLike(String value) {
            addCriterion("destination like", value, "destination");
            return (Criteria) this;
        }

        public Criteria andDestinationNotLike(String value) {
            addCriterion("destination not like", value, "destination");
            return (Criteria) this;
        }

        public Criteria andDestinationIn(List<String> values) {
            addCriterion("destination in", values, "destination");
            return (Criteria) this;
        }

        public Criteria andDestinationNotIn(List<String> values) {
            addCriterion("destination not in", values, "destination");
            return (Criteria) this;
        }

        public Criteria andDestinationBetween(String value1, String value2) {
            addCriterion("destination between", value1, value2, "destination");
            return (Criteria) this;
        }

        public Criteria andDestinationNotBetween(String value1, String value2) {
            addCriterion("destination not between", value1, value2, "destination");
            return (Criteria) this;
        }

        public Criteria andAirlineIsNull() {
            addCriterion("airline is null");
            return (Criteria) this;
        }

        public Criteria andAirlineIsNotNull() {
            addCriterion("airline is not null");
            return (Criteria) this;
        }

        public Criteria andAirlineEqualTo(String value) {
            addCriterion("airline =", value, "airline");
            return (Criteria) this;
        }

        public Criteria andAirlineNotEqualTo(String value) {
            addCriterion("airline <>", value, "airline");
            return (Criteria) this;
        }

        public Criteria andAirlineGreaterThan(String value) {
            addCriterion("airline >", value, "airline");
            return (Criteria) this;
        }

        public Criteria andAirlineGreaterThanOrEqualTo(String value) {
            addCriterion("airline >=", value, "airline");
            return (Criteria) this;
        }

        public Criteria andAirlineLessThan(String value) {
            addCriterion("airline <", value, "airline");
            return (Criteria) this;
        }

        public Criteria andAirlineLessThanOrEqualTo(String value) {
            addCriterion("airline <=", value, "airline");
            return (Criteria) this;
        }

        public Criteria andAirlineLike(String value) {
            addCriterion("airline like", value, "airline");
            return (Criteria) this;
        }

        public Criteria andAirlineNotLike(String value) {
            addCriterion("airline not like", value, "airline");
            return (Criteria) this;
        }

        public Criteria andAirlineIn(List<String> values) {
            addCriterion("airline in", values, "airline");
            return (Criteria) this;
        }

        public Criteria andAirlineNotIn(List<String> values) {
            addCriterion("airline not in", values, "airline");
            return (Criteria) this;
        }

        public Criteria andAirlineBetween(String value1, String value2) {
            addCriterion("airline between", value1, value2, "airline");
            return (Criteria) this;
        }

        public Criteria andAirlineNotBetween(String value1, String value2) {
            addCriterion("airline not between", value1, value2, "airline");
            return (Criteria) this;
        }

        public Criteria andDataSourceIsNull() {
            addCriterion("data_source is null");
            return (Criteria) this;
        }

        public Criteria andDataSourceIsNotNull() {
            addCriterion("data_source is not null");
            return (Criteria) this;
        }

        public Criteria andDataSourceEqualTo(Byte value) {
            addCriterion("data_source =", value, "dataSource");
            return (Criteria) this;
        }

        public Criteria andDataSourceNotEqualTo(Byte value) {
            addCriterion("data_source <>", value, "dataSource");
            return (Criteria) this;
        }

        public Criteria andDataSourceGreaterThan(Byte value) {
            addCriterion("data_source >", value, "dataSource");
            return (Criteria) this;
        }

        public Criteria andDataSourceGreaterThanOrEqualTo(Byte value) {
            addCriterion("data_source >=", value, "dataSource");
            return (Criteria) this;
        }

        public Criteria andDataSourceLessThan(Byte value) {
            addCriterion("data_source <", value, "dataSource");
            return (Criteria) this;
        }

        public Criteria andDataSourceLessThanOrEqualTo(Byte value) {
            addCriterion("data_source <=", value, "dataSource");
            return (Criteria) this;
        }

        public Criteria andDataSourceIn(List<Byte> values) {
            addCriterion("data_source in", values, "dataSource");
            return (Criteria) this;
        }

        public Criteria andDataSourceNotIn(List<Byte> values) {
            addCriterion("data_source not in", values, "dataSource");
            return (Criteria) this;
        }

        public Criteria andDataSourceBetween(Byte value1, Byte value2) {
            addCriterion("data_source between", value1, value2, "dataSource");
            return (Criteria) this;
        }

        public Criteria andDataSourceNotBetween(Byte value1, Byte value2) {
            addCriterion("data_source not between", value1, value2, "dataSource");
            return (Criteria) this;
        }

        public Criteria andAirtypeIsNull() {
            addCriterion("airType is null");
            return (Criteria) this;
        }

        public Criteria andAirtypeIsNotNull() {
            addCriterion("airType is not null");
            return (Criteria) this;
        }

        public Criteria andAirtypeEqualTo(String value) {
            addCriterion("airType =", value, "airtype");
            return (Criteria) this;
        }

        public Criteria andAirtypeNotEqualTo(String value) {
            addCriterion("airType <>", value, "airtype");
            return (Criteria) this;
        }

        public Criteria andAirtypeGreaterThan(String value) {
            addCriterion("airType >", value, "airtype");
            return (Criteria) this;
        }

        public Criteria andAirtypeGreaterThanOrEqualTo(String value) {
            addCriterion("airType >=", value, "airtype");
            return (Criteria) this;
        }

        public Criteria andAirtypeLessThan(String value) {
            addCriterion("airType <", value, "airtype");
            return (Criteria) this;
        }

        public Criteria andAirtypeLessThanOrEqualTo(String value) {
            addCriterion("airType <=", value, "airtype");
            return (Criteria) this;
        }

        public Criteria andAirtypeLike(String value) {
            addCriterion("airType like", value, "airtype");
            return (Criteria) this;
        }

        public Criteria andAirtypeNotLike(String value) {
            addCriterion("airType not like", value, "airtype");
            return (Criteria) this;
        }

        public Criteria andAirtypeIn(List<String> values) {
            addCriterion("airType in", values, "airtype");
            return (Criteria) this;
        }

        public Criteria andAirtypeNotIn(List<String> values) {
            addCriterion("airType not in", values, "airtype");
            return (Criteria) this;
        }

        public Criteria andAirtypeBetween(String value1, String value2) {
            addCriterion("airType between", value1, value2, "airtype");
            return (Criteria) this;
        }

        public Criteria andAirtypeNotBetween(String value1, String value2) {
            addCriterion("airType not between", value1, value2, "airtype");
            return (Criteria) this;
        }

        public Criteria andAirtimeIsNull() {
            addCriterion("airTime is null");
            return (Criteria) this;
        }

        public Criteria andAirtimeIsNotNull() {
            addCriterion("airTime is not null");
            return (Criteria) this;
        }

        public Criteria andAirtimeEqualTo(Date value) {
            addCriterionForJDBCTime("airTime =", value, "airtime");
            return (Criteria) this;
        }

        public Criteria andAirtimeNotEqualTo(Date value) {
            addCriterionForJDBCTime("airTime <>", value, "airtime");
            return (Criteria) this;
        }

        public Criteria andAirtimeGreaterThan(Date value) {
            addCriterionForJDBCTime("airTime >", value, "airtime");
            return (Criteria) this;
        }

        public Criteria andAirtimeGreaterThanOrEqualTo(Date value) {
            addCriterionForJDBCTime("airTime >=", value, "airtime");
            return (Criteria) this;
        }

        public Criteria andAirtimeLessThan(Date value) {
            addCriterionForJDBCTime("airTime <", value, "airtime");
            return (Criteria) this;
        }

        public Criteria andAirtimeLessThanOrEqualTo(Date value) {
            addCriterionForJDBCTime("airTime <=", value, "airtime");
            return (Criteria) this;
        }

        public Criteria andAirtimeIn(List<Date> values) {
            addCriterionForJDBCTime("airTime in", values, "airtime");
            return (Criteria) this;
        }

        public Criteria andAirtimeNotIn(List<Date> values) {
            addCriterionForJDBCTime("airTime not in", values, "airtime");
            return (Criteria) this;
        }

        public Criteria andAirtimeBetween(Date value1, Date value2) {
            addCriterionForJDBCTime("airTime between", value1, value2, "airtime");
            return (Criteria) this;
        }

        public Criteria andAirtimeNotBetween(Date value1, Date value2) {
            addCriterionForJDBCTime("airTime not between", value1, value2, "airtime");
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
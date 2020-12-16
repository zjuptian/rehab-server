package com.zju.gcs.model;

public class UserDO {
    private Integer id;

    private String username;

    private String password;

    private String department;

    private Integer createPatientinfo = 0;

    private Integer queryPatientinfo = 0;

    private Integer createTreatrecord = 0;

    private Integer queryCase = 0;

    private Integer analysisAi = 0;

//    private Integer createTreatplan = 0;

    private Integer controlAccess = 0;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department == null ? null : department.trim();
    }

    public Integer getCreatePatientinfo() {
        return createPatientinfo;
    }

    public void setCreatePatientinfo(Integer createPatientinfo) {
        this.createPatientinfo = createPatientinfo;
    }

    public Integer getQueryPatientinfo() {
        return queryPatientinfo;
    }

    public void setQueryPatientinfo(Integer queryPatientinfo) {
        this.queryPatientinfo = queryPatientinfo;
    }

    public Integer getCreateTreatrecord() {
        return createTreatrecord;
    }

    public void setCreateTreatrecord(Integer createTreatrecord) {
        this.createTreatrecord = createTreatrecord;
    }

    public Integer getQueryCase() {
        return queryCase;
    }

    public void setQueryCase(Integer queryCase) {
        this.queryCase = queryCase;
    }

    public Integer getAnalysisAi() {
        return analysisAi;
    }

    public void setAnalysisAi(Integer analysisAi) {
        this.analysisAi = analysisAi;
    }

//    public Integer getCreateTreatplan() {
//        return createTreatplan;
//    }
//
//    public void setCreateTreatplan(Integer createTreatplan) {
//        this.createTreatplan = createTreatplan;
//    }

    public Integer getControlAccess() {
        return controlAccess;
    }

    public void setControlAccess(Integer controlAccess) {
        this.controlAccess = controlAccess;
    }
}

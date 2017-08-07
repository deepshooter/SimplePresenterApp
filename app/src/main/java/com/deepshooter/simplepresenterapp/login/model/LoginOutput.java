
package com.deepshooter.simplepresenterapp.login.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginOutput {

    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("is_admin")
    @Expose
    private Boolean isAdmin;
    @SerializedName("is_student")
    @Expose
    private Boolean isStudent;
    @SerializedName("is_parent")
    @Expose
    private Boolean isParent;
    @SerializedName("is_teacher")
    @Expose
    private Boolean isTeacher;
    @SerializedName("site_id")
    @Expose
    private Integer siteId;
    @SerializedName("organisation_id")
    @Expose
    private Integer organisationId;
    @SerializedName("student_id")
    @Expose
    private Integer studentId;
    @SerializedName("parent_id")
    @Expose
    private Integer parentId;
    @SerializedName("teacher_id")
    @Expose
    private Integer teacherId;
    @SerializedName("token")
    @Expose
    private String token;

    /**
     * No args constructor for use in serialization
     * 
     */
    public LoginOutput() {
    }

    /**
     * 
     * @param isTeacher
     * @param isParent
     * @param organisationId
     * @param password
     * @param message
     * @param isStudent
     * @param parentId
     * @param siteId
     * @param token
     * @param email
     * @param studentId
     * @param isAdmin
     * @param success
     * @param teacherId
     */
    public LoginOutput(String email, String password, Boolean success, String message, Boolean isAdmin, Boolean isStudent, Boolean isParent, Boolean isTeacher, Integer siteId, Integer organisationId, Integer studentId, Integer parentId, Integer teacherId, String token) {
        super();
        this.email = email;
        this.password = password;
        this.success = success;
        this.message = message;
        this.isAdmin = isAdmin;
        this.isStudent = isStudent;
        this.isParent = isParent;
        this.isTeacher = isTeacher;
        this.siteId = siteId;
        this.organisationId = organisationId;
        this.studentId = studentId;
        this.parentId = parentId;
        this.teacherId = teacherId;
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public Boolean getIsStudent() {
        return isStudent;
    }

    public void setIsStudent(Boolean isStudent) {
        this.isStudent = isStudent;
    }

    public Boolean getIsParent() {
        return isParent;
    }

    public void setIsParent(Boolean isParent) {
        this.isParent = isParent;
    }

    public Boolean getIsTeacher() {
        return isTeacher;
    }

    public void setIsTeacher(Boolean isTeacher) {
        this.isTeacher = isTeacher;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public Integer getOrganisationId() {
        return organisationId;
    }

    public void setOrganisationId(Integer organisationId) {
        this.organisationId = organisationId;
    }

    public Integer getStudentId() {
        return studentId;
    }

    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}

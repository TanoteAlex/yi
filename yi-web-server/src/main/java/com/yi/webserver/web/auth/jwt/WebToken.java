package com.yi.webserver.web.auth.jwt;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class WebToken {

    private int id;
    private String userCode;

    private String issuer;
    private String subject;

    public WebToken() {
    }

    public WebToken(int id, String userCode, String issuer, String subject) {
        this.id = id;
        this.userCode = userCode;
        this.issuer = issuer;
        this.subject = subject;
    }

    public int getId() {
        return id;
    }

    public void setId(int userId) {
        this.id = userId;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("userCode", userCode)
                .append("issuer", issuer)
                .append("subject", subject)
                .toString();
    }
}

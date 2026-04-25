package com.iam.common.oauth;

import com.iam.common.util.FormParser;
import org.springframework.beans.factory.annotation.Autowired;

public class OauthParameters {
    private String response_type;
    private String client_id;
    private String redirect_uri;
    private String code_challenge;
    private String code_challenge_method;
    private String scope;
    private String state;

    public String getResponse_type() {
        return response_type;
    }

    public void setResponse_type(String response_type) {
        this.response_type = response_type;
    }

    public String getClient_id() {
        return client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getRedirect_uri() {
        return redirect_uri;
    }

    public void setRedirect_uri(String redirect_uri) {
        this.redirect_uri = redirect_uri;
    }

    public String getCode_challenge() {
        return code_challenge;
    }

    public void setCode_challenge(String code_challenge) {
        this.code_challenge = code_challenge;
    }

    public String getCode_challenge_method() {
        return code_challenge_method;
    }

    public void setCode_challenge_method(String code_challenge_method) {
        this.code_challenge_method = code_challenge_method;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}

package com.iam.common.oauth;

public class StateToken {
    private OauthParameters oauthParameters;
    private InternalParameter internalParameter;

    public StateToken(OauthParameters oauthParameters, InternalParameter internalParameter) {
        this.oauthParameters = oauthParameters;
        this.internalParameter = internalParameter;
    }


}

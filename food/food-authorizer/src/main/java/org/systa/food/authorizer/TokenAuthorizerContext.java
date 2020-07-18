package org.systa.food.authorizer;

import java.util.Map;

public class TokenAuthorizerContext {

	String type;
    String authorizationToken;
    String methodArn;
    Map<String, String> headers;
    

    /**
     * JSON input is deserialized into this object representation
     * @param type Static value - TOKEN
     * @param authorizationToken - Incoming bearer token sent by a client
     * @param methodArn - The API Gateway method ARN that a client requested
     */
    public TokenAuthorizerContext(String type, String authorizationToken, String methodArn) {
        this.type = type;
        this.authorizationToken = authorizationToken;
        this.methodArn = methodArn;
    }
    
    public TokenAuthorizerContext(String type, String authorizationToken, String methodArn, Map<String, String> headers) {
        this.type = type;
        this.authorizationToken = authorizationToken;
        this.methodArn = methodArn;
        this.headers = headers;
    }

    public TokenAuthorizerContext() {

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAuthorizationToken() {
        return authorizationToken;
    }

    public void setAuthorizationToken(String authorizationToken) {
        this.authorizationToken = authorizationToken;
    }

    public String getMethodArn() {
        return methodArn;
    }

    public void setMethodArn(String methodArn) {
        this.methodArn = methodArn;
    }

	public Map<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}
}

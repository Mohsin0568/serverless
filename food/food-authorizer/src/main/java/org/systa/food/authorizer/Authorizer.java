package org.systa.food.authorizer;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.systa.food.authorizer.AuthPolicy.HttpMethod;
import org.systa.food.util.JwtUtil;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class Authorizer implements RequestHandler<TokenAuthorizerContext, AuthorizerResponse>{

	public AuthorizerResponse handleRequest(TokenAuthorizerContext request, Context context) {
		
		Map<String, String> headers = request.getHeaders();
		String authorization = headers.get("Authorization");        
        String jwt = authorization.substring("Bearer ".length());
        
        Map<String, String> ctx = new HashMap<>();
        try{
        	ctx.put("userId", JwtUtil.getUserid(jwt));
        }
        catch(Exception e){
        	throw new RuntimeException("Unauthorized");
        }
        
        //String principalId = "xxxx";
        
        String methodArn = request.getMethodArn();
    	String[] arnPartials = methodArn.split(":");
    	String region = arnPartials[3];
    	String awsAccountId = arnPartials[4];
    	String[] apiGatewayArnPartials = arnPartials[5].split("/");
    	String restApiId = apiGatewayArnPartials[0];
    	String stage = apiGatewayArnPartials[1];
    	
    	String arn = String.format("arn:aws:execute-api:%s:%s:%s/%s/%s/%s",
    			region,
    			awsAccountId,
    			restApiId,
    			stage,
    			HttpMethod.ALL,
                "*");

    	Statement statement = Statement.builder()
    			.effect("Allow")
                .resource(arn)
                .build();

            PolicyDocument policyDocument = PolicyDocument.builder()
                .statements(
                    Collections.singletonList(statement)
                ).build();

    	return AuthorizerResponse.builder()
                .principalId(awsAccountId)
        	    .policyDocument(policyDocument)
                .context(ctx)
                .build();
        
        //return new AuthPolicy(principalId, AuthPolicy.PolicyDocument.getAllowAllPolicy(region, awsAccountId, restApiId, stage));
	}

}

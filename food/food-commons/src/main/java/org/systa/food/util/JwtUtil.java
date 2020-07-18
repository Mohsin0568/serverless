package org.systa.food.util;

import java.security.Key;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtUtil {
	
	private static String SECRET_KEY = "4c4e79a5-aac0-477d-b867-cfbbeb6c2510";

	public static String createJwtToken(String id, String mobileNumber, String subject){
		
		//The JWT signature algorithm we will be using to sign the token
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		
		long nowMillis = System.currentTimeMillis();
	    Date now = new Date(nowMillis);
	    
	    //We will sign our JWT with our ApiKey secret
	    byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
	    Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
	    
	    
	    // set the jwt claims
	    JwtBuilder builder = Jwts.builder().setId(id)
	            .setIssuedAt(now)
	            .setSubject(subject)
	            .setIssuer(mobileNumber)
	            .signWith(signatureAlgorithm, signingKey);

	    // add expiration -- adding current time plus 10 minutes
        long expMillis = nowMillis + (1000*600);
        Date exp = new Date(expMillis);
        builder.setExpiration(exp);
	    
        return builder.compact();
	}
	
	public static Claims decodeJWT(String jwt) {
	    //This line will throw an exception if it is not a signed JWS (as expected)
	    Claims claims = Jwts.parser()
	            .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
	            .parseClaimsJws(jwt).getBody();
	    return claims;
	}
	
	public static String getUserid(String jwt){
		Claims claims = decodeJWT(jwt);
		return claims.getId();
	}
	
	public static void main(String args[]){
		System.out.println(getUserid("eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIxMSIsImlhdCI6MTU3Mjc1Nzg0MCwic3ViIjoiNGNkNzVjY2UtYTlhZi00NmNkLTllMmYtMmRmZjZlZGQxNDE1IiwiaXNzIjoiOTAzMjU2MzA1OSIsImV4cCI6MTU3Mjc1ODQ0MH0.0jVtvBKKo1ndkG6O9gLZzq2YZ-xE5syC9yjaDacVEnI"));
	}
}

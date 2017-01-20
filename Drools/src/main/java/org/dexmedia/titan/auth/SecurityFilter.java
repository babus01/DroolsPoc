package org.dexmedia.titan.auth;

import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.glassfish.jersey.internal.util.Base64;

@Provider
public class SecurityFilter implements ContainerRequestFilter {

	private static final String AUTHORIZATION_HEADER_KEY = "Authorization";
	private static final String AUTHORIZATION_HEADER_PREFIX = "Basic ";

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		JSONObject response = new JSONObject();
		List<String> authHeader = requestContext.getHeaders().get(AUTHORIZATION_HEADER_KEY);
		if (authHeader != null && authHeader.size() > 0) {
			String authToken = authHeader.get(0);
			System.out.println(authToken);
			authToken = authToken.replaceFirst(AUTHORIZATION_HEADER_PREFIX, "");
			String decodedString = Base64.decodeAsString(authToken);
			System.out.println(decodedString);

			StringTokenizer tokenizer = new StringTokenizer(decodedString, ":");
			System.out.println(tokenizer);

			String username = tokenizer.nextToken();
			String password = tokenizer.nextToken();
			System.out.println(username);
			System.out.println(password);
			if ("user".equalsIgnoreCase(username) && "password".equalsIgnoreCase(password)) {
				return;
			}
		}

		{
			try {
				response.put("error", "User cannot access the resource");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			Response unauthorizedStatus = Response.status(Response.Status.UNAUTHORIZED).entity(response.toString())
					.build();
			requestContext.abortWith(unauthorizedStatus);

		}

	}
}

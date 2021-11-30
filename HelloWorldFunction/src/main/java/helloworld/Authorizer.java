package helloworld;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import helloworld.AuthResponse.PolicyDocument;
import helloworld.AuthResponse.Statement;

public class Authorizer implements RequestHandler<APIGatewayProxyRequestEvent, AuthResponse> {

    @Override
    public AuthResponse handleRequest(APIGatewayProxyRequestEvent requestEvent, Context context) {
        APIGatewayProxyRequestEvent.ProxyRequestContext proxyContext = requestEvent.getRequestContext();
        APIGatewayProxyRequestEvent.RequestIdentity identity = proxyContext.getIdentity();
        String arn = String.format("arn:aws:execute-api:us-east-1:%s:%s/%s/%s/%s",
                proxyContext.getAccountId(),
                proxyContext.getApiId(),
                proxyContext.getStage(),
                proxyContext.getHttpMethod(),
                "*");

        String authorization = requestEvent.getHeaders().get("Authorization");
        if (!"Hello@Authorizer".equals(authorization)) {
            return apiGatewayResponse(identity.getAccountId(), "Deny", arn);
        }
        return apiGatewayResponse(identity.getAccountId(), "Allow", arn);
    }

    private AuthResponse apiGatewayResponse(String principalId, String effect, String resource) {
        return new AuthResponse(principalId,
                new PolicyDocument("2012-10-17",
                        new Statement("execute-api:Invoke", effect, resource)));
    }
}

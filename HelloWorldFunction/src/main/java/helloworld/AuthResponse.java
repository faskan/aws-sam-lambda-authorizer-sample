package helloworld;

import com.amazonaws.services.lambda.runtime.Context;

import java.util.Arrays;
import java.util.List;

public class AuthResponse {
    public String principalId;
    public AuthResponse.PolicyDocument policyDocument;

    public AuthResponse(String principalId, AuthResponse.PolicyDocument policyDocument) {
        this.principalId = principalId;
        this.policyDocument = policyDocument;
    }

    public static class PolicyDocument {
        public String Version;
        public List<Statement> Statement;

        public PolicyDocument(String version, Statement statement) {
            this.Version = version;
            this.Statement = Arrays.asList(statement);
        }
    }

    public static class Statement {
        public String Action;
        public String Effect;
        public String Resource;

        public Statement(String action, String effect, String resource) {
            this.Action = action;
            this.Effect = effect;
            this.Resource = resource;
        }
    }
}

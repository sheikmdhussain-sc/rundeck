package org.rundeck.jaas.jetty

import org.eclipse.jetty.util.security.Credential
import org.eclipse.jetty.util.security.CredentialProvider
import org.springframework.security.crypto.bcrypt.BCrypt


class BcryptCredentialProvider implements CredentialProvider {
    class BcryptCredential extends Credential {
        private static final String __TYPE = "BCRYPT:";

        String hash

        BcryptCredential(String hash) {
            super()
            this.hash = hash.substring(__TYPE.length())
        }

        boolean check(Object credentials) {
            return BCrypt.checkpw(credentials as String, hash)
        }
    }

    String getPrefix() {
        return BcryptCredential.__TYPE
    }

    @Override
    Credential getCredential(String credential) {
        return new BcryptCredential(credential)
    }
}


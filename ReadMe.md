#### Application requirements

1. Spring Security should be used as a security framework.
2. Application should support only stateless user authentication and verify integrity of JWT token.
3. Users should be stored in a database with some basic information and a password.

User Permissions:

     - Guest:
        * Read operations for main entity.
        * Signup.
        * Login.
     - User:
        * Make an order on main entity.
        * All read operations.
     - Administrator (can be added only via database call):
        * All operations, including addition and modification of entities.

4. Get acquainted with the concepts Oauth2 and OpenId Connect
5. (Optional task) Use Oauth2 as an authorization protocol.
   a. OAuth2 scopes should be used to restrict data.
   b. Implicit grant and Resource owner credentials grant should be implemented.
6. (Optional task) It's allowed to use Spring Data. Requirement for this task - all repository (and existing ones) should be migrated to Spring Data.

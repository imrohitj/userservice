# userservice Spring Boot Spring Security Application


# Build instructions
  * create mysql db, you can find the details /src/main/resources/application.properties
  * run mvn clean install
  * run UserserviceApplication


# Usage instructions

* On Application startup following entry inserted into database

  Role table
   * +----+------------------+
        | id | name             |
        +----+------------------+
        |  2 | ROLE_ADMIN       |
        |  3 | ROLE_SUPER_ADMIN |
        |  1 | ROLE_USER        |
        +----+------------------+

* User table
  *  +--------------------------------------+------------------------+------------+-----------+--------------------------------------------------------------+
        | user_id                              | email                  | first_name | last_name | password                                                     |
        +--------------------------------------+------------------------+------------+-----------+--------------------------------------------------------------+
        | 8b977bea-786a-4b3c-a028-6400234dd63b | first.last_2@gmail.com | First_2    | Last_1    | $2a$10$bB/6rsLnzAykGCRhygpOvOJv4nBg5yCuhIcJwQhROu5uZCIZQA.0u |
        | 999952fd-3a5b-4e65-bcea-2dca21a98e5b | first.last_1@gmail.com | First_1    | Last_1    | $2a$10$x7Mo/KPbFVK/GVx1JGqUdu9oPnSLUck4S0c8B0JtkKLaVh50M6wuO |
        | e0e80363-7838-4e82-9595-53ccd2c69e5d | first.last_3@gmail.com | First_3    | Last_1    | $2a$10$7kgwS5g/aSkTRoXbTH6K4urBgOrC8pKe1aPNRRut8a78oHCngCkV2 |



*  user_roles table;
 *    +--------------------------------------+----------+
      | user_user_id                         | roles_id |
      +--------------------------------------+----------+
      | 887a9efe-9aef-46bc-9191-1dba5ce79f13 |        2 |
      | 887a9efe-9aef-46bc-9191-1dba5ce79f13 |        1 |
      | 887a9efe-9aef-46bc-9191-1dba5ce79f13 |        1 |
      | 887a9efe-9aef-46bc-9191-1dba5ce79f13 |        3 |
      | 4eafc1cd-7f02-40c0-bcf4-4be44f85c7d0 |        1 |
      | 4eafc1cd-7f02-40c0-bcf4-4be44f85c7d0 |        2 |
      | f43fd111-b559-4985-8319-34fe37ae273d |        1 |
      | f43fd111-b559-4985-8319-34fe37ae273d |        3 |
      | aed5c786-09a7-4130-be77-305b5249daca |        1 |
      +--------------------------------------+----------+


* API

   * User Sign up API - localhost:8080/api/registration -
                    Body {
                       "firstName":"First_1",
                       "lastName":"Last_1",
                       "password":"12345",
                       "email":"first.last_1@yahoo.com"
                     }

   * User login API - localhost:8080/api/login -
   Body username:first.last_1@gmail.com, password:12345
   Note: If valid user id and password it will return access and refresh JWT token in header.

   * Get Uer Details by User Id - localhost:8080/api/user/getById?userId=[user id] with JWT token
   Header - Authorization= Bearer [JPA Token]

   * Search by email id - localhost:8080/api/users/searchByEmail?email=first
   Header - Authorization= Bearer [JPA Token]

   * Assign role to user -
   Header - Authorization= Bearer [JPA Token]
   userid=[valid useremail], role=[valid role name]


# What else you would have implemented had you more time to work on it

   1. Get list of all users with pagination and sorting (I have implemented basic list all user API).
   2. Sorting and pagination on search result.
   3. Email validation
   4. Encrypt "secret" while generating JWT token.
   5. Email confirmation on successful signup.
   6. Ability to provide update user details like First Name, Last Name,
   7. Ability to provide forgot password and reset password functionality.

# List down all assumptions you made while designing/implementing

   1. Every user have unique email id.
   2. We have 3 user role
      ROLE_USER - Default role to a every user
      ROLE_ADMIN - User able to search by all users by email id and userid.
      ROLE_SUPER_ADMIN - User allow to add new application role.
   3. The default role assign to user is ROLE_USER

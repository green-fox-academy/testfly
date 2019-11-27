# Rueppellii-Tribes-Backend1

Environment variables:

**DEV:**

| Key | Value |
| --- | ----- | 
|DATASOURCE_URL | jdbc:mysql://localhost/yourDBname |
|DATASOURCE_USERNAME | *your local mysql username* |
|DATASOURCE_PASSWORD | *your local mysql password* |
|HIBERNATE_DIALECT | org.hibernate.dialect.MySQL57Dialect|
|DATABASE_HOST | *your own localhost so far /NOT USED YET/* |
|DATABASE_PORT | *the port you've set /NOT USED YET/* |
|PROFILE | dev |
|TRIBES_TOKEN_SIGNING_KEY | *a signing key specified by you* |
|TRIBES_LOG_LVL | *select on of these: DEBUG, INFO or ERROR* |

**HEROKU:**

| Key | Value |
| --- | ----- | 
|DATABASE_URL | postgres://username:password@host:port/database |
|DATASOURCE_USERNAME | username
|DATASOURCE_PASSWORD | password |
|DATASOURCE_URL | jdbc:postgresql://host:port/database |
|HIBERNATE_DIALECT | org.hibernate.dialect.PostgreSQLDialect|
|PROFILE | heroku |
 _________________________________________________________________

COMMON ERRORS OCCURRING IN FLYWAY:

1)	<strong>Schema-validation: missing table [hibernate_sequence]</strong>
<br>
Reason: magic
<br>
Solution: set GeneationType = IDENTITY in all models

2)	<strong>Schema-validation: missing table [anything_else] </strong>
<br>
Reason: the models are different from the generated SQL codes,
thus Flyway throws an error instead of generating the tables.
Sometimes the problem is not with the table specified in the
error message, but with another one.
<br>
Solution:
<br>
a) Check if all your models are created, have proper fields, 
have the @Entity annotation and joined correctly.
<br>
b) Turn Flyway off (build.gradle dependencies + application.properties), use create-drop 
instead of validate, run the app, and let Hibernate generate the SQL code
<br>
c) Change back to validate. Insert the code into the SQL table 
from MySQL Workbench or from IntelliJ. Check if it's working. 
If not, repeat the whole process from point a).
<br>
d) Make the SQL code cleaner by removing the unnecessary parts and renaming the
randomly generated UQ or FQ names.
<br>
e) check if your code works after 

3)	<strong>Migration checksum mismatch for migration version: X.Y</strong>
<br>
Reason: flyway_schema_history logs different checksum 
from the actual checksum in the correct version.
It means You might have made some changes in your X.Y. version
<br>
Solution: drop database; create database;

4)	<strong>Detected failed migration to version: X.Y</strong>
<br>
Reason: something is amiss in your database.
<br>
Solution: drop database; create database;

5)	<strong>@OneToOne or @ManyToOne on com.example.demo.x.y 
references an unknown entity: com.example.demo.x </strong>
<br>
Reason: it's not a Flyway error :-). Your @Entity annotation is missing.
<br>
Solution: Insert @Entity.

6) <strong>If some SQL lines are mentioned in the error message, 
the problem is usually with the SQL Syntax itself or your model structure.</strong>

# the first stage of our build will use a maven 3.6.1 parent image
FROM maven:3.6.1-jdk-8-alpine AS MAVEN_BUILD

# copy the pom and src code to the container
COPY ./ ./

# package our application code
RUN mvn clean install

# the second stage of our build will use tomcat 8 on alpine
FROM tomcat:8.5-alpine

# copy only the artifacts we need from the first stage and discard the rest
COPY --from=MAVEN_BUILD ./recipe-web/target/recipe-web.war  /usr/local/tomcat/webapps/recipe-web.war

CMD ["catalina.sh", "run"]









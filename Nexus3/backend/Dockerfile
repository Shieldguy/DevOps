FROM openjdk:11-jre-slim-buster

RUN groupadd -g 500 was && \
    useradd -ms /bin/bash -d /home/was -u 500 -g 500 was

# for gradle
ADD target/libs/msa-demo-*.jar /home/was/spring-application.jar
# for maven
ADD target/msa-demo-*.jar /home/was/spring-application.jar
RUN chown -R was:was /home/was/spring-application.jar

EXPOSE 8080

USER was
WORKDIR /home/was

CMD ["java", "-jar", "spring-application.jar"]

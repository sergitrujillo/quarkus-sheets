FROM openjdk:8-jre-slim
RUN mkdir -p /opt/app/lib
COPY target/lib/*.jar /opt/app/lib/
COPY target/chapter4-0.1-runner.jar /opt/app/
EXPOSE 8080
CMD ["java", "-jar", "/opt/app/chapter4-0.1-runner.jar"]
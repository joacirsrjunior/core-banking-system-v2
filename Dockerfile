FROM openjdk:11-jre-slim
EXPOSE 8084
EXPOSE 15432
WORKDIR ./
ADD /build/libs/core-banking-0.0.1-SNAPSHOT.jar /opt/core-banking-system.jar
CMD exec java -jar /opt/core-banking-system.jar
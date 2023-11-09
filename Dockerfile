FROM openjdk:17-jdk
EXPOSE 8089
WORKDIR /app
ADD target/gestion-station-ski-1.0.jar sidaouimohamedamine/gestion-station-ski.jar
ENTRYPOINT ["java","-jar","/app/gestion-station-ski.jar"]
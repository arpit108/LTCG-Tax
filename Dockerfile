FROM java:8
WORKDIR /
ADD ltcg.jar ltcg.jar
EXPOSE 8080
CMD ["java","-jar","ltcg.jar"]


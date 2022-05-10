FROM maven:3.8.5-jdk-11
WORKDIR /store
COPY . .
RUN mvn clean install
CMD mvn spring-boot:run
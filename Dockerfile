FROM eclipse-temurin:21

WORKDIR /app

COPY . .

RUN mvn clean package -DskipTests

EXPOSE 5454

CMD ["java", "-jar", "target/*.jar"]
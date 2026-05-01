# ===== BUILD STAGE =====
FROM maven:3.9.6-eclipse-temurin-21 AS build

WORKDIR /app

COPY . .

WORKDIR /app/Ecommerce

RUN mvn clean package -DskipTests

# ===== RUN STAGE =====
FROM eclipse-temurin:21

WORKDIR /app

COPY --from=build /app/Ecommerce/target/*.jar app.jar

EXPOSE 5454

ENTRYPOINT ["java","-jar","app.jar"]
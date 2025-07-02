# Build image
FROM docker.io/library/maven:3.9-eclipse-temurin-17 AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests # 設置工作目錄
WORKDIR /app

# 構建應用程式（如果在 Render 上構建）
FROM docker.io/library/eclipse-temurin:17-jdk-alpine
WORKDIR /app

# 或者如果你已經在本地構建，只需複製 JAR 文件
COPY --from=builder /app/target/*.jar app.jar

# 暴露端口（與你的 application.properties 中設置的相同）
ENV SPRING_PROFILES_ACTIVE=prod

# 啟動應用程式
ENTRYPOINT ["java", "-jar", "app.jar"]
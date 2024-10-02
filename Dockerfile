# OpenJDK 17 기반 이미지 사용
FROM openjdk:17-jdk-alpine

# JAR 파일을 컨테이너 내 /app/app.jar 경로에 복사
COPY build/libs/X-0.0.1-SNAPSHOT.jar /app/app.jar

# 애플리케이션 실행 (prod 프로파일 사용)
ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "/app/app.jar"]

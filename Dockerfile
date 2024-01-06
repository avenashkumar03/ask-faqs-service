FROM amd64/eclipse-temurin:17-alpine

WORKDIR /apps/ask-faqs-service

COPY ./build/libs/ask-faqs-service-0.0.1-SNAPSHOT.jar /apps/ask-faqs-service/ask-faqs-service.jar

RUN ln -snf /usr/share/zoneinfo/America/New_York /etc/localtime && echo "America/New_York" > /etc/timezone

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=local", "ask-faqs-service.jar"]

EXPOSE 80

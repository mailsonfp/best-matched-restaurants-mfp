FROM alpine:3.19
RUN apk add openjdk17-jre
CMD java -version
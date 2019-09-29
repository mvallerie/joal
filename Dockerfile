# Builder image with jdk
FROM --platform=$BUILDPLATFORM maven:3.6-jdk-8-alpine AS build

RUN apk add git \
    && JOAL_VERSION="2.1.19" \
    && git clone https://github.com/anthonyraymond/joal.git --branch "$JOAL_VERSION" --depth=1 \
    && cd joal \
    && mvn --batch-mode --quiet package -DskipTests=true \
    && mkdir /artifact \
    && mv "/joal/target/jack-of-all-trades-$JOAL_VERSION.jar" /artifact/joal.jar \
    && apk del git \
    && rm -rf /var/cache/apk/*

# Actual joal image with jre only
FROM openjdk:8u181-jre-alpine

LABEL name="joal"
LABEL maintainer="joal.contact@gmail.com"
LABEL url="https://github.com/anthonyraymond/joal"
LABEL vcs-url="https://github.com/anthonyraymond/joal"

WORKDIR /joal/

COPY --from=build /artifact/joal.jar /joal/joal.jar

VOLUME /data

ENTRYPOINT ["java","-jar","/joal/joal.jar"]
CMD ["--joal-conf=/data"]


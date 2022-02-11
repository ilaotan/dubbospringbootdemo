FROM registry.cn-hangzhou.aliyuncs.com/sonli-public/jdk-alpine:8u301 as builder
WORKDIR application
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} application.jar
RUN java -Djarmode=layertools -jar application.jar extract

FROM registry.cn-hangzhou.aliyuncs.com/sonli-public/jdk-alpine:8u301

WORKDIR application
COPY --from=builder application/dependencies/ ./
COPY --from=builder application/spring-dependencies/ ./
COPY --from=builder application/snapshot-dependencies/ ./
COPY --from=builder application/sonli-dependencies/ ./
COPY --from=builder application/spring-boot-loader/ ./
COPY --from=builder application/application/ ./

# 时区设置 或者在k8s中做主机-容器 文件映射.
#RUN ln -fs /usr/share/zoneinfo/Asia/Shanghai /etc/localtime

ENV JAVA_OPTS="-Xms256m -Xmx256m -Xmn128m"
ENV SPRING_OPTS=""



ENTRYPOINT ["/usr/bin/dumb-init", "--"]
#CMD java ${JAVA_OPTS} ${SPRING_OPTS} ${DEBUG_OPTS} org.springframework.boot.loader.JarLauncher > /dev/null 2>&1
CMD java ${JAVA_OPTS} org.springframework.boot.loader.JarLauncher ${SPRING_OPTS}



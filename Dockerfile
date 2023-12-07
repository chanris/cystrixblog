FROM amazoncorretto:17-alpine-jdk
MAINTAINER chenyue
EXPOSE 80
ENV RUNPATH /home/chenyue/cb
# 登录进容器的默认路径
WORKDIR $RUNPATH
VOLUME /home/chenyue/cb/logs
# 将宿主机上目录下的文件拷贝进镜像
ADD ./cystrixblog-1.0.0.jar cystrixblog.jar
# 文件不存在时，创建文件
RUN bash -c 'touch /cystrixblog.jar'
ENTRYPOINT ["java", "-jar", "/cystrixblog.jar", "--spring.profiles.active=pro"]
# start with opencv java
FROM stonerworx/opencv-java

# create the application directory and copy the local files there
RUN mkdir -p /home/opensurf-java
WORKDIR /home/opensurf-java
COPY ./ /home/opensurf-java/

RUN gradle build

#copy native library to java.library.path
RUN cp /home/opensurf-java/libs/native/libopensurf_java.so /usr/lib

#copy java library
RUN cp /home/opensurf-java/build/libs/opensurf-1.0.jar /home/javalibs/opensurf-1.0.jar

# clean up after yourself
RUN apt-get clean && rm -rf /var/lib/apt/lists/* /tmp/* /var/tmp/*
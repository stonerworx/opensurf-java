# start with opencv java
FROM stonerworx/opencv-java

# create the application directory and copy the local files there
RUN mkdir -p /home/user/opensurf-java
WORKDIR /home/user/opensurf-java
COPY ./ /home/user/opensurf-java/

# copy the opencv jar to our application
RUN cp /home/user/opencv/build/bin/opencv-2411.jar /home/user/opensurf-java/libs/opencv-2411.jar
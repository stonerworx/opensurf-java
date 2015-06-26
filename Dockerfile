# start with opencv java
FROM stonerworx/opencv-java

# create the application directory and copy the local files there
RUN mkdir -p /home/user/app
WORKDIR /home/user/app
COPY ./ /home/user/app/

# copy the opencv jar to our application
RUN cp /home/user/opencv/build/bin/opencv-2411.jar /home/user/app/libs/opencv-2411.jar
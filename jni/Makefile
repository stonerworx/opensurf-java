# Indicates compilator to use
CC      = g++

# Specifies compilator options
FLAGS  = -w -O3 -I/opt/local/include/opencv -I/opt/local/include -L/opt/local/lib -lopencv_calib3d -lopencv_contrib -lopencv_core -lopencv_features2d -lopencv_flann -lopencv_gpu -lopencv_highgui -lopencv_imgproc -lopencv_legacy -lopencv_ml -lopencv_nonfree -lopencv_objdetect -lopencv_photo -lopencv_stitching -lopencv_superres -lopencv_ts -lopencv_video -lopencv_videostab -I/System/Library/Frameworks/JavaVM.framework/Headers -I./

# Name of the library
LIB = ../libs/native/libopensurf.dylib

# Object files .o necessary to build the main program
OBJS  = fasthessian.o integral.o utils.o ipoint.o surf.o
 
all: $(OBJS)
	$(CC) $(FLAGS) -o $(LIB) $(OBJS) -ldl -shared -fPIC opensurf.cpp
	-rm -f *.o

%.o: $(addprefix ./, %.cpp)
	$(CC) $(FLAGS) -c $< -o $@

clean:
	-rm -f $(LIB)
	-rm -f *.o

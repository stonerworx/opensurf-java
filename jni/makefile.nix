# Indicates compiler to use
CC      = g++

# Path to JNI header files
JNILIBS = -I/usr/lib/jvm/java-8-oracle/include -I/usr/lib/jvm/java-8-oracle/include/linux

# Specifies compiler options
CFLAGS  = -O3 -Wall -fPIC `pkg-config --cflags opencv` -D LINUX
LDFLAGS =
LDLIBS  = `pkg-config --libs opencv`

# Files extensions .cpp, .o
SUFFIXES = .cpp .o
.SUFFIXES: $(SUFFIXES) .

# Name of the main program
#PROG  = opensurf

# Name of the library
LIB = ../libs/native/libopensurf_java.so

# Object files .o necessary to build the main program
OBJS  = fasthessian.o integral.o surf.o utils.o ipoint.o

# Object files .o necessary to build the library
LIBOBJS  = fasthessian.o integral.o surf.o utils.o ipoint.o

all: $(LIB)

$(LIB): $(LIBOBJS)
	$(CC) $(LIBOBJS) $(JNILIBS) $(LDLIBS) -o $(LIB) -ldl -shared -fPIC opensurf.cpp

%.o: $(addprefix ./, %.cpp)
	$(CC) $(CFLAGS) -c $< -o $@

clean:
	-rm -f $(PROG)
	-rm -f $(LIB)
	-rm -f *.o

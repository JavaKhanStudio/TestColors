cmake_minimum_required(VERSION 3.4.1)

set(CMAKE_CXX_STANDARD 11)

file(GLOB_RECURSE SOURCES src/main/cpp/*.cpp)

add_library(filter SHARED ${SOURCES})

target_link_libraries(filter log)

#include <emscripten.h>
#include <iostream>
#include "types.h"

extern "C" {
  EMSCRIPTEN_KEEPALIVE int test(){
    std::cout << "Test CPP in web" <<std::endl;
    return 0;
  }
}

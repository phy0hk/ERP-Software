#include <emscripten/bind.h>
#include <emscripten/emscripten.h>
#include <iostream>
#include "inventoryTypes.h"
using namespace emscripten;


std::vector<int> getAllChildIDs(const LocationType& parentNode) {
    std::vector<int> result;
    const std::vector<LocationType>& children = parentNode.children;
    for (const LocationType& child : children) {
      result.push_back(child.id);
        // Recursive call
      std::vector<int> subChildIDs = getAllChildIDs(child);
      result.insert(result.end(), subChildIDs.begin(), subChildIDs.end());
    }
    return result;
}

std::vector<LocationType> plantTree(){

}

LocationType getNodeByID(const int& id,const std::vector<LocationType>& Nodes){
  for(const LocationType Node : Nodes){
    if(Node.id == id) return Node;
    LocationType found = getNodeByID(id,Node.children);
    if(found.id==-1) return found;
  }
  return LocationType{};
}

EMSCRIPTEN_BINDINGS(inventory_module) {
    // Bind LocationType
   class_<LocationType>("LocationType")
     .constructor<>()
        .property("id", &LocationType::id)
        .property("name", &LocationType::name)
        .property("parentId", &LocationType::parentId)
        .property("type", &LocationType::type)
        .property("code", &LocationType::code)
        .property("description", &LocationType::description)
        .property("created_at", &LocationType::created_at)
        .property("updated_at", &LocationType::updated_at)
        .property("children", &LocationType::children);
    // Bind vector types
    register_vector<int>("VectorInt");
    register_vector<LocationType>("VectorLocation");

    // Bind function
    function("getAllChildIDs", &getAllChildIDs);
    function("plantTree", &plantTree);
    function("getNodeByID",&getNodeByID);
    function("toJs",&toJs);
}

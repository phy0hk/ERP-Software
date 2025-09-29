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
      std::vector<int> subChildIDs = getAllChildIDs(child);
      result.insert(result.end(), subChildIDs.begin(), subChildIDs.end());
    }
    return result;
}

std::vector<LocationType> PlantTree(const std::vector<LocationType>& Locations,
                                    const std::vector<LocationType>& AllLocations) {
    std::vector<LocationType> data;
    for (const auto& location : Locations) {
        LocationType parent = location; // copy to modify children
        std::vector<LocationType> children;

        // Filter AllLocations where parentId matches
        std::copy_if(AllLocations.begin(), AllLocations.end(),
                     std::back_inserter(children),
                     [&parent](const LocationType& l) { return l.parentId == parent.id; });

        if (!children.empty()) {
            parent.children = PlantTree(children, AllLocations); // recursive call
        }

        data.push_back(parent);
    }

    return data;
}
LocationType getNodeByID(const int& id, const std::vector<LocationType>& nodes) {
    for (const auto& node : nodes) {
      //if node is found return it
        if (node.id == id) return node;

        if (!node.children.empty()) {
          //if node children is not empty continue finding in child recursively
            LocationType found = getNodeByID(id, node.children);
            if (found.id != -1) return found;
        }
    }
    return LocationType();
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
    function("PlantTree", &PlantTree);
    function("getNodeByID",&getNodeByID);
    function("toJs",&toJs);
}

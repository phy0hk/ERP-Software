#ifndef TYPES_H
#define TYPES_H

#include <vector>
#include <string>
#include <cstdint>

struct LocationType {
    int id = 0;
    std::string name = "";
    int parentId = 0;
    std::string type = "";
    std::string code = "";
    std::string description = "";
    double created_at = 0;
    double updated_at = 0;
    std::vector<LocationType> children = {};
    LocationType() = default;
};

LocationType toJs(const LocationType& loc){
  LocationType jsLoc;
  jsLoc.id = loc.id;
    jsLoc.name = loc.name;
    jsLoc.parentId = loc.parentId;
    jsLoc.type = loc.type;
    jsLoc.code = loc.code;
    jsLoc.description = loc.description;
    jsLoc.created_at = static_cast<double>(loc.created_at);
    jsLoc.updated_at = static_cast<double>(loc.updated_at);
    for (const auto& c : loc.children)
        jsLoc.children.push_back(toJs(c));
    return jsLoc;
}

#endif

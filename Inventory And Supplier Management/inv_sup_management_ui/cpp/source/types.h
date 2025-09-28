#ifndef TYPES_H
#define TYPES_H

#include <vector>
#include <string>
#include <optional>
#include <ctime>

struct LocationType {
int id;
std::string name;
std::optional<int> parentId;
std::optional<std::string> type;
std::optional<std::string> code;
std::optional<std::string> description;
std::optional<std::time_t> created_at;
std::optional<std::time_t> updated_at;
std::optional<std::vector<LocationType>> children;
};

#endif

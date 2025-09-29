import type LocationType from "./TypesList";
import useWasm from "./wasmLoader";
let inventoryWASML:any|null = null;
export function toWasmLocationType(loc: LocationType): any {
  if(!inventoryWASM) useWasm("/cpp/inventory_cpp.js");
  const wasmLoc = new inventoryWASM.LocationType();
  wasmLoc.id = loc.id;
  wasmLoc.name = loc.name;
  wasmLoc.parentId = loc.parentId ?? 0;
  wasmLoc.type = loc.type ?? "";
  wasmLoc.code = loc.code ?? "";
  wasmLoc.description = loc.description ?? "";
  wasmLoc.created_at = loc.created_at ? new Date(loc.created_at) : 0;
  wasmLoc.updated_at = loc.updated_at ? new Date(loc.updated_at) : 0;

  // Construct children as a WASM VectorLocation
  const wasmChildren = new inventoryWASM.VectorLocation();
  (loc.children ?? []).forEach(child => wasmChildren.push_back(toWasmLocationType(child)));
  wasmLoc.children = wasmChildren;

  return wasmLoc;
}

//transform Wasm LocationType obj to Js LocationType obj
export function toJsLocationType(obj:any):LocationType{
  const inventoryWASM = useWasm("/cpp/inventory_cpp.js")
  if(!inventoryWASM) return;
    const Loc:LocationType = {};
    Loc.id = obj.id
    Loc.name = obj.name,
    Loc.parentId = obj.parentId;
    Loc.type = obj.type;
    Loc.code = obj.code;
    Loc.description = obj.description;
    Loc.created_at = obj.created_at;
    Loc.updated_at = obj.updated_at;
    const tempChild:LocationType[] = [];
    for(let i = 0;i<obj.children.size();i++){
      tempChild.push(toJsLocationType(obj.children.get(i)));
    }
    Loc.children = tempChild;
    return Loc;
}


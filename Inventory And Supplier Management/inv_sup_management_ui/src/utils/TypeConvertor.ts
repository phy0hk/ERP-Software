import type {LocationType} from "./TypesList";
import useWasm from "./wasmLoader";


export async function toWasmLocationType(loc: LocationType):Promise<any> {
  const inventoryWASM = await useWasm("/cpp/inventory_cpp.js")
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
  if (loc.children && loc.children.length > 0) {
    for (const child of loc.children) {
      const wasmChild = await toWasmLocationType(child); // await here
      wasmChildren.push_back(wasmChild);
    }
  }
  wasmLoc.children = wasmChildren;
  return wasmLoc;
}

//transform Wasm LocationType obj to Js LocationType obj
export async function toJsLocationType(obj:any):Promise<LocationType>{
  const inventoryWASM = await useWasm("/cpp/inventory_cpp.js")
    const Loc:LocationType = {
      id: 0,
      name: "",
      parentId: null
    };

    Loc.id = obj.id
    Loc.name = obj.name,
    Loc.parentId = obj.parentId;
    Loc.type = obj.type;
    Loc.code = obj.code;
    Loc.description = obj.description;
    Loc.created_at = obj.created_at;
    Loc.updated_at = obj.updated_at;
    const tempChild:LocationType[] = [];
    const childrenSize:number = obj.children.size();
   
    for(let i = 0;i<obj.children.size();i++){     

      toJsLocationType(obj.children.get(i)).then((data)=>{
        tempChild.push(data);
      })
    }
    Loc.children = tempChild;
    return Loc;
}

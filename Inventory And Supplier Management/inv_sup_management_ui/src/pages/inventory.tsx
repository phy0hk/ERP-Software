import { useEffect, useState, useRef } from "react";
import RootLayout from "../layout/layout";
import { getAllLocationsURL,getChildLocationsURL,getInventoryItemsWithIdURL } from "../utils/APILinks";
import type { LocationType } from "../utils/TypesList";
import {Map} from "lucide-react";
import WarehouseSideBar from "../components/inventory/WarehouseSideBar"
import {Table} from "../components/common/table"
import useWasm from "../utils/wasmLoader"
import {toWasmLocationType,toJsLocationType} from "../utils/TypeConvertor"
export default function InventoryPage(){
  const [BigTree,setBigTree] = useState<LocationType[]>([]);
  const [AllLocationsList,setAllLocationsList] = useState<LocationType[]>([])
  const [locationDatas,setLocationDatas] = useState<string[][]|null|undefined>(null);
  const [sideOpen,setSideOpen] = useState<boolean>(false);
  const [selectedLocation,setSelectedLocation] = useState<number|null>(null);
  const [inventoryHeaders,setInventoryHeaders] = useState<string[]>(["Inventory ID","Product ID","Product Name","Location ID","Qty","Reserved Qty"]);
  const [locatoinHeders,setLocationHeaders] = useState<string[]>(["ID","Name","Type","Code","Description"]);
  const [inventoryItems,setInventoryItems] = useState<string[][]|undefined>([]);
  const [inventoryWASM,setInventoryWASM] = useState<any>(null);
  const parentTableRef = useRef<HTMLDivElement>(null);
  const [DynamicTableWidth,setDynamicTableWidth] = useState(1000);
 async function getAllLocations(){    
      const res = await fetch(getAllLocationsURL());
      
      try {
          const resJson = await res.json();
          const datas:LocationType[] = resJson.data;
          setAllLocationsList(datas)
      } catch (error) {
          console.log(error);
          return;
      }
  }

  //Load Wasm for InventoryPage
  useEffect(()=>{
    useWasm("/cpp/inventory_cpp.js").then((module)=>{
      setInventoryWASM(module)
    }).catch((e)=>{
      console.log(e);
    })
  },[])


  useEffect(()=>{
    if(inventoryWASM!==null && inventoryWASM!== undefined && AllLocationsList.length>0){
   plantBigTree();
    }
  },[inventoryWASM,AllLocationsList])

  async function plantBigTree(){
        const filteredList = AllLocationsList.filter((item)=>item.parentId===null);
          const filteredWasmList = new inventoryWASM.VectorLocation();
          const AllLocationWasmList = new inventoryWASM.VectorLocation();         
          
          for(const item of filteredList){
            filteredWasmList.push_back(await toWasmLocationType(item))            
          }
          for(const item of AllLocationsList){
            AllLocationWasmList.push_back(await toWasmLocationType(item))
          };
          const jsonBigTree:LocationType[] = [];
          const createdTree = inventoryWASM.PlantTree(filteredWasmList,AllLocationWasmList);
          for(let i=0;i<createdTree.size();i++){
            jsonBigTree.push(await toJsLocationType(createdTree.get(i)));
          }
          setBigTree(jsonBigTree);
  }
  // function PlantTree(Locations:LocationType[],AllLocations:LocationType[]):LocationType[]{
  //     return Locations.map((parent)=>{
  //         const children:LocationType[] = AllLocations.filter(item=>item.parentId===parent.id)
  //         if(children.length>0){
  //             parent.children = PlantTree(children,AllLocations);
  //         }else{
  //             parent.children = undefined;
  //         }
  //         return parent;
  //     })
  // }   

  useEffect(()=>{
    getAllLocations();      
  },[])


const fetchChildLocations=async (id:number)=>{  
  try {
    const res = await fetch(getChildLocationsURL(id));
    const jsonData = await res.json();
    const data = await jsonData.data;
    let temp:string[][] = [];
for(const item of data){
  temp.push([item.id,item.name,item.type,item.code,item.description]) 
}

const BigTreeTrasnform = new inventoryWASM.VectorLocation();
for(const Branch of BigTree){
  BigTreeTrasnform.push_back(await toWasmLocationType(Branch))
}
const findNode = inventoryWASM.getNodeByID(id,BigTreeTrasnform);
const Node:LocationType = await toJsLocationType(findNode);

const childIDs = [];
const WasmNode = await toWasmLocationType(Node);

const tempChildIds = inventoryWASM.getAllChildIDs(WasmNode)


for(let i=0;i<tempChildIds.size();i++){
 childIDs.push(tempChildIds.get(i));
}

setLocationDatas(temp);
let invItems:string[][] = [];

if(childIDs===undefined) setInventoryItems(await fetchInventoryItems(id));
else{
for(const cid of childIDs){
  const invTemp:string[][] = await fetchInventoryItems(cid);
  invItems = [...invItems,...invTemp]
}
setInventoryItems(invItems)
}
  } catch (error) {
    console.error(error);
    
  }
}

const fetchInventoryItems:(id:number)=>Promise<string[][]|undefined>=async (id:number)=>{
  try {
    const res = await fetch(getInventoryItemsWithIdURL(id));
    const jsonData:any = await res.json();
    const data:any = jsonData.data;
    
    let temp:string[][] = [];
    for(const item of data){
      temp.push([item.id,item.productId,"",item.locationId,item.quantity,item.reserved|0]);
    }
    return temp;
  } catch (error) {
    console.error(error);
  }
}


// const fetchNodeByID = (id:number,Nodes:LocationType[]) =>{
//    if(Nodes===undefined) return;
//    for(const Node of Nodes){    
//      if(Node.id == id) return Node;
//      if(Node.children!==undefined) {
//       const found = fetchNodeByID(id,Node.children)
//       if(found) return found;
//      };
//    }
// }

useEffect(()=>{
if(parentTableRef.current){
setDynamicTableWidth(parentTableRef.current.offsetWidth+1000)
console.log(parentTableRef.current.offsetWidth);
}
},[parentTableRef])

const handleSidebarClose = ()=>{
setSideOpen(!sideOpen);
}
useEffect(() => {
  console.log(DynamicTableWidth);
  
}, [DynamicTableWidth])


  return(
        <RootLayout>
            <div className="w-screen h-[100vh] scrollbar-hide relative flex flex-row">
              <WarehouseSideBar visible={sideOpen} onClose={handleSidebarClose} onSelect={fetchChildLocations} Tree={BigTree} />
              <div className={`p-5 flex flex-col gap-10 overflow-auto w-full`} >
            <div className={`w-full ${locationDatas && locationDatas.length>0?"":"hidden"}`}>
            <div className="font-bold text-md h-10 text-grayscale">Sub Locations</div>
            <Table colResize={true} rowResize={true} RowValues={locationDatas} rowHeights={80} ColumnNames={locatoinHeders}/>
            {/* <TableViewChildren/>
             <TableViewProducts/>*/}
            </div>
            <div className={`w-full`}>
            <div className={`font-bold text-md h-10 text-grayscale`}>
            Inventory
            </div>
            <Table rowHeights={80} RowValues={inventoryItems} rowResize={true} ColumnNames={inventoryHeaders} colResize={true}/>
            </div>
            </div>
           </div>
<button  className={`fixed left-5 bottom-5 bg-grayscale/10 active:bg-grayscale/30 p-3 rounded-full transition delay-100 ease-in-out sm:hidden ${sideOpen?"-translate-x-100":""}`} onClick={handleSidebarClose}><Map size={20}/></button>
        </RootLayout>
    )
}





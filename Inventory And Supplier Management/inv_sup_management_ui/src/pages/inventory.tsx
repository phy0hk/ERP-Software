import { useEffect, useState } from "react";
import RootLayout from "../layout/layout";
import { getAllLocationsURL,getChildLocationsURL,getInventoryItemsWithIdURL } from "../utils/APILinks";
import type { LocationType } from "../utils/TypesList";
import {Map} from "lucide-react";
import WarehouseSideBar from "../components/inventory/WarehouseSideBar"
import {Table} from "../components/common/table"

export default function InventoryPage(){
  const [BigTree,setBigTree] = useState<LocationType[]>([]);
  const [locationDatas,setLocationDatas] = useState<string[]>([]);
  const [sideOpen,setSideOpen] = useState<boolean>(false);
  const [selectedLocation,setSelectedLocation] = useState<number|null>(null);
  const [inventoryHeaders,setInventoryHeaders] = useState<string[]>(["Inventory ID","Product ID","Product Name","Location ID","Qty","Reserved Qty"]);
  const [locatoinHeders,setLocationHeaders] = useState<string[]>(["ID","Name","Type","Code","Description"]);
  const [inventoryItems,setInventoryItems] = useState<string[]>([]);

 async function getAllLocations(){
      const res = await fetch(getAllLocationsURL());
      try {
          const resJson = await res.json();
          const datas:LocationType[] = resJson.data;
          const filteredList = datas.filter((item)=>item.parentId===null);
          setBigTree(PlantTree(filteredList,datas));
      } catch (error) {
          console.log(error);
          return;
      }
  }
   
  function PlantTree(Locations:LocationType[],AllLocations:LocationType[]):LocationType[]{
      return Locations.map((parent)=>{
          const children = AllLocations.filter(item=>item.parentId===parent.id)
          if(children.length>0){
              parent.children = PlantTree(children,AllLocations);
          }else{
              parent.children = undefined;
          }
          return parent;
      })
  }   

  useEffect(()=>{
    getAllLocations();      
  },[])

const fetchChildLocations=async (id:number)=>{
  try {
    const res = await fetch(getChildLocationsURL(id));
    const jsonData = await res.json();
    const data = jsonData.data;
      let temp = []
for(const item of data){
  temp.push([item.id,item.name,item.type,item.code,item.description]) 
}
setLocationDatas(temp)
fetchInventoryItems(id);
  } catch (error) {
    console.error(error);
    
  }
}

const fetchInventoryItems=async (id:number)=>{
  try {
    const res = await fetch(getInventoryItemsWithIdURL(id));
    const jsonData = await res.json();
    const data = jsonData.data;
    console.log(data);
    
    let temp = [];
    for(const item of data){
      temp.push([item.id,item.productId,"",item.locationId,item.quantity,item.reserved|0])
    }
    setInventoryItems(temp);
    
  } catch (error) {
    console.error(error);
    
  }
}

const handleSidebarClose = (change)=>{
setSideOpen(change | !sideOpen);
}
  return(
        <RootLayout>
            <div className="w-full h-[100vh] scrollbar-hide relative flex flex-row">
              <WarehouseSideBar visible={sideOpen} onClose={handleSidebarClose} onSelect={fetchChildLocations} Tree={BigTree} />
              <div className={`w-full p-5 flex flex-col gap-10`}>
            <div className={`${locationDatas && locationDatas.length>0?"":"hidden"}`}>
            <div className="font-bold text-md h-10 text-grayscale">Sub Locations</div>
            <Table colResize={true} rowResize={true} RowValues={locationDatas} rowHeight={60} ColumnNames={locatoinHeders}/>
            {/* <TableViewChildren/>
             <TableViewProducts/>*/}
            </div>
            <div>
            <div className={`font-bold text-md h-10 text-grayscale`}>
            Inventory
            </div>
            <Table className="" rowHeights={80} RowValues={inventoryItems} ColumnNames={inventoryHeaders} colResize={true}/>
            </div>
            </div>
           </div>
<button  className={`fixed left-5 bottom-5 bg-grayscale/10 active:bg-grayscale/30 p-3 rounded-full transition delay-100 ease-in-out sm:hidden ${sideOpen?"-translate-x-100":""}`} onClick={handleSidebarClose}><Map size={20}/></button>
        </RootLayout>
    )
}





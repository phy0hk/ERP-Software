import { useEffect, useState } from "react";
import RootLayout from "../layout/layout";
import { getAllLocationsURL } from "../utils/APILinks";
import type { LocationType } from "../utils/TypesList";
import {Map} from "lucide-react";
import WarehouseSideBar from "../components/inventory/WarehouseSideBar"
import {Table} from "../components/common/table"

export default function InventoryPage(){
  const [BigTree,setBigTree] = useState<LocationType[]>([])
      const [sideOpen,setSideOpen] = useState<boolean>(false);
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
const handleSidebarClose = (change)=>{
setSideOpen(change | !sideOpen);
}
  return(
        <RootLayout>               
            <div className="w-full h-[100vh] scrollbar-hide relative flex flex-row">
              <WarehouseSideBar visible={sideOpen} onClose={handleSidebarClose} Tree={BigTree} />
            <div className="w-fit p-5 overflow-x-auto">
            <Table ColumnNames={["Id","Name","Type","Code","Description"]}/>
            {/* <TableViewChildren/>
             <TableViewProducts/>*/}
            </div>
           </div>
<button  className={`fixed left-5 bottom-5 bg-grayscale/10 active:bg-grayscale/30 p-3 rounded-full transition delay-100 ease-in-out sm:hidden ${sideOpen?"-translate-x-100":""}`} onClick={handleSidebarClose}><Map size={20}/></button>
        </RootLayout>
    )
}





import { useEffect, useState } from "react";
import CusTreeView from "../components/inventory/cusTreeView";
import RootLayout from "../layout/layout";
import { getAllLocationsURL } from "../utils/APILinks";
import type { LocationType } from "../utils/TypesList";

export default function InventoryPage(){
  const [BigTree,setBigTree] = useState<LocationType[]>([])
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
  
  return(
        <RootLayout>
            <div className="w-full h-full">
            <div className="w-80 h-full bg-primary/10 p-3">
                <CusTreeView Data={BigTree}/>
            </div>
            </div>
        </RootLayout>
    )
}

import {useSelector,useDispatch} from "react-redux"
import type {RootState} from "../../utils/globalStats/store"
import {setAddNewLocPopUp} from "../../utils/globalStats/slices/inventorySlice"
import {useEffect} from "react"
import {X,MapPlus} from 'lucide-react'
import Dropdown from "../common/Dropdown"
import type {LocationType} from "../../utils/TypeList"
import type {InventoryStates} from "../../utils/globalStats/slices/inventorySlice"
export default function NewLocationPopUp({className}:props){
  const NewLocationPopUp:InventoryStates.AddNewLocPopUp = useSelector((state:RootState)=>state.inventory.AddNewLocPopUp)
  const AllLocations:LocationType[] = useSelector((state:RootState)=>state.inventory.AllLocations)
  const dispatch:any = useDispatch();
  useEffect(()=>{
    document.body.classList.toggle("overflow-hidden",NewLocationPopUp.isOpen)
  },[NewLocationPopUp])
  return (
    <div className={`w-full h-full top-0 fixed z-1000 backdrop-blur-[2px] flex items-center justify-center p-2 ${className} ${NewLocationPopUp.isOpen?"":"hidden"}`}>
    {/*Parent container*/}
      <div className={`max-w-full w-150 h-fit min-h-60 bg-background shadow border-[1px] border-grayscale/20 flex-col p-4`}>
      {/*Header container*/}
        <div className={`flex flex-row justify-between items-center`}>
<p className={`text-lg font-semibold flex flex-row items-center justify-center gap-2`}><MapPlus/>Add New Location</p>
        <X size={30} onClick={()=>dispatch(setAddNewLocPopUp({...NewLocationPopUp,isOpen:false}))} className={"p-1 rounded hover:bg-grayscale/10"}/>
        </div>

        {/*Parent location dropwdown*/}
        <div className={`flex flex-col`}>
          <p>Parent Location</p>
          <Dropdown data={["None",...AllLocations.map((item)=>item.code)]} defaultVal={NewLocationPopUp.selectedItem.code}/>
        </div>

      </div>
    </div>
  )
}

type props = {
  classname:string;
}

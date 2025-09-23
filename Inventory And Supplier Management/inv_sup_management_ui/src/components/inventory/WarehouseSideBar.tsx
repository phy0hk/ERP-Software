import type { LocationType } from "../utils/TypesList";
import {X} from "lucide-react"
import CusTreeView from "./cusTreeView";

export default function WarehouseSideBar({Tree,visible=false,onClose}:{Tree:LocationType[],visible:boolean,onClose:any}){
  const handleClose = () =>{
    if(onClose!==undefined){
      onClose()
    }
  }
  return(
    <div className={`max-sm:absolute bg-gray-200 justify-between flex flex-row transition delay-100 gap-2 ease-in-out ${visible?"":"max-sm:-translate-x-full"} w-80 h-full p-3`}>
    <div className="h-full w-full">
    <CusTreeView Data={Tree}/>
    </div>
    <div className="w-10 sm:w-0">
<button onClick={handleClose} className={`sm:hidden p-1 hover:bg-grayscale/10 rounded`}><X/></button>
    </div>

    </div>
  )
}


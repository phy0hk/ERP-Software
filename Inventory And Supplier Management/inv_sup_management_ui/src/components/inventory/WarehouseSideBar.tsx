import type { LocationType } from "../utils/TypesList";
import {X} from "lucide-react"
import CusTreeView from "./cusTreeView";
import {useRef,useState,React} from "react";
export default function WarehouseSideBar({Tree,visible=false,onClose,onSelect}:{Tree:LocationType[],visible:boolean,onClose:any,onSelect:any|undefined}){

const WarehouseSideBarRef = useRef<HTMLDivElement | null>(null);
const startX = useRef<number>(0);
const [width,setWidth] = useState(300);
const startWidth = useRef<number>(0);

  const handleClose = () =>{
    if(onClose!==undefined){
      onClose()
    }
  }
  const handleOnSelect=(id:number)=>{
if(onSelect!==undefined){
  onSelect(id)
}
  }

const handleMouseDown = (e:React.MouseEvent) =>{
  startX.current = e.clientX;
startWidth.current = width;
document.body.classList.add("cursor-col-resize");
 window.addEventListener("mousemove",handleMouseMove);
 window.addEventListener("mouseup",handleMouseUp);
}
const handleMouseMove = (e:React.MouseEvent)=>{
  const changed:number = e.clientX - startX.current;
  const newWidth:number = startWidth.current+(changed*2);
  console.log(changed);
  if(newWidth>300){
 setWidth(newWidth);
  }
  startX.current = e.clientX;
  startWidth.current = newWidth;
}
const handleMouseUp = (e:React.MouseEvent)=>{
document.body.classList.remove("cursor-col-resize");
  window.removeEventListener("mousemove",handleMouseMove);
  window.removeEventListener("mouseup",handleMouseUp);
}

  return(
    <div ref={WarehouseSideBarRef} className={`max-sm:absolute z-100 bg-gray-200 justify-between flex flex-row transition delay-100 gap-2 ease-in-out ${visible?"":"max-sm:-translate-x-full"} h-full py-3 pl-3`} style={{width:width}}>
    <div className="h-full w-full">
    <CusTreeView Data={Tree} onSelect={handleOnSelect}/>
    </div>
    <div className="w-10 sm:w-0">
<button onClick={handleClose} className={`sm:hidden p-1 hover:bg-grayscale/10 rounded`}><X/></button>
    </div>
<div onMouseDown={handleMouseDown} className={`h-full w-2 hover:cursor-col-resize`}></div>
    </div>
  )
}


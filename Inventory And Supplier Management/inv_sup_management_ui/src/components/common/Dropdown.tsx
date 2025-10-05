import {useState,useEffect,useRef,React} from "react"
import {ChevronUp,ChevronDown} from "lucide-react"

export default function Dropdownn({className,data,onSelect,isOpen,defaultVal}:props){
  const [Selected,setSelected] = useState<string>("None")
  const [Opened,setOpened] = useState<boolean>(false)
  const ref = useRef<HTMLDivElement>(null)
  useEffect(()=>{
    if(onSelect){
      onSelect(Selected)
    }
  },[Selected])

  useEffect(()=>{
   if(isOpen!==undefined && isOpen!==null){
     setOpened(isOpen)
   } 
  },[isOpen])
//Handle the dropdown select value
  const handleSelect = (value:string) =>{
    setSelected(value)
  }
  //handle the dropdown open and close
  const handleOpen = ({bol=true}:{bol:boolean}) =>{
    setOpened((prev)=>bol && !prev)
    if(isOpen!==undefined && isOpen!==null){
      isOpen(bol && !isOpen)
    }
  }
  //set default value if exist
  useEffect(()=>{
    if(defaultVal){
      setSelected(defaultVal)
    }
  },[defaultVal])
//this will detect click outside 

useEffect(() => {
  function handleClickOutside(event: MouseEvent) {
    if (ref.current && !ref.current.contains(event.target as Node) && Opened) {
      handleOpen({ bol: false });
    }
  }

  document.addEventListener("mousedown", handleClickOutside);
  return () => {
    document.removeEventListener("mousedown", handleClickOutside);
  };
}, [Opened, handleOpen]);


  return (
    <div ref={ref} className={`w-full select-none h-10 relative bg-background rounded shadow border-[1px] border-grayscale/20 ${className}`} onClick={handleOpen}>
    <div className={"w-full h-10 px-4 flex items-center justify-between absolute"}>{Selected}{Opened?<ChevronUp/>:<ChevronDown/>}</div>
   <div className={`w-full mt-11 bg-background border-[1px] border-grayscale/20 rounded transition-[max-height,opacity] ease-in-out duration-200 ${Opened?"h-fit max-h-50 min-h-5 opacity-100 overflow-auto":"max-h-0 opacity-0 overflow-hidden"}`} disabled={!Opened}>   
    {data && data.map((value,index)=>{
        return (
          <div key={index} className={`flex items-center hover:bg-grayscale/10 select-none px-4 h-10 ${Selected===value?"bg-grayscale/10":""}`} onClick={()=>handleSelect(value)}>{value}</div>
        )
      })}
      </div>
    </div>
  )
}

type props = {
  className:string;
  data:string[];
  isOpen:any;
  defaultVal:string;
}

import {TableHeaderType,TableRowType,TableType,TableCellType} from "../../utils/TypesList.ts"
import {useState,useEffect,useRef,React} from "react"
export function TableHeaders({ColumnNames,className}:TableHeaderType){
return (
  <div className={`flex flex-row w-fit border-grayscele border-1
    ${ColumnNames===undefined || ColumnNames===null ?"hidden":""} 
    ${className}`}>

  {ColumnNames && ColumnNames.map((name,index)=>{ 
    const lastIndex:number = ColumnNames.length-1;
    const isLast:string = lastIndex===index?"flex-1":"border-r-1";
    return (
     <div key={index} className={`p-2 hover:bg-grayscale/10 w-50 ${isLast}`}>{name}</div>
    )
  }
   )}
  </div>
)
}

export function TableRow({RowValues,className}:TableRowType){
return (
  <div className={`flex flex-row border-grayscele border-1 ${className}`}>
    
  {RowValues && RowValues.map((name,index)=>{
    const lastIndex:number = RowValues.length-1;
    const isLast:string = lastIndex===index?"flex-1":"border-r-1";
    return(
      <div key={index} className={`p-2 hover:bg-grayscale/10 ${isLast}`}>{name}</div>
    )
  })}
  </div>
)
}

export function Table({ColumnNames,RowValues,className,TableWidth=1000}:TableType){
  const [colLength,setColLength] = useState(0);
  const [rowLength,setRowLength] = useState(0);
  const [colWidths,setColWidths] = useState<number[]>([]);
  const [rowHeights,setRowHeights] = useState<number[]>([40]);

  useEffect(()=>{
    if(ColumnNames!==undefined){
      setColLength(ColumnNames.length)
    }
    if(RowValues!==undefined){
      setRowLength(RowValues.length)
    }
  },[ColumnNames,RowValues])
  useEffect(()=>{
    if(ColumnNames){
      setColWidths(new Array(ColumnNames.length).fill(TableWidth/ColumnNames.length))
    }
  },[ColumnNames])
  useEffect(()=>{
    if(RowValues){
      setRowHeights(new Array(RowValues.length+1).fill(40))
    }
  },[RowValues])

  const handleWidthChange = (changedWidth:number,colID:number)=>{
    setColWidths((prev)=>{
      const temp = [...prev];
      const newWidth = temp[colID]+changedWidth;
      const neightborWidth = temp[colID+1]-changedWidth;
      if(neightborWidth>=60 && newWidth>=60){
        temp[colID] = newWidth;
        temp[colID+1] = neightborWidth;
      }      
      return temp;
    })
  }

  return (
    <div className={`flex flex-col`}>
     <div className={`flex flex-row w`}>
     {ColumnNames && ColumnNames.map((item,index)=>{
       return (<TableCell rowID={0} colID={index} key={index} width={colWidths[index]} height={rowHeights[0]} lastColID={colLength} onWidthChange={(changed)=>handleWidthChange(changed,index)} lastRowID={rowLength} value={item}/>)
     })}
     </div>
     {
       RowValues && RowValues.map((Row,RowIndex)=>{
return (<div className={`flex flex-row`}>
        {Row.map((item,ColIndex)=>{
  return (<TableCell rowID={RowIndex+1} colID={ColIndex} width={colWidths[ColIndex]} height={rowHeights[RowIndex]} lastColID={colLength} lastRowID={rowLength} value={item}/>)
})}</div>)
       })
     }
    </div>
  );
}

export function TableCell({rowID,colID,lastRowID,lastColID,value,width=100,height=150,className,onWidthChange}:TableCellType){
// const [insideWidth,setInsideWidth] = useState<number>(width);
// const [insideHeight,setInsideHeight] = useState<number>(height);
const startPos = useRef<{x:number,y:number}>({x:0,y:0});
const DragSide= useRef<string|null>(null);
// useEffect(()=>{
// console.log("Height "+insideHeight,"Width"+insideWidth);
//
// },[insideWidth,insideHeight])

const handleMouseDown = (e:React.MouseEvent,Side:string) =>{  
  startPos.current = {x:e.clientX,y:e.clientY}
  DragSide.current = Side
  window.addEventListener("mousemove",handleMouseMove);
  window.addEventListener("mouseup",handleMouseUp);
}
const handleTouchStart = (e:React.TouchEvent,Side:string) =>{
  const touch = e.touches[0]
  startPos.current = {x:touch.clientX,y:touch.clientY}
 DragSide.current = Side
 window.addEventListener("touchmove",handleTouchMove);
 window.addEventListener("touchend",handleTOuchEnd);
}
const handleMouseUp = (e:React.MouseEvent)=>{
  DragSide.current = null
  window.removeEventListener("mousemove",handleMouseMove);
  window.removeEventListener("mouseup",handleMouseUp);
}
const handleTouchEnd = (e:React.TouchEvent)=>{
  DragSide.current = null
  window.removeEventListener("touchmove",handleTouchMove);
  window.removeEventListener("touchend",handleTouchEnd)
}
const handleMouseMove = (e:React.MouseEvent)=>{
  
  if(DragSide===null) return;
const changedPosX = e.clientX - startPos.current.x;
const changedPosY = e.clientY - startPos.current.y;

startPos.current = {x:e.clientX,y:e.clientY}
updatePos(changedPosX,changedPosY)
}
const handleTouchMove = (e:React.TouchEvent)=>{
  const touch = e.touches[0];
  if(!touch) return;
  const changedPosX = touch.clientX - startPos.current.x;
  const changedPosY = touch.clientY - startPos.current.y;
  startPos.current = {x:touch.clientX,y:touch.clientY};
  updatePos(changedPosX,changedPosY)
}

function updatePos(changedPosX:number,changedPosY:number){
if(DragSide.current==="Right" && colID!==lastColID-1){
    // setInsideWidth((prev)=>prev+changedPosX);
widthChange(changedPosX)
    }else if(DragSide.current==="Left" && colID!==0){ 
    // setInsideWidth((prev)=>prev-changedPosX);
}else if(DragSide.current==="Top"){
    // setInsideHeight((prev)=>prev-changedPosY);
}else if(DragSide.current==="Bottom"){
  // setInsideHeight((prev)=>prev+changedPosY);
}
}

function widthChange(changedWidth:number){
if(onWidthChange!==undefined){
  onWidthChange(changedWidth)
}
}


  return (
    <div className={`relative px-3 py-2 ${className}`} style={{width,height}}>
    {/*Top Border*/}
      <div className={`absolute top-0 left-0 right-0 h-1 border-grayscale border-t-1 ${rowID>0?"hidden":""}`}></div>
{/*Bottom Border*/}
      <div className={`absolute bottom-0 left-0 right-0 h-1 border-grayscale border-b-1`}></div>
 {/*Left Border*/} 
      <div onMouseDown={(e)=>handleMouseDown(e,"Left")} className={`absolute top-0 bottom-0 left-0 w-1 border-grayscale border-l-1 ${colID===0?"":"hidden"} ${rowID===0?"hover:cursor-col-resize":""}`}></div>
{/*Right Border*/}
      <div onMouseDown={(e)=>handleMouseDown(e,"Right")} className={`absolute top-0 right-0 bottom-0 border-grayscale border-r-1 w-1 ${lastColID-1!==colID && rowID===0?"hover:cursor-col-resize":""}`}></div>
      <div className={`overflow-auto scrollbar-hide ${rowID===0?"select-none":""}`}>{value}</div>
    </div>
  )
}

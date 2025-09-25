import {TableHeaderType,TableRowType,TableType,TableCellType} from "../../utils/TypesList.ts"
import {useState,useEffect} from "react"
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

export function Table({ColumnNames,RowValues,className}:TableType){
  const [colLength,setColLength] = useState(0);
  const [rowLength,setRowLength] = useState(0);
  useEffect(()=>{
    if(ColumnNames!==undefined){
      setColLength(ColumnNames.length)
    }
    if(RowValues!==undefined){
      setRowLength(RowValues.length)
    }
  },[ColumnNames,RowValues])

  return (
    <div className={`flex flex-col`}>
     <div className={`flex flex-row`}>
     {ColumnNames.map((item,index)=>{return (<TableCell rowID={0} colID={index} key={index} width={"auto"} value={item}/>)})}     
     </div>
    </div>
  );
}

export function TableCell({rowID,colID,width=50,height=50,value,className}:TableCellType){
  return (
    <div className={`px-3 py-2`} style={{width,height}} className={`px-3 py-2 relative`}>
    {/*Top border*/}
    <div className={`absolute h-0.5 top-0 left-0 right-0 hover:cursor-row-resize bg-black`}></div>
    {/*Bottom border*/}
    <div className={`absolute h-0.5 bottom-0 left-0 right-0 hover:cursor-row-resize bg-black`}></div>
    {/*Right border*/}
    <div className={`absolute w-0.5 top-0 bottom-0 right-0 hover:cursor-col-resize bg-black`}></div>
    {/*Left border*/}
    <div className={`absolute w-0.5 top-0 left-0 bottom-0 hover:cursor-col-resize bg-black`}></div>
    <div className="overflow-auto w-full h-full">{value}</div>
    </div>
  )
}

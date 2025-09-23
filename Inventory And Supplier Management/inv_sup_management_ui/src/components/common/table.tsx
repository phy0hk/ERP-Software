import {TableHeaderType,TableRowType,TableType} from "../../utils/TypesList.ts"

export function TableHeaders({ColumnNames,className}:TableHeaderType){
return (
  <div className={`flex flex-row w-fit border-grayscele border-1 
    ${ColumnNames===undefined || ColumnNames===null ?"hidden":""} 
    ${className}`}>

  {ColumnNames && ColumnNames.map((name,index)=>{ 
    const lastIndex:number = ColumnNames.length-1;
    const isLast:string = lastIndex===index?"flex-1":"border-r-1";
    return (
     <div key={index} className={`p-2 hover:bg-grayscale/10  ${isLast}`}>{name}</div>
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
  return (
    <div className={``}>
      
    </div>
  );
}

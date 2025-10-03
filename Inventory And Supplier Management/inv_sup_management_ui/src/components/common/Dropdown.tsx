export default function Dropdownn({className}:props){
  return (
    <div className={`w-full h-10 bg-background rounded shadow border-[1px] border-grayscale/20 ${className}`}>

    </div>
  )
}

type props = {
  className:string;
  data:string[];

}

import {useState,useEffect} from "react";

export default function loadWasm(path:string){
  if(path===undefined || path===null) return null;
  const [module,setModule] =  useState<any>(null);
  useEffect(()=>{
const script = document.createElement("script");
script.src = path;
script.onload = () =>{
  window.Module.onRuntimeInitialized = () =>{
   setModule(window.Module);
  }
}
console.log(path);

document.body.appendChild(script);
return ()=>{
  document.body.removeChild(script);
};
  },[path])
  return module;
}

import { useLocation, useNavigate } from "react-router";
import type { PageRoutes } from "../../utils/TypesList";
import DynamicIcon from "../common/DynamicIcon.tsx"

export default function NavbarButton({name,route,icon}:PageRoutes){
    const navigate = useNavigate();
    const location = useLocation();
    const pathname = location.pathname;
    
    const handleClick = () =>{
        navigate(route);
    }
    
    return (
        <button className="flex flex-col group" onClick={handleClick} >
            <div className={` ${pathname==route?"bg-primary/10":""} px-5 py-3 cursor-pointer flex flex-row items-center gap-3`}>
        <DynamicIcon size={20} name={icon}/>
        <div className="max-sm:hidden">
            {name}
        </div>
            </div>
        <span className={` ${pathname==route?"w-full":"w-0 group-hover:bg-secondary group-hover:w-full"}  bg-primary h-1`}></span>
        </button>
    )
}


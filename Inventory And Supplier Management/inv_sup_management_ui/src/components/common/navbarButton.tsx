import { useLocation, useNavigate } from "react-router";
import type { PageRoutes } from "../../utils/TypesList";
import * as Icons from "lucide-react"
import type { LucideProps } from "lucide-react";

export default function NavbarButton({name,route,icon}:PageRoutes){
    const navigate = useNavigate();
    const location = useLocation();
    const pathname = location.pathname;
    
    const handleClick = () =>{
        navigate(route);
    }


    return (
        <button className="flex flex-col" onClick={handleClick} >
            <div className={` ${pathname==route?"bg-primary/10":""} px-5 py-3 cursor-pointer flex flex-row items-center gap-3`}>
        <DynamicIcon name={icon}/>
        <div className="max-sm:hidden">
            {name}
        </div>
            </div>
        <span className={` ${pathname==route?"w-full":"w-0"} transform ease-in-out delay-300 bg-primary h-1`}></span>
        </button>
    )
}

type IconName = keyof typeof Icons;

interface DynamicIconProps {
  name: IconName;
  size?: number;
  color?: string;
}

const DynamicIcon = ({ name, size = 24, color = "currentColor" }: DynamicIconProps) => {
  const Icon = Icons[name] as React.ComponentType<LucideProps>;
  return <Icon size={size} color={color} />;
};
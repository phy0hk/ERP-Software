import * as Icons from "lucide-react"
import type { LucideProps } from "lucide-react";


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

export default DynamicIcon;

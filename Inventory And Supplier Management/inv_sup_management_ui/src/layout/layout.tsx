import Navbar from "../components/common/navbar";

export default function RootLayout({children}:any){
    return(
        <div className="font-inter bg-background w-full h-dvh">
        <Navbar/>
        {children}
        </div>
    )
}

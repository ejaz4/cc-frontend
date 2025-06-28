import { useEffect, useState } from "react";
import { storage } from "./storage";

export const useIsSetup = () => {
    const [isSetup, setIsSetup] = useState<boolean | null>(null);

    const checkSetup = async () => {
        const setup = await storage.getItem("isSetup")

        console.log(setup)
        if (setup === null) {
            console.log("Setup not found, setting to false");
            setIsSetup(false);
            return;
        } else if (setup === "true") {
            console.log("Setup found, setting to true");
            setIsSetup(true);
            return;
        }
    }

    useEffect(() => {
        checkSetup()
    }, [])
    
    return isSetup;
}
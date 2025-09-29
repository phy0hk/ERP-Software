let wasmModuleCache:any = null;
let loadingPromise:Promise<any> |null= null;

export default function useWasm(path: string) {
  if (!path) return Promise.reject(new Error("No path defined"));
  if (wasmModuleCache) return Promise.resolve(wasmModuleCache);
  if (loadingPromise) return loadingPromise;

  loadingPromise = new Promise((resolve, reject) => {
    const script = document.createElement("script");
    script.src = path;  // path relative to /public
    script.onload = () => {
      if (window.InventoryModule) {
        window.InventoryModule().then((module: any) => {
          wasmModuleCache = module;
          resolve(module);
        });
      } else {
        reject(new Error("Module not found in window"));
      }
    };
    script.onerror = () => reject(new Error("Failed to load WASM module"));
    document.body.appendChild(script);
  });

  return loadingPromise;
}


export function getDeviceArchitecture() {
  const ua = navigator.userAgent || navigator.vendor || window.opera;

  if (/arm64|aarch64/i.test(ua)) return "arm64";
  if (/arm|android/i.test(ua)) return "arm";
  if (/x86_64|win64|amd64/i.test(ua)) return "x86_64";
  if (/x86|i386/i.test(ua)) return "x86";
  return "unknown";
}

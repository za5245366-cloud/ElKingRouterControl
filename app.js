const routerIp = "192.168.0.1";
document.getElementById("copyIp").addEventListener("click", async () => {
  try {
    await navigator.clipboard.writeText(routerIp);
    document.getElementById("copyIp").textContent = "تم النسخ ✓";
  } catch {
    document.getElementById("copyIp").textContent = routerIp;
  }
});

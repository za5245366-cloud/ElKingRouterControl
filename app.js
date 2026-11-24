const ROUTER_IP = "192.168.1.1";
const ROUTER_URL = `http://${ROUTER_IP}`;

// Mobile navigation
const sidebar = document.getElementById("sidebar");
const menuButton = document.getElementById("menuBtn");
if (sidebar && menuButton) {
  menuButton.addEventListener("click", () => sidebar.classList.toggle("open"));
}

// Keep router links synchronized with the configured gateway.
document.querySelectorAll('a[href^="http://192.168."]').forEach((link) => {
  link.href = ROUTER_URL;
});

document.querySelectorAll(".nav").forEach((link) => {
  link.addEventListener("click", () => {
    document.querySelectorAll(".nav").forEach((item) => item.classList.remove("active"));
    link.classList.add("active");
    sidebar?.classList.remove("open");
  });
});

const frame=document.getElementById('routerFrame');const fallback=document.getElementById('frameFallback');let timer;
function tryFrame(){fallback.classList.remove('hidden');clearTimeout(timer);frame.src='about:blank';setTimeout(()=>{frame.src='http://192.168.1.1/';timer=setTimeout(()=>fallback.classList.remove('hidden'),3500)},80)}
// Browsers do not expose a reliable event for X-Frame-Options/CSP blocking.
// Keep the safe fallback visible until the user confirms that the login page rendered.
document.getElementById('retry').addEventListener('click',tryFrame);
document.getElementById('sameTab').addEventListener('click',()=>{window.location.href='http://192.168.1.1/'});
document.getElementById('menu').addEventListener('click',()=>document.getElementById('sidebar').classList.toggle('open'));
frame.addEventListener('load',()=>{if(frame.src==='about:blank')return;clearTimeout(timer);});
// User can dismiss fallback if the router login screen is visibly loaded inside the frame.
fallback.addEventListener('dblclick',()=>fallback.classList.add('hidden'));

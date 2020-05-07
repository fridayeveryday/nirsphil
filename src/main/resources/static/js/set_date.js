//if time isn't then timeout equals 1000
// if time is then timeout equals time
timeout = 1000;
function set_date(id, time) {
    setInterval(function(){ document.getElementById(id).value = new Date().getTime(); }, time ? time: timeout);
}

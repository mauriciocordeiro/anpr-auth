(function (window) {
    window["env"] = window["env"] || {};

    window["env"]["apiAuth"]     = "http://authpy:5002";
    window["env"]["apiVehicles"] = "http://localhost:5001/vehicles";
    window["env"]["apiCheck"]    = "http://localhost:8081/vehicles";
})(this);
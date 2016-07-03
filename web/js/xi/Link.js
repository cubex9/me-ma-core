if (!cxn)
    var cxn = {};

cxn.Link = function (net, o) {
    this.net = net;
    this.id = o.id;
    this.off = o.off;
    this.target = o.target;
    this.tags = o.tags;
}

cxn.Node.prototype = {
    // nacte sam sebe ze zdroje
    parse: function (data) {
        var o = JSON.parse(data);
    },
    getId: function () {
        return this.id;
    },
    // jmeno
    getOff: function () {
        return this.off;
    },
    // target
    getTarget: function () {
        return this.target;
    },
    // pole tagu
    getTags: function () {
        return this.tags;
    }
}
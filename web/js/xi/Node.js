if (!cxn)
    var cxn = {};

cxn.Node = function (net, o) {
    this.net = net;
    this.id = o.id;
    this.name = o.name;
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
    getName: function () {
        return this.name;
    },
    // pole tagu
    getTags: function () {
        return this.tags;
    }
}
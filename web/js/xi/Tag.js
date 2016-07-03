if (!cxn)
    var cxn = {};

cxn.Tag = function (net, o) {
    this.net = net;
    this.id = o.id;
    this.name = o.name;
}

cxn.Tag.prototype = {
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

    }
}
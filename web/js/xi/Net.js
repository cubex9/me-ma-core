/**
 * Created by kubasek on 7/3/2016.
 */
if (!cxn)
    var cxn = {};

cxn.Net = function (data) {
    this.name = null;
    this.entities = new Array();
    this.parse(data);
};

cxn.Net.prototype = {
    // nacte sam sebe ze zdroje
    parse: function (o) {
        // 
        this.name = o.name;
        for (var x in o.entities) {
            var e = o.entities[x];
            if (e.clazz == 'Node') {
                this.entities[e.id] = new cxn.Node(this, e);
            } else if (e.clazz = 'Link') {
                this.entities[e.id] = new cxn.Link(this, e);
            } else if (e.clazz = 'Tag') {
                this.entities[e.id] = new cxn.Tag(this, e);
            }
        }
    },
    // vlozi nebo prepise objekt
    put: function (o) {
        this.elements[o.getId()] = o;
    },

    // get by id
    get: function (id) {
        return this.elements[id];
    },

    render: function (c) {
        var ctx = c.getContext("2d");
    }
};

var net = null;
var connection = require('../connection');
var mysql = require('mysql');

function Todo() {
    this.getPost = function (res) {
        connection.acquire(function (err, con) {
            con.query('select * from post', function (err, result) {
                con.release();
                res.send(result);
            });
        });
    };

    this.getSub = function (res) {
        connection.acquire(function (err, con) {
            con.query('select * from subject', function (err, result) {
                con.release();
                res.send(result);
            });
        });
    };

    this.getPostinSub = function (id, res) {
        connection.acquire(function (err, con) {
            con.query('select * from post where IDSubject=?', [id], function (err, result) {
                con.release();
                res.send(result);
            });
        });
    };

    this.getPostAcc = function (id, res) {
        connection.acquire(function (err, con) {
            con.query('select * from post where IDAccount=?', [id], function (err, result) {
                con.release();
                res.send(result);
            });
        });
    };

    this.createUser = function (field_data, res) {
        var querySQL = "insert into account values(?,?,?)";
        var table = [parseInt(field_data.idAccount),field_data.name,parseInt(field_data.idFB)];
        querySQL = mysql.format(querySQL,table);
        connection.acquire(function (err, con) {
            con.query(querySQL , function (err, result) {
                con.release();
                if (err) {
                    res.send({status: 1, message: 'TODO creation failed'});
                } else {
                    res.send({status: 0, message: 'TODO created successfully'});
                }
            });
        });
    };

    this.createPost = function (field_data, res) {
        var querySQL = "insert into post values(?,?,?,?,?,?,?,?)";
        var table = [parseInt(field_data.idAccount),field_data.Title,field_data.PostTime,field_data.Content,field_data.Address,parseInt(field_data.Sdt),field_data.Tienday,parseInt(field_data.type),field_data.IDAccount,field_data.IDSubject];
        querySQL = mysql.format(querySQL,table);
        connection.acquire(function (err, con) {
            con.query(querySQL , function (err, result) {
                con.release();
                if (err) {
                    res.send({status: 1, message: 'TODO creation failed'});
                } else {
                    res.send({status: 0, message: 'TODO created successfully'});
                }
            });
        });
    };

    this.update = function (field_data, res) {
        var querySQL = "update post set ? where id = ?";
        var table = [];
        querySQL = mysql.format(querySQL,table);
        connection.acquire(function (err, con) {
            con.query(querySQL, function (err, result) {
                con.release();
                if (err) {
                    res.send({status: 1, message: 'TODO update failed'});
                } else {
                    res.send({status: 0, message: 'TODO updated successfully'});
                }
            });
        });
    };

    this.read = function (id, res) {
        connection.acquire(function (err, con) {
            con.query('select * from post where id=?', [id], function (err, result) {
                con.release();
                res.send(result);
            });
        });
    };

    this.delete = function (id, res) {
        connection.acquire(function (err, con) {
            con.query('delete from post where id = ?', [id], function (err, result) {
                con.release();
                if (err) {
                    res.send({status: 1, message: 'Failed to delete'});
                } else {
                    res.send({status: 0, message: 'Deleted successfully'});
                }
            });
        });
    };
}
module.exports = new Todo();
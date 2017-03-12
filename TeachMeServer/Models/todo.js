var connection = require('../connection');
var mysql = require('mysql');

function Todo() {
    this.getListPost = function (id,res) {
        connection.acquire(function (err, con) {
            con.query('select IDPost,Title,Content,Address,Tienday,Sdt,Tenmon,Post.IDFb,Name from Post,Subject,Account where Account.IDFb = Post.IDFb and Post.IDSubject = Subject.IDSubject and type = ? order by PostTime DESC',id, function (err, result) {
                con.release();
                res.send(result);
            });
        });
    };

    this.getSub = function (res) {
        connection.acquire(function (err, con) {
            con.query('select * from Subject', function (err, result) {
                con.release();
                res.send(result);
            });
        });
    };


    this.getPostinSub = function (ten, res) {
        connection.acquire(function (err, con) {
            con.query('select IDPost,Title,Content,Address,Tienday,Sdt,Tenmon,Post.IDFb,Name from Post,Subject,Account where Account.IDFb = Post.IDFb and Post.IDSubject = Subject.IDSubject and TenMon=?', ten, function (err, result) {
                con.release();
                res.send(result);
            });
        });
    };

    this.getPostAcc = function (id, res) {
        connection.acquire(function (err, con) {
            con.query('select * from post where IDFb=?', [id], function (err, result) {
                con.release();
                res.send(result);
            });
        });
    };

    this.getPostBookmark = function (id, res) {
        var querySQL = "select Post.IDPost,Title,Content,Address,Tienday,Sdt,Tenmon,Post.IDFb,Name from Post,Subject,Account,bookmark where Post.IDSubject = Subject.IDSubject and Account.IDFb = Post.IDFb and Post.IDPost=bookmark.IDPost and bookmark.IDFb =  "+id;
        console.log(querySQL);
        connection.acquire(function (err, con) {
            con.query(querySQL, function (err, result) {
                con.release();
                res.send(result);
            });
        });
    };

    //updated
    this.checkUser = function (idfb, res) {
        var querySQL = 'SELECT * FROM `Account` WHERE `IDFb` = '+idfb;
        connection.acquire(function (err, con) {
            con.query(querySQL, function (err, result) {
                //console.log(querySQL);
                con.release();
                res.send(result);
            });
        });
    };

    this.createUser = function (field_data, res) {
        var querySQL = "insert into Account(Name,IDFb,Email,Gender,Birthday) values(?,?,?,?,?)";
        var table = [field_data.name,field_data.id,field_data.email,field_data.gender,field_data.birthday];
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
        var querySQL = "insert into Post(Title,PostTime,Content,Address,Sdt,Tienday,type,IDFb,IDSubject) values(?,?,?,?,?,?,?,?,?)";
        var table = [field_data.Title,field_data.PostTime,field_data.Content,field_data.Address,field_data.Sdt,field_data.Tienday,parseInt(field_data.type),field_data.IDFb,field_data.IDSubject];
        querySQL = mysql.format(querySQL,table);
        //console.log(querySQL);
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

    this.createBookMark = function (field_data,res) {
        var querySQL = "insert into bookmark(IDFb,IDPost) values(?,?)";
        var table = [field_data.IDFb,field_data.IDPost];
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
        connection.acquire(function (err, con) {
            con.query('update todo_list set ? where id = ?', [field_data, field_data.id], function (err, result) {
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
            con.query('select title,content,sdt,tienday,tenmon,name,idfb from post,account where post.IDFb=account.IDFb and idpost=?', [id], function (err, result) {
                con.release();
                res.send(result);
            });
        });
    };

    this.deleteBookmark = function (field_data, res) {
        var querySQL = 'delete from bookmark where IDFb = ? and IDPost = ?';
        var table = [ field_data.IDFb,field_data.IDPost];
        querySQL = mysql.format(querySQL,table);
        connection.acquire(function (err, con) {
            con.query(querySQL, function (err, result) {

                con.release();
                if (err) {
                    res.send({status: 1, message: 'Failed to delete'});
                } else {
                    res.send({status: 0, message: 'Deleted successfully'});
                }
            });
        });
    };

    this.deleteAllBookmark = function (field_data, res) {
        var querySQL = 'delete from bookmark where IDFb = ? ';
        var table = [ field_data.IDFb];
        querySQL = mysql.format(querySQL,table);
        connection.acquire(function (err, con) {
            con.query(querySQL, function (err, result) {

                con.release();
                if (err) {
                    res.send({status: 1, message: 'Failed to delete'});
                } else {
                    res.send({status: 0, message: 'Deleted successfully'});
                }
            });
        });
    };

    this.checkBookmark = function (IDFb,IDPost, res) {
        var querySQL = 'select * from bookmark where IDFb = ? and IDPost = ?';
        var table = [ IDFb,IDPost];
        querySQL = mysql.format(querySQL,table);
        connection.acquire(function (err, con) {
            con.query(querySQL, function (err, result) {
                con.release();
                res.send(result);
            });
        });
    };
}
module.exports = new Todo();

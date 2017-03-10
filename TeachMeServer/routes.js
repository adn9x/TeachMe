var todo = require('./Models/todo');

module.exports = {
    configure: function (app) {
        app.get('/todo/getlistpost', function (req, res) {
            todo.getListPost(res);
        });

        app.get('/todo/getsubject', function (req, res) {
            todo.getSub(res);
        });

        app.get('/todo/read/:id/', function (req, res) {
            todo.read(req.params.id,res);
        });

        app.get('/todo/checkuser/:id/', function (req, res) {
            todo.checkUser(req.params.id,res);
        });

        app.get('/todo/getpostinsub/:id/', function (req, res) {
            todo.getPostinSub(req.params.id,res);
        });

        app.get('/todo/getpostacc/:id/', function (req, res) {
            todo.getPostAcc(req.params.id,res);
        });

        app.post('/todo/createuser', function (req, res) {
            todo.createUser(req.body, res);
        });

        app.post('/todo/createpost', function (req, res) {
            todo.createPost(req.body, res);
        });

        app.put('/todo/update', function (req, res) {
            todo.update(req.body, res);
        });

        app.delete('/todo/delete/:id/', function (req, res) {
            todo.delete(req.params.id, res);
        });
    }
};
var todo = require('./Models/todo');

module.exports = {
    configure: function (app) {
        app.get('/todo/getlistpost/:id/', function (req, res) {
            todo.getListPost(req.params.id,res);
        });

        app.get('/todo/getsubject', function (req, res) {
            todo.getSub(res);
        });
        app.get('/todo/getpost', function (req, res) {
            todo.getPost(res);
        });

        app.get('/todo/read/:id/', function (req, res) {
            todo.read(res);
        });

        app.get('/todo/checkuser/:id/', function (req, res) {
            todo.checkUser(req.params.id,res);
            console.log(req.params.id);
        });

        app.get('/todo/getpostinsub/:ten/', function (req, res) {
            todo.getPostinSub(req.params.ten, res);
        });

        app.get('/todo/getpostacc/:id/', function (req, res) {
            todo.getPostAcc(req.params.id,res);
        });


        app.get('/todo/getpostbookmark/:id/', function (req, res) {
            todo.getPostBookmark(req.params.id,res);
        });

        app.post('/todo/createuser', function (req, res) {
            todo.createUser(req.body, res);
        });

        app.post('/todo/createpost', function (req, res) {
            todo.createPost(req.body, res);
        });

        app.post('/todo/createbookmark', function (req, res) {
            todo.createBookMark(req.body, res);
        });

        app.put('/todo/update', function (req, res) {
            todo.update(req.body, res);
        });

        app.post('/todo/deletebookmark/', function (req, res) {
            todo.deleteBookmark(req.body, res);
        });

        app.post('/todo/delallbookmark/', function (req, res) {
            todo.deleteAllBookmark(req.body, res);
        });

        app.get('/todo/checkbookmark/:IDFb/:IDPost', function (req, res) {
            todo.checkBookmark(req.params.IDFb,req.params.IDPost, res);
        });

    }
};